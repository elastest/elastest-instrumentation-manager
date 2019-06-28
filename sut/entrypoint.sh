#!/bin/bash

# A. elastest user created with elastest password:
useradd -ms /bin/bash elastest;
usermod -aG sudo elastest; 
echo "elastest:elastest" | chpasswd; cd /root/.ssh; 

# B. SSH keys generated without passphrase:
##ssh-keygen -q -t rsa -N '' -f id_rsa; 
cp id_rsa.pub authorized_keys; cd /; 
service ssh restart; 

# pending to create /home/elastest/.ssh folder and assign it to elastest user
# ssh-keygen -q -t rsa -N '' -f /home/elastest/.ssh/id_rsa; 
# cp /home/elastest/.ssh/id_rsa.pub /home/elastest/.ssh/authorized_keys; 
# service ssh restart;

# The container will run as long as the script is running, that's why
# we need something long-lived here
#exec tail -f /var/log/dpkg.log
#FREQ_TIME_PRINT_DATE="${FREQ_TIME:10}"
#echo "FREQ_TIME_PRINT_DATE = " $FREQ_TIME_PRINT_DATE
#watch -n $FREQ_TIME_PRINT_DATE ./print_date.sh 
cd /
watch -n 10 ./print_date.sh
