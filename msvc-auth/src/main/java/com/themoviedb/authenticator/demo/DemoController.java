package com.themoviedb.authenticator.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hola")
@AllArgsConstructor
public class DemoController {

    @GetMapping(value = "demo")
    public String welcome() {
        return "Welcome form secure endpoint";
    }
}
