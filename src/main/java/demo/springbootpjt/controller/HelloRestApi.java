package demo.springbootpjt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestApi {
    @GetMapping("/hellorest")
    public String hello(String testText){
        return "hello " + testText;
    }

}
