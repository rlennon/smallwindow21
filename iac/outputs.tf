output "elasticbeanstalk_app_endpoint" {
  value = module.compute.elasticbeanstalk_app_endpoint
}

output "dbase_instance_address" {
  value = module.database.dbase_instance_address
}
output "dbase_instance_endpoint" {
  value = module.database.dbase_instance_endpoint
}
output "dbase_instance_port" {
  value = module.database.dbase_instance_port
}

