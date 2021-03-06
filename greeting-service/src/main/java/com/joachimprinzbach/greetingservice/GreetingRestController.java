package com.joachimprinzbach.greetingservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class GreetingRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingRestController.class);
    private static final int MAX_AGE_SECONDS = 86400;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${CONCAT_SERVICE_URL}")
    private String concatServiceUrl;

    @GetMapping(path = "greeting")
    public GreetingDto greet(@CookieValue(value = "email", required = false, defaultValue = "unknown") String email) {
        LOGGER.info("Calling greeting");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "email=" + email);
        LOGGER.info("Forward cookie email: " + email);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        HttpEntity<String> response = restTemplate.exchange(concatServiceUrl, HttpMethod.GET, request, String.class);
        LOGGER.info("Received value from concat service: " + request.getBody());
        return new GreetingDto("Hello " + response.getBody());
    }

    @RequestMapping(path = "login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Calling login");
        String email = request.getParameter("email");
        Cookie cookie = createCookie("email", email);
        response.addCookie(cookie);
        return "Set email=" + email;
    }

    private Cookie createCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(MAX_AGE_SECONDS);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
