package org.yao;

import io.micrometer.core.instrument.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsContributor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

@Component
public class RoomboxTagsContributor implements WebMvcTagsContributor {
  private Logger log = LoggerFactory.getLogger(RoomboxTagsContributor.class);

  @Override
  public Iterable<Tag> getTags(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      Throwable exception) {
    log.info("contributing tags");
    String roomboxCode = response.getHeader(SpecialController.HEADER_KEY);
    if (roomboxCode == null) {
      return Collections.emptyList();
    }
    return Arrays.asList(Tag.of(SpecialController.HEADER_KEY, roomboxCode));
  }

  @Override
  public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
    return null;
  }
}
