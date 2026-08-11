#!/bin/sh
GRADLE_OPTS="-Xmx2048m -XX:MaxMetaspaceSize=512m"
exec java $GRADLE_OPTS -jar gradle-wrapper.jar "$@"
