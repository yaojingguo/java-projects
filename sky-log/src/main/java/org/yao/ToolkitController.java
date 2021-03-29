package org.yao;

import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sw")
public class ToolkitController {
  @GetMapping("/trace")
  public String trace() {
    new Worker().sleep();
    work();
    return "work done";
  }

  @Trace
  private void work() {
    System.out.printf("working...\n");
    Util.sleep(1);
    System.out.printf("done\n");
  }

    @GetMapping("trace-user")
    public User traceUser() {
      return avatar(10, "Jack");
    }

    @GetMapping("tag")
    public User onlyTag() {
      return incrementAge(10);
    }

    @GetMapping("tag-indirect")
    public User tagIndirect() {
      return bridge();
    }

    @Trace
    private User bridge() {
      return incrementAge(100);
    }

    @GetMapping("tags")
    public User onlyTags() {
      return zero(10, "Jack");
    }

    @Trace
    @Tag(key = "age", value = "arg[0]")
    @Tag(key = "name", value = "arg[1]")
    @Tag(key = "name", value = "returnedObj.name")
    @Tag(key = "age", value = "returnedObj.age")
    public User avatar(int originalAge, String originalName) {
      User u = new User(originalAge + 1, originalName + "-pandora");
      return u;
    }

    @Tag(key = "age", value = "arg[0]")
  //  @Tag(key = "op", value = "only-tag")
    public User incrementAge(int age) {
      System.out.printf(
          "trace id: %s, segment id: %s, span id: %s\n", TraceContext.traceId(),
   TraceContext.segmentId(), TraceContext.spanId());
      User u = new User(age - 1, "xiaoyu");
      return u;
    }

  @Tags({@Tag(key = "age", value = "arg[0]"), @Tag(key = "name", value = "returnedObj.name")})
  public User zero(int age, String name) {
    User u = new User(0, "anonymous");
    return u;
  }

    @GetMapping("active")
    public String active() {
      ActiveSpan.setOperationName("try-use-toolkit");
      ActiveSpan.tag("homeland", "China");
      ActiveSpan.info("set up a classroom");
      return "active response";
    }

    @GetMapping("active-error-occurred")
    public String activeErrorOccurred() {
      ActiveSpan.setOperationName("operation-with-error-occurred");
      ActiveSpan.tag("city", "Beijing");
      ActiveSpan.error("failed to add a student to the classroom");
      return "active-error-occurred response";
    }

    @GetMapping("active-throwable")
    public String activeThrowable() {
      ActiveSpan.setOperationName("operation-with-throwable");
      ActiveSpan.error(new Throwable());
      return "active-throwable response";
    }
}
