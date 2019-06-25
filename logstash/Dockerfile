FROM logstash:6.8.1

MAINTAINER  Fernando Mendez <fernando.mendez@atos.net>

# Set Image Labels
ARG GIT_COMMIT=unspecified
LABEL git_commit=$GIT_COMMIT

ARG COMMIT_DATE=unspecified
LABEL commit_date=$COMMIT_DATE

ARG VERSION=unspecified
LABEL version=$VERSION 

USER root

# RUN apt-get update && apt-get -y install net-tools
RUN yum install -y net-tools

COPY logstash.conf /logstash/

CMD ["-f", "/logstash/logstash.conf"]
