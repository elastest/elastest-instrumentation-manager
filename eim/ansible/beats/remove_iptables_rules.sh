#!/bin/bash

mysql -u root -p"$PASS" -h "$ET_EDM_MYSQL_HOST" <<EOF

echo "Use EIM ..."
USE EIM
echo "Running queries ..."
tam="SELECT count(*) FROM agent_configuration_control WHERE 'PACKETLOSS'>0;"
agent="SELECT '$AGENT_ID' FROM agent_configuration_control;"

for i in "$agent"; do
  for i in "$tam"; do
   	iptable_rule=mysql -e "SELECT 'PACKETLOSS' FROM agent_configuration_control;"
  	sudo iptables -D "$iptable_rule"
  	"DELETE FROM agent_configuration_control WHERE AGENT_ID=$agent;"
   done
done

echo "Rules of SUT reseted"
EOF