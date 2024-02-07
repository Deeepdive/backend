#!/bin/bash

ROOT_PATH="/home/ubuntu/deepdive-github-action"
SOURCE_JAR="$ROOT_PATH/build/libs/backend-0.0.1-SNAPSHOT.jar"
JAR="$ROOT_PATH/application.jar"

APP_LOG="$ROOT_PATH/application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"

NOW=$(date +%c)

echo "[$NOW] 현재 디렉토리: $(pwd)" >> "$START_LOG"
echo "[$NOW] build/libs 디렉토리 내용: $(ls -la "$ROOT_PATH/build/libs")" >> "$START_LOG"

echo "[$NOW] $JAR 복사" >> $START_LOG
cp "$SOURCE_JAR" "$JAR" >> "$START_LOG"

echo "[$NOW] $JAR 실행" >> $START_LOG
nohup java -jar "$JAR" > "$APP_LOG" 2> "$ERROR_LOG" &

SERVICE_PID=$(pgrep -f $JAR)
echo "[$NOW] Service PID: $SERVICE_PID" >> $START_LOG