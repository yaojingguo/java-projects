package org.yao;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.exporters.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;

public class DataService {
  private Tracer tracer = null;

  public DataService() {
    //    tracer = openTelemetry.getTracer("instrumentation-library-name", "1.0.0");
  }

  void parentOne() {
    Span parentSpan = tracer.spanBuilder("parent").startSpan();
    try {
      childOne(parentSpan);
    } finally {
      parentSpan.end();
    }
  }

  void childOne(Span parentSpan) {
    Span childSpan =
        tracer.spanBuilder("child").setParent(Context.current().with(parentSpan)).startSpan();
    // do stuff
    childSpan.end();
  }

  void parentTwo() {
    Span parentSpan = tracer.spanBuilder("parent").startSpan();
    try (Scope scope = parentSpan.makeCurrent()) {
      childTwo();
    } finally {
      parentSpan.end();
    }
  }

  void childTwo() {
    Span childSpan =
        tracer
            .spanBuilder("child")
            // NOTE: setParent(...) is not required;
            // `Span.current()` is automatically added as the parent
            .startSpan();
    try (Scope scope = childSpan.makeCurrent()) {
      // do stuff
    } finally {
      childSpan.end();
    }
  }

  void links() {
    Span parentSpan1 = null;
    Span parentSpan2 = null;
    Span parentSpan3 = null;
    SpanContext remoteSpanContext = null;
    Span child = tracer.spanBuilder("childWithLink")
        .addLink(parentSpan1.getSpanContext())
        .addLink(parentSpan2.getSpanContext())
        .addLink(parentSpan3.getSpanContext())
        .addLink(remoteSpanContext)
        .startSpan();
  }

  // SDK
  void sampler() {
    SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
        .setSampler(Sampler.alwaysOn())
        //or
        .setSampler(Sampler.alwaysOff())
        //or
        .setSampler(Sampler.traceIdRatioBased(0.5))
        .build();
  }

  void spanProcessor () {
    SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
        .addSpanProcessor(SimpleSpanProcessor.create(new LoggingSpanExporter()))
        .addSpanProcessor(BatchSpanProcessor.builder(new LoggingSpanExporter()).build())
        .build();

  }

  public static void main(String[] args) {
  }
}
