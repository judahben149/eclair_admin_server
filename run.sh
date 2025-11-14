#!/bin/bash

# Eclair Admin Server - Run Script
# This script ensures JAVA_HOME is set and runs the application

# Set JAVA_HOME to Java 17
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH="$JAVA_HOME/bin:$PATH"

echo "========================================="
echo "Eclair Admin Server"
echo "========================================="
echo "Java Version: $(java -version 2>&1 | head -n 1)"
echo "JAVA_HOME: $JAVA_HOME"
echo "========================================="
echo ""

# Run Maven Spring Boot
mvn spring-boot:run
