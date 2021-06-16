# Apply Configuration
terraform apply -auto-approve -var="aws_profile=lyit"

# Destroy Configuration
terraform destroy -auto-approve -var="aws_profile=lyit"
