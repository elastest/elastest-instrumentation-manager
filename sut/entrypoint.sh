#!/bin/bash

# A. elastest user created with elastest password:
useradd -ms /bin/bash elastest;
usermod -aG sudo elastest; 
echo "elastest:elastest" | chpasswd; cd /root/.ssh; 

# B. SSH keys generated without passphrase:
ssh-keygen -q -t rsa -N '' -f id_rsa; 
cp id_rsa.pub authorized_keys; cd /; 
service ssh restart; 

# The container will run as long as the script is running, that's why
# we need something long-lived here
# exec tail -f /var/log/dpkg.log
