/* Copyright 2020 Rhino Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
