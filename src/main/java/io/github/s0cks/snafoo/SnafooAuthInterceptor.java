package io.github.s0cks.snafoo;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

enum SnafooAuthInterceptor
implements ClientHttpRequestInterceptor{
  INSTANCE;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
  throws IOException {
    HttpRequest req = new HttpRequestWrapper(request);
    req.getHeaders().set("Authorization", "ApiKey 3d97d52d-6e8b-4d50-81c2-fec5ceff96a9");
    return execution.execute(req, body);
  }
}