#!/usr/bin/env bash
sudo apt update
sudo apt install -y python3-pip
sudo pip3 install awscli
# todo: create user and store password in ssm
#aws ssm put-parameter --name "prod-mongo-password" --type SecureString --overwrite --region us-west-2  --value $(grep 'The default' /home/bitnami/bitnami_credentials | cut -d \' -f4)