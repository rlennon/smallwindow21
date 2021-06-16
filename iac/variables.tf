
variable "region" {
  default = "eu-west-1"
}

variable "availability_zone_1" {
  description = "Availability Zone 1"
  type        = string
  default     = "eu-west-1a"
}
variable "availability_zone_2" {
  description = "Availability Zone 2"
  type        = string
  default     = "eu-west-1b"
}
variable "project_name" {
  description = "The name of the project"
  type        = string
  default     = "Small Window 21"
}


variable "app_name" {
  description = "Name of the elastic beanstalk application"
  type        = string
  default     = "SmallWindow21"
}
variable "app_environment_name" {
  description = "Name of the application environment"
  type        = string
  default     = "develop"
}

variable "dbase_instance_name" {
  description = "Value of the Name tag for the EC2 DBase instance"
  type        = string
  default     = "SmallWindow21DB"
}

variable "base_resource_name" {
  description = "Tag name for infrastructure"
  type        = string
  default     = "Small Window 21"
}

variable "private_subnet" {
  description = "Private subnet infrastructure"
  type        = string
  default     = "Private Subnet - Small Window 21"
}

variable "owner_name" {
  description = "Owner of the Infrastructure"
  type        = string
  default     = "SmallWindow21 Team"
}

variable "aws_profile" {
  description = "The aws profile to use"
  type        = string
  default     = "default"
}

variable "instance_type" {
  description = "The instance type to use when creating ec2 instances"
  type        = string
  default     = "t2.micro"
}

variable "eb_profile_name" {
  description = "The name of the profile to use for elastic beanstalk"
  type        = string
  default     = "SmallWindow21-Profile"
}

variable "eb_role_name" {
  description = "The name of the role to use for elastic beanstalk"
  type        = string
  default     = "SmallWindow21-Role"
}
