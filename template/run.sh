#!/bin/bash

cp -R /etc/template/* /etc/minecraft

cd /etc/minecraft

while true; do
  java -jar paper.jar
done
