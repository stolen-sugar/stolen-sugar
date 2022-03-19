aws cloudformation delete-stack --region us-east-2 --stack-name stolen-sugar-createtables

sleep 60

aws cloudformation create-stack --region us-east-2 --stack-name stolen-sugar-createtables --template-body file://tables.template.yml --capabilities CAPABILITY_IAM

sleep 10

aws dynamodb batch-write-item --region us-east-2 --request-items file://seed.json