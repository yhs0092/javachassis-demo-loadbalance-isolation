package com.github.yhs0092.client.console;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.github.yhs0092.hello.common.Holder;
import com.github.yhs0092.hello.common.Person;

@RestSchema(schemaId = "client")
@RequestMapping(path = "/client")
public class Client {
  RestTemplate restTemplate = RestTemplateBuilder.create();

  @GetMapping(path = "/template")
  public String testTemplate() {
    Holder<Person> param = new Holder<>();
    Person person = new Person();
    person.setName("hello");
    param.setData(person);

    Holder<Person> result = restTemplate.postForObject("cse://loadbalance-isolation-server/generic/template", param, Holder.class);
    StringBuilder response = new StringBuilder()
        .append(result.getClass())
        .append(":")
        .append(result.getData().getClass())
        .append(":")
        .append(result.getData().getName());
    return response.toString();
  }
}
