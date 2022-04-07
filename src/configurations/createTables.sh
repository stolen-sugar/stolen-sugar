aws cloudformation delete-stack --region us-east-2 --stack-name stolen-sugar-createtables

sleep 60

aws cloudformation create-stack --region us-east-2 --stack-name stolen-sugar-createtables --template-body file://tables.template.yml --capabilities CAPABILITY_IAM