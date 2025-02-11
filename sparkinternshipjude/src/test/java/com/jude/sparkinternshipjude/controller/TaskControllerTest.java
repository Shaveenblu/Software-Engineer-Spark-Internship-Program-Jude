package com.jude.sparkinternshipjude.controller;

import com.jude.sparkinternshipjude.controllers.TaskController;
import com.jude.sparkinternshipjude.model.Task;
import com.jude.sparkinternshipjude.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private Task testTask;
    private List<Task> taskList;

    @BeforeEach
    void setUp() {
        testTask = new Task("Test Task");
        testTask.setId(1L);
        taskList = Arrays.asList(testTask);
    }

    @Test
    void testCreateTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(testTask);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Test Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Task"));

        verify(taskService).createTask(any(Task.class));
    }

    @Test
    void testGetAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(taskList);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Test Task"))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(taskService).getAllTasks();
    }

    @Test
    void testMarkTaskAsCompleted() throws Exception {
        testTask.setCompleted(true);
        when(taskService.markTaskAsCompleted(1L)).thenReturn(testTask);

        mockMvc.perform(put("/api/tasks/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));

        verify(taskService).markTaskAsCompleted(1L);
    }

    @Test
    void testMarkTaskAsCompletedNotFound() throws Exception {
        when(taskService.markTaskAsCompleted(1L)).thenReturn(null);

        mockMvc.perform(put("/api/tasks/1/complete"))
                .andExpect(status().isNotFound());

        verify(taskService).markTaskAsCompleted(1L);
    }

    @Test
    void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());

        verify(taskService).deleteTask(1L);
    }
}
