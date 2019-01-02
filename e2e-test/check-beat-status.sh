#!/bin/bash

ELASTESTURL=$1
PROJID=$2

function cleanexit() {
    # Project destruction
    echo Destroying project
    curl -X "DELETE" "$ELASTESTURL/api/project/$PROJID"
    exit $1
}

function checknonempty() {
    if [[ $1X = "X" ]]; then
        echo Empty string
        cleanexit -5
    fi
}


# Project creation
echo "Checking beats services"
#AGENTS=$(curl -s -H "Content-Type: application/json" "$ELASTESTURL/eim/api/agent")
AGENTS=$(curl -s -H "Content-Type: application/json" "http://172.18.0.9:8080/eim/api/eim/api/agent")
echo $AGENTS

RESULT=`echo "$AGENTS" | grep -wo .status.:[0-9]* | cut -d ':' -f 2`

if [ "$RESULT" = "200" ]; then
     echo Test successful
     cleanexit 0
fi

cleanexit -5