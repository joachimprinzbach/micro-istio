package com.joachimprinzbach.concatservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConcatRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConcatRestController.class);

    @GetMapping(path = "concat")
    public ResultDto concat(@CookieValue(value = "email", required = false, defaultValue = "unknown") String email) {
        LOGGER.info("Calling concat with cookie email="+email);
        return new ResultDto("dummy");
    }
}
