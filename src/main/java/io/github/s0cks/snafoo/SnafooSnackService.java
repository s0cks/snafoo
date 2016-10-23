package io.github.s0cks.snafoo;

import io.github.s0cks.snafoo.data.PurchasedSnack;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public enum SnafooSnackService{
  INSTANCE;

  private final RestTemplate template = new RestTemplate();

  private SnafooSnackService(){
    this.template.setInterceptors(Collections.singletonList(SnafooAuthInterceptor.INSTANCE));
  }

  public PurchasedSnack[] getSnacks(){
    return this.template.getForObject("https://api-snacks.nerderylabs.com/v1/snacks", PurchasedSnack[].class);
  }
}