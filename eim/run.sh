#!/bin/bash


# Waiting MYSQL service is ready #
echo "Timezone:" $HOST_TIMEZONE
cp /usr/share/zoneinfo/$HOST_TIMEZONE /etc/localtime

echo $HOST_TIMEZONE >  /etc/timezone

while  ! nc -z eim-mysql 3306 ; do
    echo "MySQL server is not ready in address 'emi_mysql' and port 3306"
    sleep 2
done


########################################################

ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa
/etc/init.d/tomcat7 start

# The container will run as long as the script is running, that's why
# we need something long-lived here
exec tail -f /var/log/tomcat7/catalina.out
# exec tail -f /var/log/tomcat7/eim-rest-api.log
