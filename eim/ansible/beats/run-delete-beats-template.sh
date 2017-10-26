#! /bin/bash

ansible-playbook ##delete-playbook-path## --extra-vars '{"ansible_become_pass":"elastest","ansible_ssh_extra_args":"-o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null"}'
