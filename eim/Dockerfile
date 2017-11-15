FROM williamyeh/ansible:ubuntu14.04-onbuild

# make sure the package repository is up to date
RUN \
	apt-get update && apt-get -y upgrade && \
	apt-get install -y software-properties-common && \
	apt-get install -y wget && \
	apt-get install -y unzip && \
	apt-get install -y vim && \
	# install default jdk
	apt-get -y install default-jdk && apt-get clean && \
	update-alternatives --display java && \
	echo "JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64" >> /etc/environment && \
	# install tomcat7
	apt-get -y install tomcat7 && \
	# set catalina path
	echo "JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64" >> /etc/environment && \
	echo "CATALINA_HOME=/var/lib/tomcat7" >> /etc/environment && \
	echo "CATALINA_BASE=/var/lib/tomcat7" >> /etc/environment && \	
	# do not check host key
	echo "host_key_checking = False" >> /etc/ansible/ansible.cfg && \
	# add sut to ansible hosts, inside targethosts group
#	printf "[targethosts]\nsut\n" >> /etc/ansible/hosts && \
	# folder for logs
	mkdir -p /var/log/eim && \
	# folder to store executed playbooks
	mkdir -p /var/log/executed_playbooks && \
	# folder for beats
	mkdir -p /var/ansible/ssh && \
	mkdir -p /var/ansible/ssh/hosts && \
        mkdir -p /var/ansible/beats/ansible-beats-master && \
        wget https://github.com/aya/ansible-beats/archive/master.zip -P /var/ansible/beats && \
        unzip /var/ansible/beats/master.zip -d /var/ansible/beats && \
	# tomcat7 is executed as root
	sed -i 's/=tomcat7/=root/g' /etc/default/tomcat7 && \
	# generate ssh keys
#	mkdir -p /tmp/ssh && \
#	echo "echo -e 'y\n' | ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa 1> /dev/null" >> /etc/bash.bashrc && \
#	echo "ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa" >> /etc/bash.bashrc && \ 
	# copy generated public key
#	echo "cp /root/.ssh/id_rsa.pub /tmp/ssh" >> /etc/bash.bashrc
	mkdir -p /root/.ssh/


# open default tomcat port
EXPOSE 8080

# ssh
COPY ansible/ssh/playbook-ssh-template.yml  /var/ansible/ssh
COPY ansible/ssh/run-ssh-template.sh /var/ansible/ssh
COPY ansible/ssh/playbook-delete-ssh-template.yml  /var/ansible/ssh
COPY ansible/ssh/run-delete-ssh-template.sh /var/ansible/ssh

 # beats
COPY ansible/beats/run-beats-template.sh /var/ansible/beats
COPY ansible/beats/ansible-beats-master/playbook-beats-output-logstash-template.yml /var/ansible/beats/ansible-beats-master
COPY ansible/beats/run-delete-beats-template.sh /var/ansible/beats
COPY ansible/beats/ansible-beats-master/playbook-delete-beats-output-logstash-template.yml /var/ansible/beats/ansible-beats-master

# restart tomcat server to refresh the value of the properties 
COPY tomcat/restart_tomcat.sh /var/tomcat/restart_tomcat.sh
RUN chmod +x /var/tomcat/restart_tomcat.sh

#COPY elastest-eim/target/elastest-instrumentation-manager-0.0.1-SNAPSHOT.war /var/lib/tomcat7/webapps/eim.war
COPY eim-rest-api/target/eim-rest-api-0.0.1-SNAPSHOT.war /var/lib/tomcat7/webapps/eim.war

#RUN find /var/ansible/ssh/run-ssh.sh -type f -exec chmod 755 {} \; &&\
#	find /var/ansible/beats/run-beats.sh -type f -exec chmod 755 {} \;

# start tomcat7
ADD run.sh /root/run.sh
RUN chmod +x /root/run.sh
CMD ["/root/run.sh"]
