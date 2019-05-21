#!/bin/bash

curl -i -X POST -H "Content-Type:application/json" -H "Accept:application/json" http://localhost:8080/eim/api/agent/controllability/iagent0/stress -d '{
"exec": "EXECBEAT",
"component": "EIM",
"packetLoss": "",
"stressNg": "4",
"dockerized": "no",
"cronExpression": "@every 60s"
}'
