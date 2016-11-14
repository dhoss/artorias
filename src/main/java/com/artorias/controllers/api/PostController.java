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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private ModelMapper mapper;

    @Autowired
    private DefaultPostService postService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDTO> list() {
        java.lang.reflect.Type targetListType = new TypeToken<List<PostDTO>>() {}.getType();
        List<Post> posts = postService.list(1);
        log.debug("POSTS " + posts);
        List<PostDTO> mappedPosts = mapper.map(posts, targetListType);
        return mappedPosts;
    }
}
