FROM elasticsearch:6.5.0

MAINTAINER  Fernando Mendez <fernando.mendez@atos.net>

# Set Image Labels
ARG GIT_COMMIT=unspecified
LABEL git_commit=$GIT_COMMIT

ARG COMMIT_DATE=unspecified
LABEL commit_date=$COMMIT_DATE

ARG VERSION=unspecified
LABEL version=$VERSION 

RUN apt-get update && apt-get -y install net-tools
