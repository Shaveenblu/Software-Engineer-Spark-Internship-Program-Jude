package com.jude.sparkinternshipjude;


import com.jude.sparkinternshipjude.model.Task;
import com.jude.sparkinternshipjude.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @AfterEach
    void cleanup() {
        taskRepository.deleteAll();
    }

    @Test
    void testCreateAndRetrieveTask() {
        String baseUrl = "http://localhost:" + port + "/api/tasks";
        Task newTask = new Task("Integration Test Task");

        // Create task
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(
            baseUrl, newTask, Task.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());

        // Retrieve task
        ResponseEntity<Task[]> getResponse = restTemplate.getForEntity(
            baseUrl, Task[].class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertTrue(getResponse.getBody().length > 0);
    }

    @Test
    void testCompleteAndDeleteTask() {
        String baseUrl = "http://localhost:" + port + "/api/tasks";
        Task savedTask = taskRepository.save(new Task("Task to Complete"));

        // Complete task
        restTemplate.put(baseUrl + "/" + savedTask.getId() + "/complete", null);
        Task completedTask = taskRepository.findById(savedTask.getId()).orElse(null);
        assertNotNull(completedTask);
        assertTrue(completedTask.isCompleted());

        // Delete task
        restTemplate.delete(baseUrl + "/" + savedTask.getId());
        assertFalse(taskRepository.existsById(savedTask.getId()));
    }
}