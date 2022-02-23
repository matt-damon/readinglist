package com.manning.readinglist;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component
public class ReaderHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean res = Reader.class.isAssignableFrom(parameter.getParameterType());
    return res;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) throws Exception {

      Authentication auth = (Authentication) webRequest.getUserPrincipal();//通过认证后，连带fullname，password都带给controller
      Object res = auth != null && auth.getPrincipal() instanceof Reader ? auth.getPrincipal() : null;
      return res;
  }

}
