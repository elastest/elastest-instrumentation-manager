#! /bin/bash

ansible-playbook -i ##ansible-cfg-file## ##playbook-path##

# previous execution command
# ansible-playbook ##playbook-path## --extra-vars '{"ansible_become_pass":"elastest","ansible_ssh_extra_args":"-o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null"}'
