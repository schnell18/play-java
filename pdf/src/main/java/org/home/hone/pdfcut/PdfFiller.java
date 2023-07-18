package org.home.hone.pdfcut;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSInputStream;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.util.Charsets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PdfFiller {

    public static int fillForm(String file, Map<String, String> formData) throws IOException {
        File pdfFile = new File(file);

        // Load the existing pdf file
        PDDocument pdd = PDDocument.load(pdfFile);
        PDAcroForm acroForm = pdd.getDocumentCatalog().getAcroForm();

        List<PDField> fields = new ArrayList<PDField>();
        for (String item : formData.keySet()) {
            PDField pdField = acroForm.getField(item);
            if (pdField == null) continue;
            fields.add(pdField);
            pdField.setValue(formData.get(item));
            //pdField.setReadOnly(true);
        }

        // Save
        String filled = "filled.pdf";
        acroForm.flatten(fields, true);
        pdd.save(filled);
        pdd.close();
        return 1;
    }

    public static int inspectForm(String file) throws IOException {
        File pdfFile = new File(file);

        // Load the existing pdf file
        PDDocument pdd = PDDocument.load(pdfFile);
        PDAcroForm acroForm = pdd.getDocumentCatalog().getAcroForm();
        acroForm.getFields().forEach(f -> System.out.println(f.getFullyQualifiedName()));

        // Save
        pdd.close();
        return 1;
    }
    public static int fillData(String file, Map<String, String> formData) {
        Function<String, String> subst = s -> {
            for (String item : formData.keySet()) {
                String placeholder = String.format("#%s#", item);
                if (StringUtils.contains(s, placeholder))
                    return StringUtils.replaceOnce(s, placeholder, formData.get(item));
            }
            return null;
        };

        File pdfFile = new File(file);
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDPageTree pages = document.getDocumentCatalog().getPages();
            PDPage page = pages.get(0);
            Iterator<PDStream> cs = page.getContentStreams();
            while (cs.hasNext()) {
                PDStream pdStream = cs.next();
                COSInputStream is = pdStream.createInputStream();
                byte[] buf = new byte[1024 * 8];
                is.read(buf);
                System.out.println(new String(buf));
            }
            PDFStreamParser parser = new PDFStreamParser(page);
            parser.parse();

            List tokens = parser.getTokens();
            for (int j = 0; j < tokens.size(); j++) {
                Object next = tokens.get(j);
                if (next instanceof Operator) {
                    Operator op = (Operator) next;
                    //Tj and TJ are the two operators that display strings in a PDF
                    if (op.getName().equals("Tj")) {
                        // Tj takes one operator and that is the string to display so lets update that operator
                        COSString previous = (COSString) tokens.get(j - 1);
                        String string = subst.apply(previous.getString());
                        if (string != null)
                            previous.setValue(string.getBytes(Charsets.UTF_16BE));
                    }
                    else if (op.getName().equals("TJ")) {
                        COSArray previous = (COSArray) tokens.get(j - 1);
                        for (int k = 0; k < previous.size(); k++) {
                            Object arrElement = previous.getObject(k);
                            if (arrElement instanceof COSString) {
                                COSString cosString = (COSString) arrElement;
                                String string = subst.apply(cosString.getString());
                                if (string != null)
                                    cosString.setValue(string.getBytes(Charsets.UTF_16BE));
                            }
                        }
                    }
                }
            }
            // now that the tokens are updated we will replace the page content stream.
            PDStream updatedStream = new PDStream(document);
            OutputStream out = updatedStream.createOutputStream();
            ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
            tokenWriter.writeTokens(tokens);
            page.setContents(updatedStream);
            out.close();

            String filled = "filled.pdf";
            document.save(filled);
            document.close();
        }
        catch (InvalidPasswordException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        String pdfFile = "/Users/justin/Documents/360_voucher_songti.pdf";
        Map<String, String> formData = new HashMap<>();
        formData.put("billNo", "00040000009293");
        formData.put("insuranceBillNo", "P190000014382293");
        formData.put("applicant", "lishizheng");
        formData.put("recognizee", "huatuo");
        formData.put("effectiveYear", "2017");
        formData.put("effectiveMonth", "6");
        formData.put("effectiveDay", "1");
        fillForm(pdfFile, formData);
    }
}
