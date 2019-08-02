FROM ubuntu:16.04

MAINTAINER  Fernando Mendez <fernando.mendez@atos.net>

# Set Image Labels
ARG GIT_COMMIT=unspecified
LABEL git_commit=$GIT_COMMIT
ARG COMMIT_DATE=unspecified
LABEL commit_date=$COMMIT_DATE
ARG VERSION=unspecified
LABEL version=$VERSION 

RUN \
    apt-get update && \
    apt-get install sudo && \
    apt-get -y install python && \
    apt-get -y install apt-transport-https && \
    apt-get -y install openssh-server && \
    apt-get -y install vim && \
    apt-get -y install curl &&\
    useradd -ms /bin/bash elastest && \
    usermod -aG sudo elastest && \
    echo "elastest:elastest" | chpasswd && \
    mkdir -p /var/run/sshd && \
    mkdir -p /root/.ssh && \
##  mkdir -p /tmp/ssh && \
    ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa
##  echo "service ssh start" >> /etc/bash.bashrc 

## && \
#   printf "if [[ $EUID -eq 0 ]]; then\ncp /tmp/ssh/id_rsa.pub /root/.ssh/authorized_keys\nfi\n" >> /etc/bash.bashrc
##  echo "cp /tmp/ssh/id_rsa.pub /root/.ssh/authorized_keys 1> /dev/null" >> /etc/bash.bashrc

EXPOSE 22

# start ssh
#CMD ["/usr/sbin/sshd", "-D"]

# RUN echo "service ssh start" >> /etc/bash.bashrc

COPY entrypoint.sh /entrypoint.sh
COPY print_date.sh /print_date.sh

RUN chmod 777 entrypoint.sh
RUN chmod 777 print_date.sh

ENTRYPOINT ["/entrypoint.sh"]

#CMD export TERM=xterm; watch -n -10 /print_date.sh


