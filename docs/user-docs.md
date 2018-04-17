# ElasTest Instrumentation Manager (EIM): User Guide

## Introduction
EIM is integrated with the ElasTest dashboard and allows to instrumentalize and de-instrumentalize the deployed SuTs. TJobs can also executed to verify that the SuTs are successfully instrumentalized.

#### Prerequisites
1. You need to create a project in ElasTest platform, follow these [steps](https://github.com/elastest/elastest-torm/blob/master/docs/index.md#create-a-project) please
2. You need to create a SuT deployed outside ElasTest. <br>
  2.1. You can deploy a valid SuT with the docker command: `docker run -itd --network=elastest_elastest --name=eim-sut elastest/eim-sut`<br>
  2.2. From deployed SuT you need:<br>
     2.2.1. User: `root` for the proposed SuT<br>&nbsp;&nbsp;&nbsp;
     2.2.2. IP address: you can obtain it executing: `docker exec -it eim-sut ifconfig`. The inet addr value for eth0 interface.<br>&nbsp;&nbsp;&nbsp;
     2.2.3. Private key: you can obtain it executing: `docker exec -it eim-sut cat /root/.ssh/id_rsa`.<br>

## Instrumentalize agent

## De-instrumentalize agent
