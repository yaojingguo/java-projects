package org.yao;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Timed
@RequestMapping("api")
public class PersonController {
  Map<Integer, Person> people = new HashMap<Integer, Person>();

  public PersonController(MeterRegistry registry) {
    people.put(1, new Person("one"));
    people.put(2, new Person("two"));
    // constructs a gauge to monitor the size of the population
    registry.gaugeMapSize("population", null, people);
  }

  @GetMapping("people")
  public Collection<Person> listPeople() {
    return people.values();
  }

  @GetMapping("person")
  public Person findPerson(@PathVariable Integer id) {
    return people.get(id);
  }
}