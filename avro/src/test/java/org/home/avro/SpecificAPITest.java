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

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class SpecificAPITest {
    @Test
    public void doTest() {
        try {
            StringPair pair = new StringPair();
            pair.setLeft("L");
            pair.setRight("R");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DatumWriter<StringPair> writer = new SpecificDatumWriter<>(StringPair.class);
            Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            writer.write(pair, encoder);
            encoder.flush();
            out.close();

            DatumReader<StringPair> reader = new SpecificDatumReader<>(StringPair.class);
            Decoder decoder = DecoderFactory.get().binaryDecoder(out.toByteArray(), null);
            StringPair result = reader.read(null, decoder);
            assertThat(result.getLeft(), is("L"));
            assertThat(result.getRight(), is("R"));
        }
        catch (Exception e) {
            e.printStackTrace();
            fail("Opus, exception occurs");
        }
    }
}
