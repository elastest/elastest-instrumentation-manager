FROM ubuntu:14.04

RUN \
	apt-get update && \
	apt-get -y install apt-transport-https && \
	apt-get -y install openssh-server && \
	apt-get -y install vim && \
#        useradd -ms /bin/bash elastest && \
#	usermod -aG sudo elastest && \
#	echo "elastest:elastest" | chpasswd && \
	mkdir -p /var/run/sshd && \
	mkdir -p /root/.ssh && \
##	mkdir -p /tmp/ssh && \
	echo "service ssh start" >> /etc/bash.bashrc 
## && \
#	printf "if [[ $EUID -eq 0 ]]; then\ncp /tmp/ssh/id_rsa.pub /root/.ssh/authorized_keys\nfi\n" >> /etc/bash.bashrc
##	echo "cp /tmp/ssh/id_rsa.pub /root/.ssh/authorized_keys 1> /dev/null" >> /etc/bash.bashrc

EXPOSE 22

# start ssh
#CMD ["/usr/sbin/sshd", "-D"]
# RUN echo "service ssh start" >> /etc/bash.bashrc

COPY entrypoint.sh /entrypoint.sh

RUN chmod 777 entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
