provider "aws" {
  profile = var.profile
  region = var.region
}
resource "aws_security_group" "mongo-access" {
  name = "mongo-access"
  description = "allow direct access to mongodb and ssh"

  //  ingress {
  //    from_port = 27017
  //    protocol = "tcp"
  //    to_port = 27017
  //    cidr_blocks = ["::/0"]
  //  }
  //  ingress {
  //    from_port = 22
  //    protocol = "tcp"
  //    to_port = 22
  //    cidr_blocks = ["::/0"]
  //  }
}

//todo: always auto-scaling group
// ELB + asg
//resource "aws_load_balancer_backend_server_policy" "" {
//  instance_port = 0
//  load_balancer_name = ""
//}
resource "aws_instance" "app-api" {
  ami = "ami-0f2176987ee50226e"
  instance_type = "t2.micro"
  key_name = var.keyName

  user_data = <<EOF
  #!/bin/bash
   wget https://d3pxv6yz143wms.cloudfront.net/11.0.3.7.1/java-11-amazon-corretto-devel-11.0.3.7-1.x86_64.rpm -O /home/ec2-user/java-11.rpm
   sudo yum install -y /home/ec2-user/java-11.rpm
  EOF
}

//resource "aws_instance" "mongo" {
//  ami = "ami-01d7e588e0f2048a4"
//  instance_type = "t2.micro"
//  key_name = var.keyName
//  security_groups = [
//    "${aws_security_group.mongo-access.name}"]
//  provisioner "local-exec" {
//
//    command = "echo ${aws_instance.mongo.public_dns} > instances.txt"
//
//  }
//  depends_on = [
//    aws_security_group.mongo-access]
//}