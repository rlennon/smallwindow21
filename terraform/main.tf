module "security" {
  source               = "./security"
  project_name         = var.project_name
  vpc_id               = module.networking.vpc_id
  eb_profile_name      = var.eb_profile_name
  eb_role_name         = var.eb_role_name
  eb_service_role_name = var.eb_service_role_name
  storage_bucket_name  = module.storage.storage_bucket_name
  depends_on           = [module.storage.storage_bucket_name]
}

module "networking" {
  source                   = "./networking"
  base_resource_name       = var.base_resource_name
  owner_name               = var.owner_name
  project_name             = var.project_name
  availability_zone_1      = var.availability_zone_1
  availability_zone_2      = var.availability_zone_2
  public_subnet_base_name  = var.public_subnet_base_name
  private_subnet_base_name = var.private_subnet_base_name
}

module "database" {
  source                    = "./database"
  owner_name                = var.owner_name
  project_name              = var.project_name
  dbase_instance_name       = var.dbase_instance_name
  dbase_db_name             = var.dbase_db_name
  dbase_username            = var.dbase_username
  dbase_subnet_group_name   = var.dbase_subnet_group_name
  dbase_instance_type       = var.dbase_instance_type
  dbase_engine              = var.dbase_engine
  dbase_engine_version      = var.dbase_engine_version
  dbase_allocated_storage   = var.dbase_allocated_storage
  general_sg_id             = module.security.general_sg_id
  app_sg_id                 = module.security.app_sg_id
  dbase_sg_id               = module.security.dbase_sg_id
  private_subnet_dbase_1_id = module.networking.private_subnet_dbase_1_id
  private_subnet_dbase_2_id = module.networking.private_subnet_dbase_2_id
}

module "storage" {
  source                = "./storage"
  owner_name            = var.owner_name
  project_name          = var.project_name
  storage_bucket_prefix = var.storage_bucket_prefix
  region                = var.region
}

module "compute" {
  source                      = "./compute"
  owner_name                  = var.owner_name
  project_name                = var.project_name
  app_name                    = var.app_name
  app_environment_name        = var.app_environment_name
  instance_type               = var.instance_type
  general_sg_id               = module.security.general_sg_id
  app_sg_id                   = module.security.app_sg_id
  dbase_sg_id                 = module.security.dbase_sg_id
  vpc_id                      = module.networking.vpc_id
  public_subnet_id            = module.networking.public_subnet_id
  private_subnet_app_id       = module.networking.private_subnet_app_id
  eb_instance_profile_id      = module.security.eb_instance_profile_id
  eb_server_port              = var.eb_server_port
  db_address                  = module.database.dbase_instance_address
  db_endpoint                 = module.database.dbase_instance_endpoint
  db_port                     = module.database.dbase_instance_port
  db_name                     = module.database.dbase_db_name
  db_username                 = var.dbase_username
  db_password                 = module.database.dbase_password
  storage_bucket_name         = module.storage.storage_bucket_name
  asg_min_size                = var.asg_min_size
  asg_max_size                = var.asg_max_size
  eb_stream_logs              = var.eb_stream_logs
  eb_delete_logs_on_terminate = var.eb_delete_logs_on_terminate
  eb_log_retention_days       = var.eb_log_retention_days
  eb_service_role_arn         = module.security.eb_service_role_arn
  eb_max_count_versions       = var.eb_max_count_versions
  eb_delete_source_from_s3    = var.eb_delete_source_from_s3
  eb_health_endpoint          = var.eb_health_endpoint
  wait_for_ready_timeout      = var.eb_wait_for_ready_timeout
  depends_on = [
    module.database.dbase_instance_address,
    module.database.dbase_instance_endpoint,
    module.database.dbase_instance_port,
    module.storage.storage_bucket_name,
    module.database.dbase_password,
    module.security.eb_service_role_arn
  ]
}
