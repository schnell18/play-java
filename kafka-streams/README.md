# Write a simple kafka-streams application

This project explores how to implement a minimal kafka-streams application.
The reference document is the [official tutorial][1].

Bootstrap a maven project like:

    mvn archetype:generate \
        -DarchetypeGroupId=org.apache.kafka \
        -DarchetypeArtifactId=streams-quickstart-java \
        -DarchetypeVersion=4.0.0 \
        -DgroupId=tk.tinkerit.play \
        -DartifactId=kafka-streams \
        -Dversion=0.1.0 \
        -Dpackage=it.tinkerit.play.kafka.stream



[1]: https://kafka.apache.org/40/documentation/streams/tutorial 