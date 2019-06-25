FROM kibana:6.8.1

MAINTAINER  Fernando Mendez <fernando.mendez@atos.net>

USER root

# Set Image Labels
ARG GIT_COMMIT=unspecified
LABEL git_commit=$GIT_COMMIT

ARG COMMIT_DATE=unspecified
LABEL commit_date=$COMMIT_DATE

ARG VERSION=unspecified
LABEL version=$VERSION 

RUN yum -y install net-tools
