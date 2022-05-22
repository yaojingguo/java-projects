package org.yao;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

public final class ExampleTracing {

  private final Tracer tracer;

  public ExampleTracing(OpenTelemetry openTelemetry) {
    tracer = openTelemetry.getTracer("io.opentelemetry.example.JaegerExample");
  }

  private void myWonderfulUseCase() {
    // Generate a span
    Span span = this.tracer.spanBuilder("Start my wonderful use case").startSpan();
    span.addEvent("Event 1");
    // execute my use case - here we simulate a wait
    doWork();
    span.addEvent("Event 2");
    child();
    span.end();
  }

  private void child() {
    Span span = tracer.spanBuilder("child").startSpan();
    doWork();
    span.end();
  }

  private void doWork() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // do the right thing here
    }
  }

  public void tracing() {
    myWonderfulUseCase();
  }

}
