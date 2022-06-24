#!/bin/bash

export JAVA_OPTS="-Xmx8g"

kotlin -cp libGrafoKt/libGrafoKt.jar:. EvaluacionCCKt $1
