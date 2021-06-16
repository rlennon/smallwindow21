variable "owner_name" {
  type = string
}
variable "project_name" {
  type = string
}

variable "app_name" {
  type = string
}
variable "app_environment_name" {
  type = string
}
variable "instance_type" {
  type = string
}
variable "general_sg_id" {
  type = string
}

variable "app_sg_id" {
  type = string
}
variable "dbase_sg_id" {
  type = string
}
variable "vpc_id" {
  type = string
}
variable "public_subnet_id" {
  type = string
}
variable "private_subnet_app_id" {
  type = string
}
variable "eb_instance_profile_id" {
  type = string
}

resource "aws_elastic_beanstalk_application" "app_instance" {
  name        = var.app_name
  description = var.app_name
}

resource "aws_elastic_beanstalk_environment" "app_instance_environment" {
  name                = var.app_environment_name
  application         = aws_elastic_beanstalk_application.app_instance.name
  solution_stack_name = "64bit Amazon Linux 2018.03 v2.16.8 running Docker 19.03.13-ce"
  tier                = "WebServer"

  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "SecurityGroups"
    value     = "${var.general_sg_id}, ${var.app_sg_id}"
  }
  setting {
    namespace = "aws:ec2:vpc"
    name      = "VPCId"
    value     = var.vpc_id
  }
  setting {
    namespace = "aws:ec2:vpc"
    name      = "Subnets"
    value     = var.private_subnet_app_id
  }
  setting {
    namespace = "aws:ec2:vpc"
    name      = "ELBSubnets"
    value     = var.public_subnet_id
  }
  setting {
    namespace = "aws:elbv2:loadbalancer"
    name      = "SecurityGroups"
    value     = var.general_sg_id
  }
  setting {
    namespace = "aws:ec2:instances"
    name      = "InstanceTypes"
    value     = var.instance_type
  }
  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "IamInstanceProfile"
    value     = var.eb_instance_profile_id
  }
  tags = {
    Name  = var.app_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}

output "elasticbeanstalk_app_endpoint" {
  value = aws_elastic_beanstalk_environment.app_instance_environment.endpoint_url
}
