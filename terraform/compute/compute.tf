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
variable "db_address" {
  type = string
}
variable "db_endpoint" {
  type = string
}
variable "db_port" {
  type = number
}
variable "db_username" {
  type = string
}
variable "db_password" {
  type = string
}
variable "storage_bucket_name" {
  type = string
}
variable "asg_min_size" {
  type = number
}
variable "asg_max_size" {
  type = number
}
resource "aws_elastic_beanstalk_application" "app_instance" {
  name        = var.app_name
  description = var.app_name
}

resource "aws_elastic_beanstalk_environment" "app_instance_environment" {
  name                = var.app_environment_name
  application         = aws_elastic_beanstalk_application.app_instance.name
  solution_stack_name = "Corretto 11 version 3.2.1 64bit Amazon Linux 2 v3.2.1 running Corretto 11"
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
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "db_address"
    value     = var.db_address
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "db_endpoint"
    value     = var.db_endpoint
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "db_port"
    value     = var.db_port
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "db_username"
    value     = var.db_username
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "db_password"
    value     = var.db_password
  }
  setting {
    namespace = "aws:autoscaling:asg"
    name      = "MinSize"
    value     = var.asg_min_size
  }
  setting {
    namespace = "aws:autoscaling:asg"
    name      = "MaxSize"
    value     = var.asg_max_size
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "storage_bucket_name"
    value     = var.storage_bucket_name
  }

  tags = {
    Owner   = var.owner_name
    project = var.project_name
  }
}

output "elasticbeanstalk_app_cname" {
  value = aws_elastic_beanstalk_environment.app_instance_environment.cname
}
output "elasticbeanstalk_app_loadbalancer_endpoint" {
  value = aws_elastic_beanstalk_environment.app_instance_environment.endpoint_url
}
