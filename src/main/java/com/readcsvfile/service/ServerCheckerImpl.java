package com.readcsvfile.service;

import static com.readcsvfile.constant.Constant.API_URL;

import java.net.Socket;
import java.net.URI;

public class ServerCheckerImpl implements ServerChecker {

  @Override
  public boolean isServerUp() {
    URI serverUri = URI.create(API_URL);
    try (Socket socket = new Socket(serverUri.getHost(), serverUri.getPort())) {
      return true;
    } catch (Exception e) {
      System.out.println("Unable to connect to the server: " + e.getMessage());
      return false;
    }
  }
}
