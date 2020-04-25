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
