FROM mysql:5.7
MAINTAINER  David Rojo <david.rojoa@atos.net>

#EXPOSE ${ET_EIM_SERVER}
#EXPOSE $ET_EIM_SERVER

ENV MYSQL_ROOT_PASSWORD elastest
#ENV MYSQL_DATABASE eim
#ENV MYSQL_USER elastest 
#ENV MYSQL_PASSWORD elastest

ADD 1-model_repository.sql /docker-entrypoint-initdb.d/
ADD 2-setup.sh /docker-entrypoint-initdb.d/
RUN chmod +x /docker-entrypoint-initdb.d/2-setup.sh

COPY . /elastest
WORKDIR /elastest

RUN chmod 777 entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]

CMD ["mysqld"]

# Requires: 
# Create image with:  docker build -t elastest/mysql .
# Instantiate container with: docker run --name elastest-mysql -e MYSQL_ROOT_PASSWORD=elastest -d elastest/mysql
