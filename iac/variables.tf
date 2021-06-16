
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
variable "dbase_username" {
  description = "Value of the database username"
  type        = string
}
variable "dbase_password" {
  description = "Value of the database password"
  type        = string
}
variable "dbase_subnet_group_name" {
  description = "Value of the database subnet group name"
  type        = string
  default     = "smallwindow21-dbsubnet-group"
}
variable "dbase_instance_type" {
  description = "Value for the database instance type"
  type        = string
  default     = "db.t3.micro"
}
variable "dbase_engine" {
  description = "Value for the database engine"
  type        = string
  default     = "postgresql"
}
variable "dbase_engine_version" {
  description = "Value for the database engine version"
  type        = string
  default     = "13.2-R1"
}
variable "dbase_allocated_storage" {
  description = "Value for the database allocated storage"
  type        = number
  default     = 10
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
