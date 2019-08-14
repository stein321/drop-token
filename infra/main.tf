provider "aws" {
  profile = var.profile
  region = var.region
}
resource "aws_security_group" "mongo-access" {
  name = "mongo-access"
  description = "for accessing mongo"

  //todo: this is insecure, removing later
  ingress {
    description = "ssh access for now"
    from_port = 22
    protocol = "tcp"
    to_port = 22
    cidr_blocks = [
      "0.0.0.0/0"]
  }
  ingress {
    description = "app acess"
    from_port = 8080
    protocol = "tcp"
    to_port = 8080
    cidr_blocks = [
      "0.0.0.0/0"]
  }
  egress {
    description = "Letting apps run free for now "
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [
      "0.0.0.0/0"]
  }
}

resource "aws_security_group" "mongo" {

  name = "mongo"
  description = "allow direct access to default port for mongo mongodb"

  ingress {
    from_port = 27017
    to_port = 27017
    protocol = "tcp"
//    security_groups = [
//      aws_security?_group.mongo-access.arn]
    description = "Default port for mongo"
    cidr_blocks = [
      "0.0.0.0/0"]
  }
  ingress {
    description = "ssh access for now"
    from_port = 22
    protocol = "tcp"
    to_port = 22
    cidr_blocks = [
      "0.0.0.0/0"]
  }
  egress {
    description = "Allow instance to update itself"
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [
      "0.0.0.0/0"]
  }
  depends_on = [
    aws_security_group.mongo-access
  ]
}

//resource "aws_iam_role" "" {
//  assume_role_policy = ""
//}
resource "aws_instance" "app-api" {
  ami = "ami-0f2176987ee50226e"
  instance_type = "t2.micro"
  key_name = var.keyName
  security_groups = [
    "${aws_security_group.mongo-access.name}"]
  iam_instance_profile = "drop-token"
  user_data = <<EOF
  #!/bin/bash
   wget https://d3pxv6yz143wms.cloudfront.net/11.0.3.7.1/java-11-amazon-corretto-devel-11.0.3.7-1.x86_64.rpm -O /home/ec2-user/java-11.rpm
   sudo yum install -y /home/ec2-user/java-11.rpm
   aws s3 sync s3://stein321-droptoken /home/ec2-user/.
   aws ssm get-parameter --name "prod-mongo-password" --with-decryption  --query "Parameter.Value" --output text --region us-west-2
  EOF
}

resource "aws_instance" "mongo" {
  ami = "ami-0fab3e151b002489c"
  instance_type = "t2.micro"
  key_name = var.keyName
  security_groups = [
    "${aws_security_group.mongo.name}"]
  provisioner "local-exec" {
    command = "sleep 10 && cat installAwsCli.sh | ssh -o StrictHostKeyChecking=no -i ~/.ssh/${var.keyName}.pem bitnami@${aws_instance.mongo.public_ip}"
  }

  iam_instance_profile = "Mongo"
  depends_on = [
    aws_security_group.mongo-access
  ]
}