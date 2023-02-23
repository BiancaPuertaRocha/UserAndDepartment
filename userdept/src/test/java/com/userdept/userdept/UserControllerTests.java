package com.userdept.userdept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userdept.userdept.controllers.UserController;
import com.userdept.userdept.entities.Department;
import com.userdept.userdept.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserController controller;

    @Test
    void createUser() throws Exception{
        Department newDept = new Department(Long.valueOf(1), "dept");
        User newUser = new User();
        newUser.setName("Bianca");
        newUser.setDepartment(newDept);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }
}
