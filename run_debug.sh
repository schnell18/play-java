java -cp out/production/classes \
     -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=y \
     -XX:+StartAttachListener \
     org.home.hone.perf.Bricks
