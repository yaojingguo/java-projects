package org.yao;

import com.lightstep.opentelemetry.launcher.OpenTelemetryConfiguration;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;

public class Main {
  public static void main(String[] args) {
    // Installs exporter into tracer SDK default provider with batching span processor.
    OpenTelemetryConfiguration.newBuilder()
        .setServiceName("first-service")
        .setAccessToken("t5RPXeMTsyVb4iePd5yGKos2jRyR1Vganvq/zO+mHBQ0hTeEhaREM705gg61eJKrJHjJDsqquuWQfHYgQ94lfWyvogymoyFKQUoYAJHa")
        .setSpanEndpoint("https://ingest.lightstep.com/")
        .install();

// Get tracer
//    Tracer tracer = OpenTelemetry.getGlobalTracer("instrumentation-library-name", "1.0.0");

  }
}
