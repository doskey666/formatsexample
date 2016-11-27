package org.datacourse.formats.examples;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.datacourse.formats.datagenerator.DataGenerator;
import org.datacourse.formats.models.SomeObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by doskey on 27/11/2016.
 */
public class CsvExample {

    public static final int NUMBER_OF_OBJECTS = 100;

    public static void main(String[] args) throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        File csvExampleTempFile = new File(tempDir, "csvExampleTempFile");

        writeData(csvExampleTempFile);
        readData(csvExampleTempFile);
    }

    private static void writeData(File csvExampleTempFile) {
        List<SomeObject> objectsToWrite = DataGenerator.generateObjects(NUMBER_OF_OBJECTS);
        System.out.println("Writing " + NUMBER_OF_OBJECTS + " objects as csv, one per line, into " + csvExampleTempFile);

        try (FileWriter fw = new FileWriter(csvExampleTempFile, false);
             BufferedWriter bw = new BufferedWriter(fw);
             CSVWriter csvWriter = new CSVWriter(bw)) { // Note the other constructors of CSVWriter allowing custom separators

            String[] csvHeader = new String[]{"someStringMember", "someIntMember"};
            csvWriter.writeNext(csvHeader);

            for (SomeObject someObject : objectsToWrite) {
                csvWriter.writeNext(new String[]{someObject.getSomeStringMember(), String.valueOf(someObject.getSomeIntMember())});
            }

        } catch (IOException e) {
            System.out.print("Failed to write to " + csvExampleTempFile + " with error " + e.getMessage());
        } finally {
            System.out.println("Done");
        }
    }

    private static void readData(File csvExampleTempFile) {

        try (FileReader fileReader = new FileReader(csvExampleTempFile);
             CSVReader csvReader = new CSVReader(fileReader)) {

            HeaderColumnNameMappingStrategy<SomeObject> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(SomeObject.class);
            CsvToBean<SomeObject> csv = new CsvToBean<>();
            List<SomeObject> parsedObjects = csv.parse(strategy, csvReader);
            System.out.println("Read " + parsedObjects.size() + " objects successfully");

        } catch (Exception e) {
            System.out.print("Failed to read from " + csvExampleTempFile + " with error " + e.getMessage());
        }
    }
}
