#!/usr/bin/env bash


for i in  {1..10000}; do
  echo $i
  curl 'localhost:9000/special/index?millis=100&success=true'
  echo 
  curl 'localhost:9000/special/index?millis=500&success=true'
  echo
  # sleep 1
done
