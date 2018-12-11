#!/bin/bash

ELASTESTURL=$1
PRIVATEKEY=$2
SUTIP=$3


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
echo Creating Project
PROJ=$(curl -s -H "Content-Type: application/json" -d '{ "id": 0, "name": "EIMe2e" }' "$ELASTESTURL/api/project")
echo $PROJ
PROJID=`echo "$PROJ" | tr '\n' ' ' | grep -wo .id.:[0-9]* | cut -d ':' -f 2`
echo Proj ID: $PROJID
checknonempty "$PROJID"

# SuT creation
echo Creating SuT
sed -i -e "s|PROJID|$PROJID|g" -e "s|PRIVATEKEY|\"$PRIVATEKEY\"|g" -e "s|SUTIP|\"$SUTIP\"|g" sutdesc.json
SUT=$(curl -s -H "Content-Type: application/json" -d @sutdesc.json "$ELASTESTURL/api/sut")
echo $SUT
SUTID=`echo "$SUT" | tr '\n' ' ' | grep -wo eimConfig.:..id.:[0-9]* | cut -d ':' -f 3`
echo SuT ID: $SUTID
checknonempty "$SUTID"

#echo SUT has been created successfully with the ID: $SUTID
#cleanexit 0