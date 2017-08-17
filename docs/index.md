# Elastest Instrumentation Manager
The ElasTest Instrumentation Manager (EIM) component controls and orchestrates the Instrumentation Agents that are deployed in [ElasTest] platform. These agents will instrument the operating system kernel of the SuT (Software under test) host instances. Thanks to it, the agent will be capable of exposing two types of capabilities: 
1) Controllability, through which the agent can force custom behaviours on the host’s network, CPU utilization, memory consumption, process lifecycle management or system shutdown, etc.
2) Observability, through which the Agent collects all information relevant for testing or monitoring purposes (e.g. energy consumption, resources utilization, etc.)

Before you start using ElasTest, you need to know the following terms:

- **SuT (System under Test):** Specification of the System that is being tested for correct operation.

## Features
The version 0.1 of the EIM, provides the following features:

- Register Instrumentation Agents. 
- Deploy [Beats](https://www.elastic.co/products/beats) software over the SuT.  

## How to run
To start using EIM, you need to follow the next steps. Several of these steps, are specific for Windows or Linux Operating Systems.

### Windows 
1.  Install [Docker Toolbox for windows](https://docs.docker.com/toolbox/toolbox_install_windows/).
Start Boo2docker Virtual Machine from Virtual Box GUI and connect via ssh. Execute `docker-machine ssh` from power shell or any terminal emulator. 
2.  Install [Docker Compose](https://docs.docker.com/compose/install/.) on the Virtual Machine Boot2docker created by Docker Toolbox installation. 
    - `sudo -i` (get access as superuser)    
    - ``curl -L https://github.com/docker/compose/releases/download/1.14.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose``
    - `chmod +x /usr/local/bin/docker-compose` 

3. Elasticsearch requires an increase of the max virtual memory to at least 262144 `sudo sysctl -w vm.max_map_count=262144`
4. Install [GIT](https://www.atlassian.com/git/tutorials/install-git) 

>**Note:** To this day, for each time the docker machine reboots, you will have to repeat the steps 2 and 3.

### Linux 
1. Install [Docker](https://docs.docker.com/engine/installation/).  
2. Install [Docker Compose](https://docs.docker.com/compose/install/).
    - `sudo -i` (get access as superuser)  
    - ``curl -L https://github.com/docker/compose/releases/download/1.14.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose``
    - `chmod +x /usr/local/bin/docker-compose` 

    > **Note:** For use Docker Compose file Version 2.1 you need Docker Engine version 1.12.0+ and Compose 1.9.0+.
    
3. Elasticsearch requires an increase of the max virtual memory to at least 262144. 
    - `sudo sysctl -w vm.max_map_count=262144`

4. Reload configuration.
    - `systemctl daemon-reload`
    
5. Install [GIT](https://www.atlassian.com/git/tutorials/install-git) 

### Download EIM
- Clone the project:
`git clone https://github.com/elastest/elastest-instrumentation-manager.git`
If you do not have git installed, you can also download the zip file from GithHub
 
### Start and stop EIM
- `cd elastest-instrumentation-manager`
- `docker-compose -p eim up `
- To stop EIM press `Ctrl+C` in the shell


## Development documentation

### Arquitecture
The ElasTest Instrumentation Manager Platform is divide in three parts:
- EIM Server Application.
- EIM Platform Services.

In the next diagram, you can to see the ElasTest Instrumentation Manager Components Architecture.

![ElasTest TORM Arquitecture](images/docker_environment_eim_r2.jpg.png)
