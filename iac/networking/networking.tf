variable "base_resource_name" {
    type = string
}
variable "owner_name" {
    type = string
}
variable "project_name" {
    type = string
}
variable "availability_zone" {
    type = string
}
variable "public_subnet_base_name" {
    type = string
}
variable "private_subnet_base_name" {
    type = string
}

resource "aws_vpc" "main" {
  #in the assignment don't forget to change the network numbers!
  cidr_block = "192.168.0.0/16"

  tags = {
    Name  = "${var.base_resource_name} VPC"
    Owner = var.owner_name
    proj  = var.project_name
  }
}

resource "aws_subnet" "public_subnet" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "192.168.1.0/24"
  availability_zone       = var.availability_zone
  map_public_ip_on_launch = true

  tags = {
    Name  = "${var.public_subnet_base_name} Public Subnet"
    Owner = var.owner_name
    proj  = var.project_name
  }
}

resource "aws_subnet" "private_subnet_dbase" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "192.168.2.0/24"
  availability_zone       = var.availability_zone
  map_public_ip_on_launch = false

  tags = {
    Name  = "${var.private_subnet_base_name} Private Dbase Subnet"
    Owner = var.owner_name
    proj  = var.project_name
  }
}

resource "aws_subnet" "private_subnet_mgmt" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "192.168.3.0/24"
  availability_zone       = var.availability_zone
  map_public_ip_on_launch = false

  tags = {
    Name  = "${var.private_subnet_base_name} Private Mgmt Subnet"
    Owner = var.owner_name
    proj  = var.project_name
  }
}

resource "aws_internet_gateway" "inet_gateway" {
  vpc_id = aws_vpc.main.id

  tags = {
    Name  = var.base_resource_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}

resource "aws_route_table" "public_routing_table" {
  vpc_id = aws_vpc.main.id

  route { #traffic from anywhere is routed
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.inet_gateway.id
  }

  tags = {
    Name  = var.base_resource_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}

resource "aws_route_table_association" "public_routing_table_assoc" {
  subnet_id      = aws_subnet.public_subnet.id
  route_table_id = aws_route_table.public_routing_table.id
}

resource "aws_eip" "nat_elastic_ip" {
  vpc = true

  tags = {
    Name  = var.base_resource_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}


resource "aws_nat_gateway" "nat_gateway" {
  allocation_id = aws_eip.nat_elastic_ip.id
  subnet_id     = aws_subnet.public_subnet.id

  tags = {
    Name  = var.base_resource_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}

# private routing table
resource "aws_route_table" "private_routing_table" {
  vpc_id = aws_vpc.main.id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.nat_gateway.id
  }

  tags = {
    Name  = var.base_resource_name
    Owner = var.owner_name
    proj  = var.project_name
  }
}

#
resource "aws_route_table_association" "private_routing_table_assoc_dbase" {
  subnet_id      = aws_subnet.private_subnet_dbase.id
  route_table_id = aws_route_table.private_routing_table.id
}

resource "aws_route_table_association" "private_routing_table_assoc_mgmt" {
  subnet_id      = aws_subnet.private_subnet_mgmt.id
  route_table_id = aws_route_table.private_routing_table.id
}


output "vpc_id" {
    value = aws_vpc.main.id
}

output "public_subnet_id" {
    value = aws_subnet.public_subnet.id
}
output "private_subnet_dbase_id" {
    value = aws_subnet.private_subnet_dbase.id
}
output "private_subnet_mgmt_id" {
    value = aws_subnet.private_subnet_mgmt.id
}