#! /bin/bash

ansible-playbook ##playbook-path## --extra-vars "ansible_become_pass=elastest"
