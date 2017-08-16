#! /bin/bash

ansible-playbook /var/ansible/beats/ansible-beats-master/##playbook-name## --extra-vars "ansible_become_pass=elastest"
