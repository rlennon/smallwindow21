module "security" {
  source          = "./security"
  project_name    = var.project_name
  vpc_id          = module.networking.vpc_id
  eb_profile_name = var.eb_profile_name
  eb_role_name    = var.eb_role_name
}

module "networking" {
  source                   = "./networking"
  base_resource_name       = var.base_resource_name
  owner_name               = var.owner_name
  project_name             = var.project_name
  availability_zone_1        = var.availability_zone_1
  availability_zone_2        = var.availability_zone_2
  public_subnet_base_name  = var.private_subnet
  private_subnet_base_name = var.private_subnet
}

module "compute" {
  source                 = "./compute"
  owner_name             = var.owner_name
  project_name           = var.project_name
  app_name               = var.app_name
  app_environment_name   = var.app_environment_name
  instance_type          = var.instance_type
  general_sg_id          = module.security.general_sg_id
  app_sg_id              = module.security.app_sg_id
  dbase_sg_id            = module.security.dbase_sg_id
  vpc_id                 = module.networking.vpc_id
  public_subnet_id       = module.networking.public_subnet_id
  private_subnet_app_id  = module.networking.private_subnet_app_id
  eb_instance_profile_id = module.security.eb_instance_profile_id
}

module "database" {
  source                    = "./database"
  owner_name                = var.owner_name
  project_name              = var.project_name
  dbase_instance_name       = var.dbase_instance_name
  instance_type             = var.instance_type
  general_sg_id             = module.security.general_sg_id
  app_sg_id                 = module.security.app_sg_id
  dbase_sg_id               = module.security.dbase_sg_id
  public_subnet_id          = module.networking.public_subnet_id
  private_subnet_dbase_1_id = module.networking.private_subnet_dbase_1_id
  private_subnet_dbase_2_id = module.networking.private_subnet_dbase_2_id
}
