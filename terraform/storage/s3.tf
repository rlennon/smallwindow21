variable "storage_bucket_prefix" {
  type = string
}

variable "owner_name" {
  type = string
}

variable "project_name" {
  type = string
}

variable "region" {
  type = string
}

data "aws_caller_identity" "current" {}

resource "aws_s3_bucket" "storage_bucket" {
  bucket = "${var.storage_bucket_prefix}-${var.region}-${data.aws_caller_identity.current.account_id}"
  acl    = "private"

  tags = {
    Name    = "${var.storage_bucket_prefix}-${var.region}-${data.aws_caller_identity.current.account_id}"
    Owner   = var.owner_name
    project = var.project_name
  }
}

output "storage_bucket_name" {
  value = aws_s3_bucket.storage_bucket.id
}
