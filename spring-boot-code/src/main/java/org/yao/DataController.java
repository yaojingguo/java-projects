package org.yao;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeValue;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.extension.auto.annotations.WithSpan;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {
  private Tracer tracer = null;

  public DataController() {
//    tracer = OpenTelemetry.getTracer("pet-clinic");
  }

  @GetMapping("/index")
  public String index() {
    a();
    return "hi, tracer";
  }

  @GetMapping("/api")
  public String useApi() {
    // Hierarchy is not set between bake-cake span and package-span
    Span topSpan = tracer.spanBuilder("top-span").startSpan();
    bakeCake();
    packageCake();
    topSpan.end();
    return "API";
  }

  @GetMapping("occasions")
  public String occasions() {
    Span span = Context.current();
    span.addEvent("one");
    span.addEvent("two");
    Attributes eventAttributes =
        Attributes.of(
            "age", AttributeValue.longAttributeValue(10),
            "nickname", AttributeValue.stringAttributeValue("duduju"));
    span.addEvent("end-computation", eventAttributes);
    return "OCC";
  }

  @GetMapping("rel")
  public String rel() {
    // in this scope, getCurrentSpan returns the parentSpan.
    Span parentSpan = tracer.getCurrentSpan();

    // Create a new span. Note that it is possible to set the parent manually.
    Span cakeSpan =
        tracer
            .spanBuilder("rel-api")
            //        .setParent(parentSpan)
            .startSpan();

    // Replace the current active span by creating a scope.
    try (Scope scope = tracer.withSpan(cakeSpan)) {

      cakeSpan.setAttribute("level", 1);

      // In this scope, cakeSpan is returned by getCurrentSpan
      bakeCake();

    } catch (Throwable t) {
      cakeSpan.setStatus(StatusCode.UNKNOWN.withDescription("Someone threw the cake!"));
    } finally {
      cakeSpan.end();
    }

    return "REL";
  }

  void bakeCake() {
    Span span = tracer.spanBuilder("bake-cake").startSpan();
    System.out.printf("bake cake\n");
    span.setAttribute("size", 10);
    span.setAttribute("shape", "circle");
    sleep(199);
    span.end();
  }

  void packageCake() {
    Span span = tracer.spanBuilder("package-cake").startSpan();
    System.out.printf("package cake\n");
    sleep(567);
    span.end();
  }

  @WithSpan
  void a() {
    sleep(500);
    b();
    sleep(400);
  }

  @WithSpan
  void b() {
    sleep(100);
  }

  void sleep(long millis) {
    try {
      System.out.printf("sleeping... %d milliseconds\n", millis);
      Thread.sleep(millis);
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }

  class Welcome {
    public String name;
    public int no;

    public Welcome(String name, int no) {
      this.name = name;
      this.no = no;
    }
  }

  @GetMapping(value = "greet")
  public Welcome greet() {
    return new Welcome("xiaoyu", 10);
  }
}
