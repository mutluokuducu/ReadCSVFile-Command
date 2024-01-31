package com.readcsvfile.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CSVReaderTest {

  private CSVReader csvReader;
  private HttpClient httpClient;
  private ServerChecker serverChecker;
  private Reader reader;

  @BeforeEach
  public void setup() {
    httpClient = mock(HttpClient.class);
    serverChecker = mock(ServerChecker.class);
    csvReader = new CSVReader(httpClient, serverChecker);
    String csvData =
        "Customer Ref,Customer Name,Address Line 1,Address Line 2,Town,County,Country,Postcode\n" +
            "123,John Doe,123 Main St,,City,County,US,12345";
    reader = new StringReader(csvData);
  }

  @Test
  void testParseCSV() throws IOException, InterruptedException {
    when(serverChecker.isServerUp()).thenReturn(true);
    HttpRequest request = mock(HttpRequest.class);
    HttpResponse<String> response = mock(HttpResponse.class);
    when(httpClient.send(Mockito.any(HttpRequest.class),
        Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(response);
    when(response.statusCode()).thenReturn(200);

    CSVReader csvReaderSpy = Mockito.spy(csvReader);
    csvReaderSpy.parseCSV(reader);
    verify(csvReaderSpy, times(1)).parseCSV(reader);
  }

}