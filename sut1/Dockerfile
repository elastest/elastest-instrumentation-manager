FROM ubuntu:16.04

MAINTAINER  Fernando Mendez <fernando.mendez@atos.net>

# Set Image Labels
ARG GIT_COMMIT=unspecified
LABEL git_commit=$GIT_COMMIT
ARG COMMIT_DATE=unspecified
LABEL commit_date=$COMMIT_DATE
ARG VERSION=unspecified
LABEL version=$VERSION 

COPY . /app

WORKDIR /app

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
    curl "https://bootstrap.pypa.io/get-pip.py" -o "get-pip.py" && \
    python get-pip.py && \
    pip install -r requirements.txt && \
    ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa


EXPOSE 22

COPY /entrypoint.sh /entrypoint.sh
COPY /app.py	/app.py

RUN chmod 777 app.py
RUN chmod 777 entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

CMD ["python","./app.py"]
