module "security" {
  source       = "./security"
  project_name = var.project_name
  vpc_id       = module.networking.vpc_id
  key_name     = var.key_name
  user_name    = var.user_name
}

module "networking" {
  source                   = "./networking"
  base_resource_name       = var.iac_cdc_lyit
  owner_name               = var.owner_name
  project_name             = var.project_name
  availability_zone        = var.availability_zone
  public_subnet_base_name  = var.priv_cdc_lyit
  private_subnet_base_name = var.priv_cdc_lyit
}

module "compute" {
  source                  = "./compute"
  owner_name              = var.owner_name
  project_name            = var.project_name
  jumpbox_instance_name   = var.jumpbox_instance_name
  app_instance_name       = var.app_instance_name
  dbase_instance_name     = var.dbase_instance_name
  mgmt_instance_name      = var.mgmt_instance_name
  key_name                = var.key_name
  instance_type           = var.instance_type
  general_sg_id           = module.security.general_sg_id
  bastion_sg_id           = module.security.bastion_sg_id
  app_sg_id               = module.security.app_sg_id
  dbase_sg_id             = module.security.dbase_sg_id
  public_subnet_id        = module.networking.public_subnet_id
  private_subnet_dbase_id = module.networking.private_subnet_dbase_id
  private_subnet_mgmt_id  = module.networking.private_subnet_mgmt_id
  depends_on              = [module.security.ssh_key_path]
}