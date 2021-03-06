FROM williamyeh/ansible:ubuntu16.04-onbuild
MAINTAINER  David Rojo <david.rojoa@atos.net> & Fernando Mendez <fernando.mendez@atos.net>

# Set Image Labels
ARG GIT_COMMIT=unspecified
LABEL git_commit=$GIT_COMMIT

ARG COMMIT_DATE=unspecified
LABEL commit_date=$COMMIT_DATE

ARG VERSION=unspecified
LABEL version=$VERSION



# make sure the package repository is up to date
RUN \
	apt-get update && apt-get -y upgrade && \
	apt-get install -y software-properties-common && \
	apt-get install -y wget && \
	apt-get install -y unzip && \
	apt-get install -y vim && \
	apt-get install -y netcat && \
	# Installation of ChaosToolkit && kubernetes dependencies
	apt-get install -y python3 && \
        update-alternatives --install /usr/bin/python python /usr/bin/python3 1 && \
	apt install -y python3-pip && \
    	pip3 install -U chaostoolkit && \
    	pip3 install chaostoolkit-kubernetes && \
        pip3 install -U chaostoolkit-google-cloud-platform && \
        pip3 install --upgrade pip && \
        pip3 install ansible && \
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
        mkdir -p /var/ansible/beats/setup-files && \
#        mkdir -p /var/ansible/beats/ansible-beats-master && \
#        wget https://github.com/aya/ansible-beats/archive/master.zip -P /var/ansible/beats && \
#        unzip /var/ansible/beats/master.zip -d /var/ansible/beats && \
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
COPY ansible/beats/run-install-beats-template.sh /var/ansible/beats
COPY ansible/beats/playbook-install-beats-output-file-logstash-template.yml /var/ansible/beats
COPY ansible/beats/run-delete-beats-template.sh /var/ansible/beats
COPY ansible/beats/playbook-delete-beats-template.yml /var/ansible/beats
COPY ansible/beats/setup-files/filebeat.yml /var/ansible/beats/setup-files
COPY ansible/beats/setup-files/metricbeat.yml /var/ansible/beats/setup-files
COPY ansible/beats/setup-files/packetbeat.yml /var/ansible/beats/setup-files

# beats - EXECBEAT
COPY ansible/beats/playbook-install-beat-execbeat.yml /var/ansible/beats
COPY ansible/beats/playbook-restore-default-iptables-rule-template.yml /var/ansible/beats
COPY ansible/beats/setup-files/execbeat.yml /var/ansible/beats/setup-files
COPY ansible/beats/remove_iptables_rules.sh /var/ansible/beats

# it works with ssh instead of execbeat rule injection
COPY ansible/beats/playbook-ssh-rule-iptables.yml /var/ansible/beats

# ChaosToolkit Configuration and experiments files
COPY ansible/beats/playbook-chaos-template.yml /var/ansible/beats
COPY ansible/beats/config /var/ansible/beats
COPY ansible/beats/gcp.json /var/ansible/beats
COPY ansible/beats/experiment_0.json /var/ansible/beats
COPY ansible/beats/experiment_1.json /var/ansible/beats
COPY ansible/beats/experiment_3.json /var/ansible/beats
COPY ansible/beats/experiment_5.json /var/ansible/beats
COPY ansible/beats/experiment_7.json /var/ansible/beats
COPY ansible/beats/run-chaos.sh /var/ansible/beats

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
#ADD wait.sh /root/wait.sh
#RUN chmod +x /root/wait.sh
#CMD /root/wait.sh && /root/run.sh 
ENTRYPOINT ["/root/run.sh"]
