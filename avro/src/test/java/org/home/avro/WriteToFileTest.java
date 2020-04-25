package org.home.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.fail;

public class WriteToFileTest {
    @Test
    public void doTest() {
        try {
            User user1 = new User();
            user1.setName("Alyssa");
            user1.setFavoriteNumber(256);
            // user1.setFavoriteColor("Alyssa");

            User user2 = new User("ben", 7, "red");

            User user3 = User.newBuilder()
                    .setName("Charlie")
                    .setFavoriteColor("blue")
                    .setFavoriteNumber(null)
                    .build();

            File dataFile = new File("users.avro");
            DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
            DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter);
            dataFileWriter.create(user1.getSchema(), dataFile);
            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
            dataFileWriter.append(user3);
            for (int i = 0; i < 1000; i++) dataFileWriter.append(user3);dataFileWriter.close();
            DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
            DataFileReader<User> dataFileReader = new DataFileReader<>(dataFile, userDatumReader);
            User user = null;
            while (dataFileReader.hasNext()) {
                user = dataFileReader.next(user);
                System.out.println(user);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            fail("Opus, exception occurs");
        }
    }
}
