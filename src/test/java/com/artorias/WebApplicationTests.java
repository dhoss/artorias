package com.artorias;

import com.artorias.controllers.api.PostController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class WebApplicationTests {

    @Autowired
    PostController postController;

	@Test
	public void contextLoads() throws Exception {
        assertThat(postController).isNotNull();
	}

}
