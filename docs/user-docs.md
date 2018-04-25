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
3. You need to add the created SuT to the created project in ETM, to do this click on `New SuT` button inside the created project and the form to create a new SuT will appear. The following data is required:
  3.1. SuT name <br>
  3.2. SuT description <br>
  3.3. Select Deployed outside ElasTest and Instrumented by ElasTest radio buttons <br>
  3.4. SuT IP<br>
  3.5. User: `root` for the proposed SuT<br>
  3.6. Private key: the result of the 2.2.3 command<br>
  3.7. Logs paths: the paths you want to monitorize the changes that is going to happen in these files. You can add more paths to monitor, clicking on the `Add Logs Path` button <br>
  When you finish to complete all the required fields, click on `Save` button at the bottom of the form

![New SuT creation](images/new_sut.jpg)

## Instrumentalize SuT

## De-instrumentalize SuT
