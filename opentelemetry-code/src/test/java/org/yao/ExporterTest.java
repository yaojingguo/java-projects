package org.yao;

import io.opentelemetry.api.OpenTelemetry;
import org.junit.Test;

public class ExporterTest {
  @Test
  public void testJaeger() {
    OpenTelemetry openTelemetry = Configuration.jaeger("http://localhost:14250");
    ExampleTracing example = new ExampleTracing(openTelemetry);
    example.tracing();
  }

  @Test
  public void testLogging() {
    ExampleTracing example = new ExampleTracing(Configuration.logging());
    example.tracing();
  }
}
