package com.helix.api.test;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
//@Tag(name = "Authentication", description = "Auth and token APIs")
public class TestController {

    @GetMapping("/info")
    public ResponseEntity<?> info() {
        return new ResponseEntity<>("Project Helix Version ", HttpStatus.OK);
    }


}
