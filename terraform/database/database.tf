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

resource "random_string" "db_password" {
  length  = 16
  special = false
}

resource "aws_db_subnet_group" "db_subnet_group" {
  name       = var.dbase_subnet_group_name
  subnet_ids = [var.private_subnet_dbase_1_id, var.private_subnet_dbase_2_id]

  tags = {
    Name = var.dbase_subnet_group_name
  }
}

resource "aws_db_instance" "db_instance" {
  identifier             = var.dbase_instance_name
  allocated_storage      = var.dbase_allocated_storage
  engine                 = var.dbase_engine
  engine_version         = var.dbase_engine_version
  instance_class         = var.dbase_instance_type
  name                   = var.dbase_instance_name
  username               = var.dbase_username
  password               = random_string.db_password.result
  skip_final_snapshot    = true
  db_subnet_group_name   = var.dbase_subnet_group_name
  vpc_security_group_ids = [var.general_sg_id, var.app_sg_id, var.dbase_sg_id]

  tags = {
    Name    = var.dbase_instance_name
    Owner   = var.owner_name
    project = var.project_name
  }
  depends_on = [aws_db_subnet_group.db_subnet_group]
}


output "dbase_instance_address" {
  value = aws_db_instance.db_instance.address
}
output "dbase_instance_endpoint" {
  value = aws_db_instance.db_instance.endpoint
}
output "dbase_instance_port" {
  value = aws_db_instance.db_instance.port
}

output "dbase_password" {
  value = random_string.db_password.result
}