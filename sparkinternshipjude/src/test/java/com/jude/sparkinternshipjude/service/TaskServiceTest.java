package com.jude.sparkinternshipjude.service;

import com.jude.sparkinternshipjude.model.Task;
import com.jude.sparkinternshipjude.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task("Test Task");
        testTask.setId(1L);
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Task createdTask = taskService.createTask(new Task("Test Task"));

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getDescription());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(
            testTask,
            new Task("Test Task 2")
        );
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> returnedTasks = taskService.getAllTasks();

        assertEquals(2, returnedTasks.size());
        verify(taskRepository).findAll();
    }

    @Test
    void testMarkTaskAsCompleted() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Task completedTask = taskService.markTaskAsCompleted(1L);

        assertNotNull(completedTask);
        assertTrue(completedTask.isCompleted());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testMarkTaskAsCompletedNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Task result = taskService.markTaskAsCompleted(1L);

        assertNull(result);
        verify(taskRepository).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));

        Task foundTask = taskService.getTaskById(1L);

        assertNotNull(foundTask);
        assertEquals(1L, foundTask.getId());
        verify(taskRepository).findById(1L);
    }
}