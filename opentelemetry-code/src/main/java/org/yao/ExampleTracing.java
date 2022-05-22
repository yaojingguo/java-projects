package org.yao;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;

public final class ExampleTracing {

  private final Tracer tracer;

  public ExampleTracing(OpenTelemetry openTelemetry) {
    tracer = openTelemetry.getTracer("io.opentelemetry.example.JaegerExample");
  }

  private void myWonderfulUseCase() {
    // Generate a span
    Span span = this.tracer.spanBuilder("top").startSpan();

    span.setAttribute("cid", "123");
    span.setAttribute("uid", "456");


    span.addEvent("Event 1");
    // execute my use case - here we simulate a wait
    doWork();
    span.addEvent("Event 2");
    child(span);
    span.end();
  }

  private void child(Span parent) {
    Span span = tracer.spanBuilder("child").setParent(Context.current().with(parent)).startSpan();
    span.setAttribute("class_type", "VIP");
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
