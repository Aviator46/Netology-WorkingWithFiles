package ru.netology;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {

    public ClientLog() {
        this.logPage = logPage;
    }

    private List<String[]> logPage = new ArrayList<>();

    public void log(int productNum, int amount) {
        logPage.add(new String[] {String.valueOf(productNum), String.valueOf(amount)});
    }

    public void exportAsCSV(File csvFile) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true));
             FileInputStream stream = new FileInputStream(csvFile)) {
            if (stream.read() == -1) {
                writer.writeNext(new String[] {"productNum", "amount"});
            }
            writer.writeAll(logPage);
        }
    }
}