package com.joachimprinzbach.concatservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConcatRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConcatRestController.class);

    @GetMapping(path = "concat")
    public ResultDto concat() {
        LOGGER.info("Calling concat CANARY!");
        return new ResultDto("dummy CANARY");
    }
}
