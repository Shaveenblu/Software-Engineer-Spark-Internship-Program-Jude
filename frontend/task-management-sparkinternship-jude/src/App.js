import React, { useState, useEffect } from 'react';
import {
  Container,
  Paper,
  TextField,
  Button,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
  Typography,
  Box,
  Checkbox
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/api/tasks';

function App() {
  const [tasks, setTasks] = useState([]);
  const [newTask, setNewTask] = useState('');

  // Fetch all tasks
  const fetchTasks = async () => {
    try {
      const response = await axios.get(API_BASE_URL);
      setTasks(response.data);
    } catch (error) {
      console.error('Error fetching tasks:', error);
    }
  };

  // Add new task
  const addTask = async (e) => {
    e.preventDefault();
    if (!newTask.trim()) return;

    try {
      await axios.post(API_BASE_URL, {
        description: newTask
      });
      setNewTask('');
      fetchTasks();
    } catch (error) {
      console.error('Error adding task:', error);
    }
  };

  // Mark task as completed
  const markAsCompleted = async (taskId) => {
    try {
      await axios.put(`${API_BASE_URL}/${taskId}/complete`);
      fetchTasks();
    } catch (error) {
      console.error('Error marking task as completed:', error);
    }
  };

  // Delete task
  const deleteTask = async (taskId) => {
    try {
      await axios.delete(`${API_BASE_URL}/${taskId}`);
      fetchTasks();
    } catch (error) {
      console.error('Error deleting task:', error);
    }
  };

  // Load tasks on component mount
  useEffect(() => {
    fetchTasks();
  }, []);

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Task Management
        </Typography>

        <Box component="form" onSubmit={addTask} sx={{ mb: 4 }}>
          <TextField
            fullWidth
            variant="outlined"
            label="New Task"
            value={newTask}
            onChange={(e) => setNewTask(e.target.value)}
            sx={{ mr: 2 }}
          />
          <Button
            type="submit"
            variant="contained"
            color="primary"
            sx={{ mt: 2 }}
            fullWidth
          >
            Add Task
          </Button>
        </Box>

        <List>
          {tasks.map((task) => (
            <ListItem
              key={task.id}
              sx={{
                bgcolor: 'background.paper',
                mb: 1,
                border: 1,
                borderColor: 'grey.200',
                borderRadius: 1,
              }}
            >
              <Checkbox
                checked={task.completed}
                onChange={() => markAsCompleted(task.id)}
                color="primary"
              />
              <ListItemText
                primary={task.description}
                sx={{
                  textDecoration: task.completed ? 'line-through' : 'none',
                }}
              />
              <ListItemSecondaryAction>
                <IconButton
                  edge="end"
                  aria-label="delete"
                  onClick={() => deleteTask(task.id)}
                >
                  <DeleteIcon />
                </IconButton>
              </ListItemSecondaryAction>
            </ListItem>
          ))}
        </List>
      </Paper>
    </Container>
  );
}


export default App;