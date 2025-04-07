# Introduction
Java programming playground.
Practise some basic Java programming topics like:
- Thread
- Generics
- Annotation
- Java Collection Framework
- Algorithm

## Catalog

| sub-project         | description              |
|---------------------|--------------------------|
| algorithm           | various algorithms       |
| basic               | some fundamental stuffs  |
| clazz               | class meta-programming   |
| crypto              | crypto stuffs like AES   |
| pdf                 | manipulate PDF in java   |
| scripting           | JSR223 scripting         |
| validation          | JSR380 validation        |
| flyway              | flyway schema mgmt demo  |
| avro                | explore avro data format |
| int-attack          | Integer cache attack     |
| hibernate           | Simple hibernate 6 demo  |
| protobuf            | Simple protobuf demo     |


## Build

This project uses maven to build.

## MemoryHog

This example is to demonstrate the garbage collection internal. To see this
demo, launch the following command under the `basic` project's root directory:

~~~~bash
cd basic
mvn compile
java -cp target/classes        \
     -Xms15M                   \
     -Xmx15M                   \
     -Xloggc:hog_gc.log        \
     org.home.hone.gc.MemoryHog 1>/dev/null &

# get the PID of MemoryHog program display live GC stat
pid=$(jcmd -l | grep org.home.hone.gc.MemoryHog | awk '{print $1}')
jstat -gcutil $pid 250
~~~~

