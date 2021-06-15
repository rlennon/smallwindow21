output "jump_box_public_ip" {
  value = module.compute.jump_box_public_ip
}
output "app_instance_public_ip" {
  value = module.compute.app_instance_public_ip
}
output "app_instance_private_ip" {
  value = module.compute.app_instance_private_ip
}
output "dbase_instance_private_ip" {
  value = module.compute.dbase_instance_private_ip
}
output "mgmt_instance_private_ip" {
  value = module.compute.mgmt_instance_private_ip
}
output "ssh_key_path" {
  value = module.security.ssh_key_path
}