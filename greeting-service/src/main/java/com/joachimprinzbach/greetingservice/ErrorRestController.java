package com.joachimprinzbach.greetingservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class ErrorRestController {

    @GetMapping(path = "maybeerror")
    public String maybeError() {
        boolean shouldThrowError = new Random().nextBoolean();
        if (shouldThrowError) {
            throw new RuntimeException("Error!");
        }
        return "Success";
    }
}
