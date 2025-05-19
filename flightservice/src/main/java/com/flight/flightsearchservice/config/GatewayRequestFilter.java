package com.flight.flightsearchservice.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GatewayRequestFilter implements Filter {

  private static final String EXPECTED_SECRET = "InternalGateway"; // Same key as in API gateway

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    String gatewaySecret = req.getHeader("X-Gateway-Secret");
    System.out.println("Received Header: X-Gateway-Secret = " + gatewaySecret);
    String requestURI = req.getRequestURI();
    if (requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs")) {
      chain.doFilter(request, response); // Bypass the filter
      return;
    }
    if (gatewaySecret == null || !gatewaySecret.equals(EXPECTED_SECRET)) {
      ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
          "Access Denied: Requests must come from API Gateway!");
      return;
    }

    chain.doFilter(request, response);
  }
}
