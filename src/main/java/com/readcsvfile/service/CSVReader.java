package com.readcsvfile.service;

import static com.readcsvfile.constant.Constant.API_URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readcsvfile.dto.Customer;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class CSVReader {

  private final HttpClient httpClient;

  private final ServerChecker serverChecker;


  public CSVReader(HttpClient httpClient, ServerChecker serverChecker) {
    this.httpClient = httpClient;
    this.serverChecker = serverChecker;
  }

  public void parseCSV(Reader in) throws IOException {

    Iterable<CSVRecord> csvRecords =
        CSVFormat.DEFAULT
            .withHeader(
                "Customer Ref",
                "Customer Name",
                "Address Line 1",
                "Address Line 2",
                "Town",
                "County",
                "Country",
                "Postcode")
            .withSkipHeaderRecord()
            .parse(in);
    for (CSVRecord csvRecord : csvRecords) {
      Customer customer =
          Customer.builder()
              .customerRef(csvRecord.get("Customer Ref"))
              .customerName(csvRecord.get("Customer Name"))
              .addressLine1(csvRecord.get("Address Line 1"))
              .addressLine2(csvRecord.get("Address Line 2"))
              .town(csvRecord.get("Town"))
              .county(csvRecord.get("County"))
              .country(csvRecord.get("Country"))
              .postcode(csvRecord.get("Postcode"))
              .build();
      String json = new ObjectMapper().writeValueAsString(customer);
      sendPost(json);
    }

  }

  private void sendPost(String jsonData) {
    if (!serverChecker.isServerUp()) {
      System.out.println("Server is not reachable. Exiting the method.");
      return;
    }
    try {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(API_URL))
              .header("Content-Type", "Application/json")
              .POST(HttpRequest.BodyPublishers.ofString(jsonData))
              .build();

      HttpResponse<String> response = httpClient.send(request,
          HttpResponse.BodyHandlers.ofString());
      System.out.println("Response status code: " + response.statusCode());
      System.out.println("Response body: " + response.body());
    } catch (java.net.ConnectException e) {
      System.err.println("Connection failed: " + e.getMessage());
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }


}
