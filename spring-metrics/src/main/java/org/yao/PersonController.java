package org.yao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.metrics.annotation.Timed;
import org.springframework.metrics.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Timed
public class PersonController {
  Map<Integer, Person> people = new HashMap<Integer, Person>();

  public PersonController(MeterRegistry registry) {
    people.put(1, new Person("one"));
    people.put(2, new Person("two"));
    // constructs a gauge to monitor the size of the population
    registry.mapSize(people, "population");
  }

  @GetMapping("/api/people")
  public Collection<Person> listPeople() {
    return people.values();
  }

  @GetMapping("/api/person/")
  public Person findPerson(@PathVariable Integer id) {
    return people.get(id);
  }
}
