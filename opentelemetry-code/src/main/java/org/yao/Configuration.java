package org.yao;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.exporter.logging.LoggingMetricExporter;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricReader;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * All SDK management takes place here, away from the instrumentation code, which should only access
 * the OpenTelemetry APIs.
 */
public class Configuration {

  /**
   * Initialize an OpenTelemetry SDK with a Jaeger exporter and a SimpleSpanProcessor.
   *
   * @param jaegerEndpoint The endpoint of your Jaeger instance.
   * @return A ready-to-use {@link OpenTelemetry} instance.
   */
  public static OpenTelemetry jaeger(String jaegerEndpoint) {
    // Export traces to Jaeger
    JaegerGrpcSpanExporter jaegerExporter =
        JaegerGrpcSpanExporter.builder()
            .setEndpoint(jaegerEndpoint)
            .setTimeout(30, TimeUnit.SECONDS)
            .build();

    Resource serviceNameResource =
        Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "otel-jaeger-example"));

    // Set to process the spans by the Jaeger Exporter
    SdkTracerProvider tracerProvider =
        SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(jaegerExporter))
            .setResource(Resource.getDefault().merge(serviceNameResource))
            .build();
    OpenTelemetrySdk openTelemetry =
        OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).build();

    // it's always a good idea to shut down the SDK cleanly at JVM exit.
    Runtime.getRuntime().addShutdownHook(new Thread(tracerProvider::close));

    return openTelemetry;
  }

  /** The number of milliseconds between metric exports. */
//  private static final long METRIC_EXPORT_INTERVAL_MS = 800L;

  /**
   * Initializes an OpenTelemetry SDK with a logging exporter and a SimpleSpanProcessor.
   *
   * @return A ready-to-use {@link OpenTelemetry} instance.
   */
  public static OpenTelemetry logging() {
    // Create an instance of PeriodicMetricReader and configure it
    // to export via the logging exporter
//    MetricReader periodicReader =
//        PeriodicMetricReader.builder(LoggingMetricExporter.create())
//            .setInterval(Duration.ofMillis(METRIC_EXPORT_INTERVAL_MS))
//            .build();

    // This will be used to create instruments
//    SdkMeterProvider meterProvider =
//        SdkMeterProvider.builder().registerMetricReader(periodicReader).build();

    // Tracer provider configured to export spans with SimpleSpanProcessor using
    // the logging exporter.
    SdkTracerProvider tracerProvider =
        SdkTracerProvider.builder()
            .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
            .build();
    return OpenTelemetrySdk.builder()
//        .setMeterProvider(meterProvider)
        .setTracerProvider(tracerProvider)
        .buildAndRegisterGlobal();
  }
}
