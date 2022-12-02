#!/bin/bash
JAVA_OPTS=""
JVM_OPTS="-Xms512m -Xmx800m -Xss256k"

if [ "X$ENV_TCBUS_IM_JAVA_OPTS" != "X" ]; then
	JVM_OPTS="$ENV_TCBUS_IM_JAVA_OPTS"
fi

if [ "X$ENV_TCBUS_IM_JVM_OPTS" != "X" ]; then
	JVM_OPTS="$ENV_TCBUS_IM_JVM_OPTS"
fi

cd /usr/local/iot/
java $JAVA_OPTS -jar app.jar $JVM_OPTS