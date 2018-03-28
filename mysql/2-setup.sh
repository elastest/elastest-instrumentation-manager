#!/bin/bash
set -e
set -x

# EIM schema
mysql -u elastest -p'elastest' EIM < /elastest/repository/db_repository_deployment.sql
