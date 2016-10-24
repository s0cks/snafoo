package io.github.s0cks.snafoo;

import io.github.s0cks.snafoo.data.PurchasedSnack;
import io.github.s0cks.snafoo.data.Snack;
import io.github.s0cks.snafoo.data.Suggestion;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public enum SnafooSnackService{
  INSTANCE;

  private final RestTemplate template = new RestTemplate();

  private SnafooSnackService(){
    this.template.setInterceptors(Collections.singletonList(SnafooAuthInterceptor.INSTANCE));
  }

  public Snack[] getSnacks(){
    return this.template.getForObject("https://api-snacks.nerderylabs.com/v1/snacks", PurchasedSnack[].class);
  }

  public Snack postSuggestion(Suggestion s){
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(s.toString(), headers);
    return this.template.postForObject("https://api-snacks.nerderylabs.com/v1/snacks", entity, PurchasedSnack.class);
  }
}