variable "project_name" {
  type = string
}
variable "vpc_id" {
  type = string
}

variable "eb_profile_name" {
  type = string
}
variable "eb_role_name" {
  type = string
}
variable "eb_service_role_name" {
  type = string
}
variable "storage_bucket_name" {
  type = string
}
resource "aws_security_group" "general_sg" {
  name        = "General Security Group"
  description = "HTTP egress to anywhere"
  vpc_id      = var.vpc_id

  tags = {
    project = var.project_name
    Name    = "General Security Group"
  }
}


resource "aws_security_group" "app_sg" {
  name        = "App Security Group"
  description = "SSH ingress from Bastion and all TCP traffic ingress from ALB Security Group"
  vpc_id      = var.vpc_id
  tags = {
    project = var.project_name
    Name    = "App Security Group"
  }
}

resource "aws_security_group" "dbase_sg" {
  name        = "Database Security Group"
  description = "Allow traffic from app instance to database on port 3306"
  vpc_id      = var.vpc_id
  tags = {
    project = var.project_name
    Name    = "Database Security Group"
  }
}

resource "aws_security_group_rule" "out_http" {
  #set specific group rules for ports and ip addresses
  type              = "egress"
  from_port         = 80
  to_port           = 80
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.general_sg.id
}

resource "aws_security_group_rule" "out_http_app" {
  type              = "egress"
  description       = "Allow TCP internet traffic egress from app layer"
  from_port         = 80
  to_port           = 80
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.app_sg.id
}

resource "aws_security_group_rule" "out_database_from_app" {
  type                     = "egress"
  from_port                = 3306
  to_port                  = 3306
  protocol                 = "tcp"
  security_group_id        = aws_security_group.dbase_sg.id
  source_security_group_id = aws_security_group.app_sg.id
}


resource "aws_security_group_rule" "in_database_from_app" {
  type                     = "ingress"
  description              = "Allow traffic on port 3306 from the App Security Group"
  from_port                = 3306
  to_port                  = 3306
  protocol                 = "tcp"
  security_group_id        = aws_security_group.dbase_sg.id
  source_security_group_id = aws_security_group.app_sg.id
}

resource "aws_iam_instance_profile" "eb_profile" {
  name = var.eb_profile_name
  role = aws_iam_role.eb_role.name
}

resource "aws_iam_role" "eb_role" {
  name                = var.eb_role_name
  path                = "/"
  managed_policy_arns = ["arn:aws:iam::aws:policy/AWSElasticBeanstalkWebTier", "arn:aws:iam::aws:policy/AWSElasticBeanstalkWorkerTier", aws_iam_policy.smallwindow21_s3_policy.arn]
  assume_role_policy  = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Action": "sts:AssumeRole",
            "Principal": {
               "Service": "ec2.amazonaws.com"
            },
            "Effect": "Allow",
            "Sid": ""
        }
    ]
}
EOF
}

resource "aws_iam_policy" "smallwindow21_s3_policy" {
  name = "smallwindow21_s3_policy"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "s3:PutObject",
          "s3:PutObjectAcl",
          "s3:GetObject",
          "s3:GetObjectAcl",
          "s3:DeleteObject",
          "s3:DeleteObjectVersion",
          "s3:ListBucket"
        ]
        Effect   = "Allow"
        Resource = ["arn:aws:s3:::${var.storage_bucket_name}", "arn:aws:s3:::${var.storage_bucket_name}/*"]
      },
    ]
  })
}


resource "aws_iam_role" "eb_service_role" {
  name                = var.eb_service_role_name
  path                = "/"
  managed_policy_arns = ["arn:aws:iam::aws:policy/AWSElasticBeanstalkEnhancedHealth", "arn:aws:iam::aws:policy/AWSElasticBeanstalkService"]
  assume_role_policy  = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Action": "sts:AssumeRole",
            "Principal": {
               "Service": "elasticbeanstalk.amazonaws.com"
            },
            "Effect": "Allow",
            "Sid": ""
        }
    ]
}
EOF
}

output "general_sg_id" {
  value = aws_security_group.general_sg.id
}

output "app_sg_id" {
  value = aws_security_group.app_sg.id
}
output "dbase_sg_id" {
  value = aws_security_group.dbase_sg.id
}

output "eb_instance_profile_id" {
  value = aws_iam_instance_profile.eb_profile.id
}

output "eb_service_role_arn" {
  value = aws_iam_role.eb_service_role.arn
}
 