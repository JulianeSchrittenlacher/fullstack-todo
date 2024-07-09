package org.example.recapspring.controller;

import org.example.recapspring.model.Status;
import org.example.recapspring.model.Todo;
import org.example.recapspring.repository.Repo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest //
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Repo repo;

    @Test
    void saveTodo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                    "description": "Bla",
                    "status": "DONE"
                      }
               """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                    {
                     "description": "Bla",
                    "status": "DONE"
                    }

                """))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void getAllTodos() throws Exception {
        //GIVEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
            []
            """));
    }

    @Test
    void getById_whenCalledWithValidId_ReturnTodo() throws Exception {
        //GIVEN
        Todo existingTodo = new Todo("1","Bla mm",Status.OPEN);
        repo.save(existingTodo);
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/1"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                        "id": "1",
                        "description": "Bla mm",
                        "status": "OPEN"
                        }
"""));
    }

    @Test
    void getById_whenCalledWithUnvalidId_thenStatus404() throws Exception {
        //GIVEN
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/1"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void updateTodo() throws Exception {
        Todo oldTodo = new Todo("1","Bla mmm",Status.OPEN);
        repo.save(oldTodo);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/1")
                .contentType(MediaType.APPLICATION_JSON).content("""
                    {
                    "description": "Bla mmm",
                    "status": "IN_PROGRESS"
                    }

                  """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.content().json("""
                    {
                    "description": "Bla mmm",
                    "status": "IN_PROGRESS"
                    }
"""));
    }

    @Test
    void deleteTodo() throws Exception {
        Todo existingTodo = new Todo("1","Bla mmm",Status.OPEN);
        repo.save(existingTodo);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}