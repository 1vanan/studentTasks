package com.example.taco;

import java.util.Arrays;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public Hello home() {

        Hello hello = new Hello();
        hello.setName("huy");
        hello.setNames(Arrays.asList("1", "2"));
        return hello;
    }
}
