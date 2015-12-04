#!/bin/bash
cp="classes/"
for jar in `find lib/ -type f -name "*.jar"`
do
cp="$cp:$jar"
done
java -cp "$cp" ee.smkv.server.Server $1
