#!/bin/bash
if [ -d "classes" ]; then
  rm -rf classes
fi
mkdir classes/

cp="classes/"
for jar in `find lib/ -type f -name "*.jar"`
do
cp="$cp:$jar"
done


files=""
for file in `find src/ -type f -name "*.java"`
do
files="$files $file";

done

javac -cp "$cp" -d "classes/" $files

rsync -r --exclude '*.java' src/ classes/



