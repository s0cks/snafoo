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

/**
 * SnafooSnackService
 *
 * Utility class for helping collect the {@link Snack}s from the Snafoo Web Service.
 * Singleton (using enum pattern) to cull creating any useless instances.
 *
 */
public enum SnafooSnackService{
  INSTANCE;

  /**
   * Helper function for getting a set of {@link Snack}s, literally just wraps {@link #getSnacks} into a {@link Set}
   *
   * @return Set of collected {@link Snack}s
   */
  public static Set<Snack> allSnacks(){
    Set<Snack> snacks = new HashSet<>();
    snacks.addAll(Arrays.asList(INSTANCE.getSnacks()));
    return snacks;
  }

  private static final String API = "https://api-snacks.nerderylabs.com/v1/snacks";

  private final RestTemplate template = new RestTemplate();

  private SnafooSnackService(){
    // Add custom interceptor for authentication to the service. Simpler than overriding each request
    this.template.setInterceptors(Collections.singletonList(SnafooAuthInterceptor.INSTANCE));
  }

  /**
   * GET https://api-snacks.nerderylabs.com/v1/snacks
   *
   * See https://api-snacks.nerderylabs.com/v1/help/api/get-snacks for relevant information on endpoint data
   *
   * @return {@link Snack}[]
   */
  public Snack[] getSnacks(){
    return this.template.getForObject(API, Snack[].class);
  }

  /**
   * POST
   *
   * See https://api-snacks.nerderylabs.com/v1/help/api/post-snacks for relevant information on endpoint data
   *
   * @param s The current {@link Suggestion} object
   * @return The created {@link Snack} from the Web Service
   */
  public Snack addSuggestion(Suggestion s){
    // Since we are posting data we have to hint at using application/json as our content-type
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(s.toString(), headers);
    return this.template.postForObject(API, entity, Snack.class);
  }
}