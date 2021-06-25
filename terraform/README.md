# Setup terraform
cd terraform
terraform init

# Apply Configuration
terraform apply -auto-approve -var="aws_profile=lyit"
 
# Destroy Configuration
terraform destroy -auto-approve -var="aws_profile=lyit" 

# format terraform
terraform fmt -recursive