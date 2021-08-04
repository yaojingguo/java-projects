package org.yao.log;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

/**
 * An example of using {@link io.opentelemetry.exporter.logging.LoggingSpanExporter} and {@link
 * io.opentelemetry.exporter.logging.LoggingMetricExporter}.
 */
public final class LoggingExporterExample {
  private static final String INSTRUMENTATION_NAME = LoggingExporterExample.class.getName();

  private final Tracer tracer = OpenTelemetry.getGlobalTracer(INSTRUMENTATION_NAME);

  public void myWonderfulUseCase() {
    Span span = this.tracer.spanBuilder("start my wonderful use case").startSpan();
    span.addEvent("Event 0");
    span.addEvent("Event 1");
    span.end();
  }

  public static void main(String[] args) {
    // it is important to initialize your SDK as early as possible in your application's lifecycle
    // Set to process the spans by the Jaeger Exporter
    OpenTelemetrySdk.getGlobalTracerManagement()
        .addSpanProcessor(SimpleSpanProcessor.builder(new LoggingSpanExporter()).build());

    LoggingExporterExample example = new LoggingExporterExample();

    example.myWonderfulUseCase();

    System.out.println("Bye");
  }
}
