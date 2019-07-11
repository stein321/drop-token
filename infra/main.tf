provider "aws" {
  profile = var.profile
  region = var.region
}

resource "aws_instance" "example" {
  ami = "ami-0f2176987ee50226e"
  instance_type = "t2.micro"
  key_name = var.keyName

  provisioner "local-exec" {
    command = "sleep 60 && cat installJava.sh | ssh -o StrictHostKeyChecking=no -i ~/.ssh/${var.keyName}.pem ec2-user@${aws_instance.example.public_ip}"
  }
}