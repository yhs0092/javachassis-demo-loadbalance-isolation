package com.github.yhs0092.client;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ClientTest {
  @Before
  public void beforeTest() throws Exception {
    Log4jUtils.init();
    BeanUtils.init();
  }

  @Test
  public void testInvoke() {
    RestTemplate restTemplate = RestTemplateBuilder.create();
    String url = "cse://loadbalance-isolation-server/hello/status";
    final ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
    System.out.println(responseEntity.getStatusCodeValue() + ":" + responseEntity.getBody());
  }
}
