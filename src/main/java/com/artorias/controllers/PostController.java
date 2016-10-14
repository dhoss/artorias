package com.artorias.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by devin on 10/14/16.
 */

@Api(basePath = "/api/posts", value = "Posts", description = "Blog post operations", produces = "application/json")
@RestController
@RequestMapping(method = RequestMethod.GET, path="/api/posts", produces= MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List posts", notes = "Lists posts")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "") })
    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public Map list() {
        Map hello = new HashMap();
        hello.put("success", true);
        return hello;
    }
}
