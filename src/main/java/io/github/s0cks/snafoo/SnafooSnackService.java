package io.github.s0cks.snafoo;

import io.github.s0cks.snafoo.model.rest.Snack;
import io.github.s0cks.snafoo.model.rest.Suggestion;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum SnafooSnackService{
  INSTANCE;

  public static Set<Snack> allSnacks(){
    Set<Snack> snacks = new HashSet<>();
    snacks.addAll(Arrays.asList(INSTANCE.getSnacks()));
    return snacks;
  }

  private static final String API = "https://api-snacks.nerderylabs.com/v1/snacks";

  private final RestTemplate template = new RestTemplate();

  private SnafooSnackService(){
    this.template.setInterceptors(Collections.singletonList(SnafooAuthInterceptor.INSTANCE));
  }

  public Snack[] getSnacks(){
    return this.template.getForObject(API, Snack[].class);
  }

  public Snack addSuggestion(Suggestion s){
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(s.toString(), headers);
    return this.template.postForObject(API, entity, Snack.class);
  }
}