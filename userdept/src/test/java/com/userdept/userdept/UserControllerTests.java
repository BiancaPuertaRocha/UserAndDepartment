package com.userdept.userdept;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userdept.userdept.controllers.UserController;
import com.userdept.userdept.entities.Department;
import com.userdept.userdept.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

    @MockBean
    @Autowired
    private UserController controller;

    @Test
    void createUser() throws Exception{
        Department newDept = new Department(Long.valueOf(1), "dept");
        User newUser = new User();
        newUser.setName("Bianca");

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        newUser.setDepartment(newDept);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void deleteUser() throws Exception{
        User newUser = new User();
        newUser.setName("Bianca");
        Department newDept = new Department(Long.valueOf(1), "dept");
        newUser.setDepartment(newDept);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(result -> mockMvc.perform(MockMvcRequestBuilders
                                .delete("/users/{id}", 1L)
                                .contentType("application/json"))
                        .andExpect(MockMvcResultMatchers.status().isOk()));
    }

    @Test
    void editUser() throws Exception{
        User newUser = new User();
        newUser.setName("Bianca");
        Department newDept = new Department(Long.valueOf(1), "dept");
        newUser.setDepartment(newDept);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(result -> mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(getContentEdit()))
                        .andExpect(MockMvcResultMatchers.status().isOk()));

    }

    private String getContentEdit(){
        return "{\"name\":\"marina\",\"department\":{\"id\":2}}";
    }
}
