#!/usr/bin/env bash

for i in  {1..10000}; do
  echo $i
  curl 'http://localhost:9001/actuator/prometheus'
done
