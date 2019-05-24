#!/bin/bash

ELASTESTURL=$1
SUTIP=$2
PRIVATEKEY=$(cat pKey)


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
echo "Creating Project"
PROJ=$(curl -s -H "Content-Type: application/json" -d '{ "id": 0, "name": "EIMe2e" }' "$ELASTESTURL/api/project")
echo $PROJ

PROJID=`echo "$PROJ" | tr '\n' ' ' | grep -wo .id.:[0-9]* | cut -d ':' -f 2`
echo Proj ID: $PROJID
checknonempty "$PROJID"

pwd
touch projid_file
echo $PROJID > projid_file

# SuT creation
echo Creating SuT
sed -i -e "s|PROJID|$PROJID|g" -e "s|PRIVATEKEY|\"$PRIVATEKEY\"|g" -e "s|SUTIP|\"$SUTIP\"|g" sutdesc.json
SUT=$(curl -s -H "Content-Type: application/json" -d @sutdesc.json "$ELASTESTURL/api/sut")
echo $SUT

SUTID=`echo "$SUT" | tr '\n' ' ' | grep -wo eimConfig.:..id.:[0-9]* | cut -d ':' -f 3`
echo SuT ID: $SUTID
checknonempty "$SUTID"

# T-Job creation
echo Creating T-Job
#sed -i -e "s|PROJID|$PROJID|g" -e "s|SUTID|$SUTID|g" tjobdesc.json
#TJOB=$(curl -s -H "Content-Type: application/json" -d @tjobdesc.json "$ELASTESTURL/api/tjob")
DESC=`sed "s/PROJID/$PROJID/;s/SUTID/$SUTID/" tjobdesc.txt`
TJOB=$(curl -s -H "Content-Type: application/json" -d "$DESC" "$ELASTESTURL/api/tjob")
echo $TJOB


TJOBID=`echo "$TJOB" | grep -wo .id.:[0-9]*,.name.:.eim.tjob | cut -d ',' -f 1 | cut -d ':' -f 2`
echo TJob ID: $TJOBID
checknonempty "$TJOBID"

# T-Job execution
echo Executing T-Job... 
TJOBEXEC=$(curl -s -H "Content-Type: application/json" -d '{"tJobParams": []}' "$ELASTESTURL/api/tjob/$TJOBID/exec")
echo $TJOBEXEC
TJOBEXECID=`echo "$TJOBEXEC" | tr '\n' ' ' | jq '.monitoringIndex' | sed 's/"//g'`
echo TJobEXEC ID: $TJOBEXECID
checknonempty "$TJOBEXECID"

# Getting result
n=0
while [ $n -le 3000 ]
do
	n=$(( n+1 ))	 # increments $n
	sleep 1
	TJOBEXEC=$(curl -s "$ELASTESTURL/api/tjob/$TJOBID/exec/$TJOBEXECID/result")
	#echo curl -s "$ELASTESTURL/api/tjob/$TJOBID/exec/$TJOBEXECID/result"
	#echo $TJOBEXEC
    if [[ $TJOBEXEC = *"SUCCESS"* ]]; then
        echo Test successful
        cleanexit 0
    fi
    if [[ $TJOBEXEC = *"FAIL"* ]]; then
        echo Test failed
        cleanexit -2
    fi
    if [[ $TJOBEXEC = *"ERROR"* ]]; then
        echo Test erroneous
        cleanexit -3
    fi
done

echo Test took too long
cleanexit -4


