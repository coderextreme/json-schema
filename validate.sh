#!/bin/bash -x
export CLASSPATH=".;re2j-1.1.jar;google-collections-1.0.jar;json-20180130.jar;handy-uri-templates-2.1.6.jar;org.everit.json.schema-1.10.0.jar"
export WEB3D=/c/x3d-code/www.web3d.org
javac -classpath $CLASSPATH *.java
java -classpath $CLASSPATH MetaSchemaTest
ls abox.json | xargs java -classpath $CLASSPATH ObjectTest
