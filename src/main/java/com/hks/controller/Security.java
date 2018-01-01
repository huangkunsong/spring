package com.hks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Huangkunsong
 */
@Controller
public class Security {

    private static final Logger log = LoggerFactory.getLogger(Security.class);


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() throws Throwable{
        return "login";
    }
}
