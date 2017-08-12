FROM logstash

RUN apt-get update && apt-get -y install net-tools

COPY logstash.conf /logstash/

CMD ["-f", "/logstash/logstash.conf"]
