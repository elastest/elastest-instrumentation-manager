FROM ubuntu:14.04

RUN \
	apt-get update && \
	apt-get -y install apt-transport-https && \
	apt-get -y install openssh-server && \
        useradd -ms /bin/bash elastest && \
	usermod -aG sudo elastest && \
	echo "elastest:elastest" | chpasswd && \
	mkdir -p /var/run/sshd

EXPOSE 22

# start ssh
#CMD ["/usr/sbin/sshd", "-D"]
RUN echo "service ssh start" >> /etc/bash.bashrc
