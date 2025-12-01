#!/bin/bash

# Run in project directory

VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec)
INJAR=$(find ./bukkit/target -name "crate-*-${VERSION}.jar")
OUTJAR=bukkit/target/CrateReloaded.jar

stat ${INJAR} > /dev/null

if [ $? -eq 0 ]
then
  echo "Running Proguard ${INJAR} -> ${OUTJAR}"

  ./bin/proguard.sh @config/proguard -injars ${INJAR} -outjars ${OUTJAR}
else
  echo "Failure: Did not find jar"
  exit 1
fi


