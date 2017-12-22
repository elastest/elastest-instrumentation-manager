#!/bin/bash

# A. elastest user created with elastest password:
useradd -ms /bin/bash elastest;
usermod -aG sudo elastest; 
echo "elastest:elastest" | chpasswd; cd /root/.ssh; 

# B. SSH keys generated without passphrase:
ssh-keygen -q -t rsa -N '' -f id_rsa; 
cp id_rsa.pub authorized_keys; cd /; 
service ssh restart; 
