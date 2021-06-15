
variable "region" {
  default = "eu-west-1"
}

variable "availability_zone" {
  description = "Availability Zone"
  type        = string
  default     = "eu-west-1a"
}

variable "project_name" {
  description = "The name of the project"
  type        = string
  default     = "phoenix"
}

variable "jumpbox_instance_name" {
  description = "Value of the Name tag for the EC2 Jump Box instance"
  type        = string
  default     = "LYIT_JumpBox_ServerInstance"
}

variable "app_instance_name" {
  description = "Value of the Name tag for the EC2 App instance"
  type        = string
  default     = "LYIT_App_ServerInstance"
}

variable "dbase_instance_name" {
  description = "Value of the Name tag for the EC2 DBase instance"
  type        = string
  default     = "LYIT_DBase_ServerInstance"
}

variable "mgmt_instance_name" {
  description = "Value of the Name tag for the EC2 Mgmt instance"
  type        = string
  default     = "LYIT_Mgmt_ServerInstance"
}


variable "iam_cdc_lyit" {
  description = "Tag name for IAM"
  type        = string
  default     = "IAM_KMN_CDC_LYIT"
}


variable "iac_cdc_lyit" {
  description = "Tag name for infrastructure"
  type        = string
  default     = "IaC in Kay McNulty CDC LYIT"
}

variable "priv_cdc_lyit" {
  description = "Private subnet infrastructure"
  type        = string
  default     = "Priv_Sub in Kay McNulty CDC LYIT"
}

variable "pub_cdc_lyit" {
  description = "Public subnet infrastructure"
  type        = string
  default     = "Pub_Sub in Kay McNulty CDC LYIT"
}


variable "owner_name" {
  description = "Owner of the Infrastructure"
  type        = string
  default     = "Damien Gallagher"
}

variable "aws_profile" {
  description = "The aws profile to use"
  type        = string
  default     = "default"
}

variable "key_name" {
  description = "The name of the key to use"
  type        = string
  default     = "lyit_key"
}

variable "user_name" {
  description = "The name of the user running the terraform commands"
  type        = string
  default     = "damien"
}

variable "instance_type" {
  description = "The instance type to use when creating ec2 instances"
  type        = string
  default     = "t2.micro"
}