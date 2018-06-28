#!/bin/bash

ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa
/etc/init.d/tomcat7 start

# The container will run as long as the script is running, that's why
# we need something long-lived here
exec tail -f /var/log/tomcat7/catalina.out
# exec tail -f /var/log/tomcat7/eim-rest-api.log
