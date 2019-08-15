#!/usr/bin/env bash
sudo apt update
sudo apt install -y python3-pip
sudo pip3 install awscli
grep 'The default' /home/bitnami/bitnami_credentials | cut -d \' -f4 > password.txt
#aws ssm put-parameter --name "prod-mongo-password" --type SecureString --overwrite --region us-west-2  --value $(grep 'The default' /home/bitnami/bitnami_credentials | cut -d \' -f4)