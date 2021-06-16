# Setup terraform
cd terraform
terraform init

# Apply Configuration
terraform apply -auto-approve -var="aws_profile=lyit" -var="dbase_username=smallwindow21" -var="dbase_password=password"

# Destroy Configuration
terraform destroy -auto-approve -var="aws_profile=lyit" -var="dbase_username=smallwindow21" -var="dbase_password=password"

# format terraform
terraform fmt -recursive