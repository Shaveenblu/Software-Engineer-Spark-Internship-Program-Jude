package com.jude.sparkinternshipjude.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    void testTaskCreation() {
        Task task = new Task("Test Task");
        
        assertEquals("Test Task", task.getDescription());
        assertFalse(task.isCompleted());
    }

    @Test
    void testTaskCompletion() {
        Task task = new Task("Test Task");
        task.setCompleted(true);
        
        assertTrue(task.isCompleted());
    }

    @Test
    void testTaskId() {
        Task task = new Task("Test Task");
        task.setId(1L);
        
        assertEquals(1L, task.getId());
    }
}