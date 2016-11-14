package com.artorias.controllers.api;

import com.artorias.database.jooq.tables.pojos.Post;
import com.artorias.dto.PostDTO;
import com.artorias.service.DefaultPostService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by devin on 10/14/16.
 */
@Slf4j
@RestController
@RequestMapping(method = RequestMethod.GET, path = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    @Autowired
    private DSLContext dsl;



    @Autowired
    private DefaultPostService postService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDTO> list(@RequestParam(name="page", defaultValue = "1") int page) {
        return postService.listAsDto(page);
    }
}
