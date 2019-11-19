package com.example.springtest.xss;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * xss필터를 테스트 하기 위한 simple 컨들롤러
 * (테스트를 위한 컨트롤러 xss필터 기능은 현재 안넣음)
 */
@RestController
public class XssTestSimpleController {

    @GetMapping(value = "/xss/form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> formData(@RequestParam String contents) {
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @GetMapping(value = "/xss/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> json(@RequestBody Map<String, String> param) {
        return new ResponseEntity<>(param.get("contents"), HttpStatus.OK);
    }
}
