variable "owner_name" {
  type = string
}
variable "project_name" {
  type = string
}

variable "dbase_instance_name" {
  type = string
}
variable "dbase_username" {
  type = string
}
variable "dbase_password" {
  type = string
}
variable "dbase_subnet_group_name" {
  type = string
}
variable "dbase_instance_type" {
  type = string
}
variable "dbase_engine" {
  type = string
}
variable "dbase_engine_version" {
  type = string
}
variable "dbase_allocated_storage" {
  type = number
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

variable "private_subnet_dbase_1_id" {
  type = string
}
variable "private_subnet_dbase_2_id" {
  type = string
}
resource "aws_db_subnet_group" "db_subnet_group" {
  name       = var.dbase_subnet_group_name
  subnet_ids = [var.private_subnet_dbase_1_id, var.private_subnet_dbase_2_id]

  tags = {
    Name = var.dbase_subnet_group_name
  }
}

resource "aws_db_instance" "db_instance" {
  allocated_storage    = var.dbase_allocated_storage
  engine               = var.dbase_engine
  engine_version       = var.dbase_engine_version
  instance_class       = var.dbase_instance_type
  name                 = var.dbase_instance_name
  username             = var.dbase_username
  password             = var.dbase_password
  db_subnet_group_name = var.dbase_subnet_group_name
  security_group_names = [var.general_sg_id, var.app_sg_id, var.dbase_sg_id]

  tags = {
    Name  = var.dbase_instance_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}


/*output "dbase_instance_private_ip" {
  value = aws_instance.dbase_instance.private_ip
}*/
