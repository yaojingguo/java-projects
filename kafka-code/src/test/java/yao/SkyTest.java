package yao;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

public class SkyTest {
  private static OkHttpClient client = new OkHttpClient();

  private static void http() throws IOException {
    String url = "https://httpbin.org/get";

    Request request = new Request.Builder().url(url).build();

    try (Response response = client.newCall(request).execute()) {
      String body = response.body().string();
      System.out.printf("body: %s\n", body);
    }
  }

  @Test
  public void testSend() {

  }

  @Test
  public void testReceive() {

  }
}
