package pl.springboot2.karoljanik.wykopclone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    Flyway flyway;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("shouldReturn404WhenNoPost")
    void getPost() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", 7))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andReturn();
        String actual = mvcResult.getResolvedException().getMessage();
        Assertions.assertEquals("No post with id: 7",actual);
    }

    @Test
    @DisplayName("shouldReturn404WhenNoTag")
    void getPostsByTag() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/by-tag/{tagId}", 4))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andReturn();
        String actual = mvcResult.getResolvedException().getMessage();
        Assertions.assertEquals("No tag with id: 4",actual);
    }

    @Test
    @DisplayName("shouldReturn404WhenNoUser")
    void getPostsByUsername() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/by-user/{username}", "user 4"))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andReturn();
        String actual = mvcResult.getResolvedException().getMessage();
        Assertions.assertEquals("No user 4 in database",actual);
    }

    @AfterEach
    public void get() {
        flyway.clean();
        flyway.migrate();
    }
}
