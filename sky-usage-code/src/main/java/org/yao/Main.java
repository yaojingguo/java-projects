package org.yao;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {
  OkHttpClient client = new OkHttpClient();

  String run(String url) throws IOException {
    Request request = new Request.Builder()
      .url(url)
      .build();

    try (Response response = client.newCall(request).execute()) {
      return response.body().string();
    }
  }

  public static void main(String[] args) throws Exception {
    Main example = new Main();
    String response = example.run("https://httpbin.org/get");
    System.out.println(response);
//    Thread.sleep(1 * 1000);
  }
}
