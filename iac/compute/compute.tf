module "linux_ami" {
  source = "./ami-lookup"
  owner  = "amazon"
  name   = "amzn2-ami-hvm*"
}

module "mariadb_ami" {
  source = "./ami-lookup"
  owner  = "aws-marketplace"
  name   = "bitnami-mariadb*"
}
variable "owner_name" {
  type = string
}
variable "project_name" {
  type = string
}
variable "jumpbox_instance_name" {
  type = string
}
variable "app_instance_name" {
  type = string
}
variable "dbase_instance_name" {
  type = string
}
variable "mgmt_instance_name" {
  type = string
}
variable "key_name" {
  type = string
}
variable "instance_type" {
  type = string
}
variable "general_sg_id" {
  type = string
}
variable "bastion_sg_id" {
  type = string
}
variable "app_sg_id" {
  type = string
}
variable "dbase_sg_id" {
  type = string
}
variable "public_subnet_id" {
  type = string
}
variable "private_subnet_dbase_id" {
  type = string
}
variable "private_subnet_mgmt_id" {
  type = string
}

resource "aws_instance" "jump_box" {
  ami           = module.linux_ami.ami_id
  instance_type = var.instance_type
  key_name      = var.key_name

  subnet_id              = var.public_subnet_id
  vpc_security_group_ids = [var.general_sg_id, var.bastion_sg_id]

  tags = {
    Name  = var.jumpbox_instance_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}

resource "aws_instance" "app_instance" {
  ami           = module.linux_ami.ami_id
  instance_type = var.instance_type
  key_name      = var.key_name

  subnet_id              = var.public_subnet_id
  vpc_security_group_ids = [var.general_sg_id, var.app_sg_id]

  tags = {
    Name  = var.app_instance_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}

resource "aws_instance" "dbase_instance" {
  ami           = module.mariadb_ami.ami_id
  instance_type = var.instance_type
  key_name      = var.key_name

  subnet_id              = var.private_subnet_dbase_id
  vpc_security_group_ids = [var.general_sg_id, var.app_sg_id, var.dbase_sg_id]

  tags = {
    Name  = var.dbase_instance_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}

resource "aws_instance" "mgmt_instance" {
  ami           = module.linux_ami.ami_id
  instance_type = var.instance_type
  key_name      = var.key_name

  subnet_id              = var.private_subnet_mgmt_id
  vpc_security_group_ids = [var.general_sg_id, var.app_sg_id]

  tags = {
    Name  = var.mgmt_instance_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}

output "jump_box_public_ip" {
  value = aws_instance.jump_box.public_ip
}
output "app_instance_public_ip" {
  value = aws_instance.app_instance.public_ip
}
output "app_instance_private_ip" {
  value = aws_instance.app_instance.private_ip
}
output "dbase_instance_private_ip" {
  value = aws_instance.dbase_instance.private_ip
}
output "mgmt_instance_private_ip" {
  value = aws_instance.mgmt_instance.private_ip
}