package com.artorias.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by devin on 10/14/16.
 */


@RestController
public class PostController {
    @RequestMapping(method = RequestMethod.GET, path="/api/posts", produces="application/json")
    public String list() {
        return "Hello";
    }
}
