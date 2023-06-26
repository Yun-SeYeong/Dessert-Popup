package com.popup.dessert.dessertpopup.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String authorization = ((HttpServletRequest) request).getHeader("Authorization");

    String token = getToken(authorization);

    if (StringUtils.isNotEmpty(token)) {
      SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
    }

    chain.doFilter(request, response);
  }

  private static String getToken(String authorization) {
    String token = null;

    if (StringUtils.isNotEmpty(authorization) && authorization.startsWith("Bearer ")) {
      token = authorization.substring(7);
    }

    return token;
  }
}
