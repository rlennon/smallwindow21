output "elasticbeanstalk_app_cname" {
  value = module.compute.elasticbeanstalk_app_cname
}

output "elasticbeanstalk_app_loadbalancer_endpoint" {
  value = module.compute.elasticbeanstalk_app_loadbalancer_endpoint
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

output "storage_bucket_name" {
  value = module.storage.storage_bucket_name
}



