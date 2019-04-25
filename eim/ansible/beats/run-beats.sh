#! /bin/bash

ansible-playbook /var/ansible/beats/ansible-beats-master/playbook-beats-output-logstash.yml --extra-vars "ansible_become_pass=elastest"
