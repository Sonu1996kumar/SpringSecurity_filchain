package com.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/countries")
public class CountryController {
    //sign  in and singn out is open for all but this code for those who are logged in and valid token
    @PostMapping("addCountry")
    public String  addCountry(){
        return "Done";
    }
}
