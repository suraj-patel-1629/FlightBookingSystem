package com.example.bookingservice.config;

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

  private static final String EXPECTED_SECRET = "InternalGateway";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    String gatewaySecret = req.getHeader("X-Gateway-Secret");
    System.out.println("Received Header: X-Gateway-Secret = " + gatewaySecret);

    if (gatewaySecret == null || !gatewaySecret.equals(EXPECTED_SECRET)) {
      ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
          "Access Denied: Requests must come from API Gateway!");
      return;
    }

    chain.doFilter(request, response);
  }
}
