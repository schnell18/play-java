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

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GenericAPITest {

    @Test
    public void doTest() {
        try {
            Schema.Parser parser = new Schema.Parser();
            Schema schema = parser.parse(getClass().getResourceAsStream("StringPair.avsc"));
            GenericRecord record = new GenericData.Record(schema);
            record.put("left", "L");
            record.put("right", "R");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
            Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            writer.write(record, encoder);
            encoder.flush();
            out.close();

            DatumReader<GenericRecord> reader = new GenericDatumReader<>(schema);
            Decoder decoder = DecoderFactory.get().binaryDecoder(out.toByteArray(), null);
            GenericRecord result = reader.read(null, decoder);
            assertThat(result.get("left").toString(), is("L"));
            assertThat(result.get("right").toString(), is("R"));
        }
        catch (Exception e) {
            System.out.println(e);
            fail("Opus, exception occurs");
        }
    }
}
