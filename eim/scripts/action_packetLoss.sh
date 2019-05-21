#!/bin/bash

curl -i -X POST -H "Content-Type:application/json" -H "Accept:application/json" http://localhost:8080/eim/api/agent/controllability/iagent0/packetloss -d '{
"exec": "EXECBEAT",
"component": "EIM",
"packetLoss": "0.01",
"stressNg": "",
"dockerized": "yes",
"cronExpression": "@every 60s"
}'


