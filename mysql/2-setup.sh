#!/bin/bash
set -e
set -x

# EIM schema
mysql -u elastest -p'elastest' eim < /eim/repository/db_repository_deployment.sql
