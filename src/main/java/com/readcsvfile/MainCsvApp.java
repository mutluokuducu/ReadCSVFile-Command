package com.readcsvfile;

import com.readcsvfile.service.CSVReader;
import com.readcsvfile.service.ServerChecker;
import com.readcsvfile.service.ServerCheckerImpl;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.http.HttpClient;
import java.nio.file.Files;

public class MainCsvApp {

  public static void main(String[] args) {
    String fileName;
    HttpClient httpClient = HttpClient.newHttpClient();
    ServerChecker serverChecker = new ServerCheckerImpl(); // Replace RealServerChecker with your actual implementation

    CSVReader service = new CSVReader(httpClient, serverChecker);
    if (args.length > 0) {
      fileName = args[0];
      System.out.println("****File read from args: " + fileName);
    } else {
      fileName = "data/demo_customer_data.csv";
      System.out.println("***File read from path: " + fileName);
    }

    try {
      InputStream is = CSVReader.class.getClassLoader().getResourceAsStream(fileName);
      if (is == null) {
        throw new IllegalArgumentException("file not found! " + fileName);
      } else {
        java.nio.file.Path tempOutput = Files.createTempFile("temp", ".csv");
        Files.copy(is, tempOutput, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        Reader in = new FileReader(tempOutput.toString());
        service.parseCSV(in);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
