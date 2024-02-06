#!/bin/bash

ROOT_PATH="/home/ubuntu/deepdive-github-action"
JAR="$ROOT_PATH/application.jar"
STOP_LOG="$ROOT_PATH/stop.log"
SERVICE_PID=$(pgrep -f $JAR)

if [ -z "$SERVICE_PID" ]; then
  echo "Service is not running" >> $STOP_LOG
else
  echo "Service stop" >> $STOP_LOG
  kill -9 "$SERVICE_PID"
fi