FROM williamyeh/ansible:ubuntu14.04-onbuild


# Start the tomcat (and leave it hanging)
##CMD service tomcat7 start && tail -f /var/lib/tomcat7/logs/catalina.out

# make sure the package repository is up to date
RUN apt-get update && apt-get -y upgrade
RUN apt-get install -y software-properties-common
RUN apt-get install wget
RUN apt-get install unzip

# install default jdk
RUN apt-get -y install default-jdk && apt-get clean
RUN update-alternatives --display java
RUN echo "JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64" >> /etc/environment

# generate ssh keys
RUN ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa

# install tomcat7
RUN apt-get -y install tomcat7

# set catalina path
RUN echo "JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64" >> /etc/environment
RUN echo "CATALINA_HOME=/var/lib/tomcat7" >> /etc/environment

# open default tomcat port
EXPOSE 8080

# do not check host key
RUN echo "host_key_checking = False" >> /etc/ansible/ansible.cfg

# add sut to ansible hosts, inside targethosts group
RUN printf "[targethosts]\nsut\n" >> /etc/ansible/hosts

# folder for logs
RUN mkdir -p /var/logs/eim

# folder for beats
RUN \
	mkdir -p /var/ansible/ssh && \
	mkdir -p /var/ansible/beats/ansible-beats-master && \
	wget https://github.com/aya/ansible-beats/archive/master.zip -P /var/ansible/beats && \
	unzip /var/ansible/beats/master.zip -d /var/ansible/beats
COPY ansible/ssh/playbook-ssh.yml  /var/ansible/ssh
COPY ansible/ssh/run-ssh.sh /var/ansible/ssh
COPY ansible/beats/run-beats.sh /var/ansible/beats
COPY ansible/beats/ansible-beats-master/playbook-beats-output-logstash.yml /var/ansible/beats/ansible-beats-master

RUN find /var/ansible/ssh/run-ssh.sh -type f -exec chmod 755 {} \; &&\
	find /var/ansible/beats/run-beats.sh -type f -exec chmod 755 {} \;
# tomcat7 is executed as root
RUN sed -i 's/=tomcat7/=root/g' /etc/default/tomcat7

# start tomcat7
CMD service tomcat7 start 
