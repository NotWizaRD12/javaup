package com.taskmanager.app.service;

import com.taskmanager.app.entity.Task;
import com.taskmanager.app.entity.TaskList;
import com.taskmanager.app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskListService taskListService;
    private final ParserService parserService;

    public Task createTask(Long taskListId, String title, String description) {
        TaskList taskList = taskListService.getTaskListById(taskListId);
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setTaskList(taskList);

        // Fetch quote from external API via Jsoup as a task "Fun Fact"
        task.setFunFact(parserService.getRandomQuote());

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void updateTask(Long id, Long newListId) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        TaskList newList = taskListService.getTaskListById(newListId);
        task.setTaskList(newList);
        taskRepository.save(task);
    }
}
