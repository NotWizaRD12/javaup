package com.taskmanager.app.controller;

import com.taskmanager.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public String createTask(@RequestParam Long boardId,
                             @RequestParam Long taskListId,
                             @RequestParam String title,
                             @RequestParam String description) {
        if (title != null && !title.trim().isEmpty()) {
            taskService.createTask(taskListId, title.trim(), description != null ? description.trim() : "");
        }
        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/{taskId}/delete")
    public String deleteTask(@RequestParam Long boardId, @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/{taskId}/move")
    public String moveTask(@RequestParam Long boardId, @PathVariable Long taskId, @RequestParam Long newListId) {
        taskService.updateTask(taskId, newListId);
        return "redirect:/boards/" + boardId;
    }
}
