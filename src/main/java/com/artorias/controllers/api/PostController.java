package com.artorias.controllers.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by devin on 10/14/16.
 */

@RestController
@RequestMapping(method = RequestMethod.GET, path = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map list() {
        Map hello = new HashMap();
        hello.put("success", true);
        return hello;
    }
}
