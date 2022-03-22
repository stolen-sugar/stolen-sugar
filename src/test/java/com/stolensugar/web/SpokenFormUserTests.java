package com.stolensugar.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class SpokenFormUserTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createSpokenFormUserShouldReturnCreateNewSpokenFormUser() throws Exception {
        String requestJson = "{\n" +
                "    \"userId\": \"2\",\n" +
                "    \"spokenFormId\": \"1\",\n" +
                "    \"choice\": \"green\"\n" +
                "}";

        String responseJson = "{\"spokenFormUser\":{\"spokenFormId\":\"1\"," +
                "\"userId\":\"2\",\"choice\":\"green\",\"choiceHistory\":null," +
                "\"details\":null,\"updatedBranch\":null}}";

        String path = "/spokenformuser";

        this.mockMvc.perform(post(path)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
            .characterEncoding("utf-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(responseJson)));
    }
}


