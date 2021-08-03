package org.yao;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {
  private static OkHttpClient client = new OkHttpClient();

  public static void main(String[] args) throws IOException {
    String url = "https://httpbin.org/get";
    Request request = new Request.Builder().url(url).build();
    try (Response response = client.newCall(request).execute()) {
      String body = response.body().string();
      System.out.printf("body: %s\n", body);
    }
  }
}
