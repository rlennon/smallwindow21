variable "owner_name" {
  type = string
}
variable "project_name" {
  type = string
}

variable "dbase_instance_name" {
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
variable "public_subnet_id" {
  type = string
}
variable "private_subnet_dbase_1_id" {
  type = string
}
variable "private_subnet_dbase_2_id" {
  type = string
}
resource "aws_db_subnet_group" "db_subnet_group" {
  name       = "db_subnet_group"
  subnet_ids = [var.private_subnet_dbase_1_id, var.private_subnet_dbase_2_id]

  tags = {
    Name = "My DB subnet group"
  }
}

resource "aws_db_instance" "db_instance" {
  allocated_storage    = 10
  engine               = "postgresql"
  engine_version       = "13.2-R1"
  instance_class       = "db.t3.micro"
  name                 = var.dbase_instance_name
  username             = "foo"
  password             = "foobarbaz"
  skip_final_snapshot  = true
  db_subnet_group_name = "My DB subnet group"
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
