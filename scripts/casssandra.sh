#!/usr/bin/env bash

set -e

kubectl apply -f ./cassandra-service.yaml
kubectl get svc cassandra
kubectl apply -f ./cassandra-statefulset.yaml
kubectl get statefulset cassandra