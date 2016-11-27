package org.datacourse.formats.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.datacourse.formats.datagenerator.DataGenerator;
import org.datacourse.formats.models.SomeObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by doskey on 27/11/2016.
 */
public class XmlExample {

    public static final int NUMBER_OF_OBJECTS = 100;

    public static void main(String[] args) throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        File xmlExampleTempFile = new File(tempDir, "xmlExampleTempFile");

        writeData(xmlExampleTempFile);
        readData(xmlExampleTempFile);
    }

    private static void writeData(File xmlExampleTempFile) {
        ObjectMapper objectMapper = new XmlMapper();

        List<SomeObject> objectsToWrite = DataGenerator.generateObjects(NUMBER_OF_OBJECTS);
        System.out.println("Writing " + NUMBER_OF_OBJECTS + " objects as xml, one per line, into " + xmlExampleTempFile);

        try (FileWriter fw = new FileWriter(xmlExampleTempFile, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (SomeObject someObject : objectsToWrite) {
                String currentObjectAsxml = objectMapper.writeValueAsString(someObject);
                out.println(currentObjectAsxml);
            }

        } catch (IOException e) {
            System.out.print("Failed to write to " + xmlExampleTempFile + " with error " + e.getMessage());
        } finally {
            System.out.println("Done");
        }
    }

    private static void readData(File xmlExampleTempFile) {
        ObjectMapper objectMapper = new XmlMapper();

        try (FileReader fileReader = new FileReader(xmlExampleTempFile);
             LineNumberReader reader = new LineNumberReader(fileReader)) {
            final int[] linesRead = {0};
            reader.lines().forEach(currentLine -> {
                try {
                    SomeObject someObject = objectMapper.readValue(currentLine, SomeObject.class);
                    linesRead[0]++;
                } catch (IOException e) {
                    System.out.println("Failed to parse line " + currentLine);
                }
            });

            System.out.println("Read " + linesRead[0] + " objects successfully");
        } catch (Exception e) {
            System.out.print("Failed to read from " + xmlExampleTempFile + " with error " + e.getMessage());
        }
    }
}
