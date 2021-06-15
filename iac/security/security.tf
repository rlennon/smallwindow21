variable "project_name" {
  type = string
}
variable "vpc_id" {
  type = string
}

resource "aws_security_group" "general_sg" {
  name = "General Security Group"
  description = "HTTP egress to anywhere"
  vpc_id      = var.vpc_id

  tags = {
    proj = var.project_name
    Name = "General Security Group"
  }
}


resource "aws_security_group" "bastion_sg" {
  name = "Bastion Security Group"
  # bastion is exposed to outside of the network
  # eg. mail, dns, web, ftp
  description = "SSH ingress to Bastion and SSH egress to App"
  vpc_id      = var.vpc_id

  tags = {
    proj = var.project_name
    Name = "Bastion Security Group"
  }
}

resource "aws_security_group" "app_sg" {
  name = "App Security Group"
  description = "SSH ingress from Bastion and all TCP traffic ingress from ALB Security Group"
  vpc_id      = var.vpc_id
  tags = {
    proj = var.project_name
    Name = "App Security Group"
  }
}

resource "aws_security_group" "dbase_sg" {
  name = "Database Security Group"
  description = "Allow traffic from app instance to database on port 3306"
  vpc_id      = var.vpc_id
  tags = {
    proj = var.project_name
    Name = "Database Security Group"
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

resource "aws_security_group_rule" "out_ssh_bastion" {
  type                     = "egress"
  from_port                = 22
  to_port                  = 22
  protocol                 = "tcp"
  security_group_id        = aws_security_group.bastion_sg.id
  source_security_group_id = aws_security_group.app_sg.id
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

resource "aws_security_group_rule" "in_ssh_bastion_from_anywhere" {
  type              = "ingress"
  from_port         = 22
  to_port           = 22
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.bastion_sg.id
}

resource "aws_security_group_rule" "in_ssh_app_from_bastion" {
  type                     = "ingress"
  description              = "Allow SSH from a Bastion Security Group"
  from_port                = 22
  to_port                  = 22
  protocol                 = "tcp"
  security_group_id        = aws_security_group.app_sg.id
  source_security_group_id = aws_security_group.bastion_sg.id
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


output "general_sg_id" {
  value = aws_security_group.general_sg.id
}
output "bastion_sg_id" {
  value = aws_security_group.bastion_sg.id
}
output "app_sg_id" {
  value = aws_security_group.app_sg.id
}
output "dbase_sg_id" {
  value = aws_security_group.dbase_sg.id
}