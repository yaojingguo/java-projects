package org.yao;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

public class Util {
  public static void setupJaegerExporter() {
    // Create a channel towards Jaeger end point
    ManagedChannel jaegerChannel =
        ManagedChannelBuilder.forAddress("localhost", 14250).usePlaintext().build();
    // Export traces to Jaeger
    // Export traces to Jaeger
    JaegerGrpcSpanExporter jaegerExporter =
        JaegerGrpcSpanExporter.builder()
            .setServiceName("http-client")
            .setChannel(jaegerChannel)
            .setDeadlineMs(30000)
            .build();

    // Set to process the spans by the Jaeger Exporter
    OpenTelemetrySdk.getGlobalTracerManagement()
        .addSpanProcessor(SimpleSpanProcessor.builder(jaegerExporter).build());
  }
}
