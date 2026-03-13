package com.taskmanager.app.service;

import com.taskmanager.app.entity.Board;
import com.taskmanager.app.entity.TaskList;
import com.taskmanager.app.repository.TaskListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListService {
    private final TaskListRepository taskListRepository;
    private final BoardService boardService;

    public TaskList createTaskList(Long boardId, String name) {
        Board board = boardService.getBoardById(boardId);
        TaskList taskList = new TaskList();
        taskList.setName(name);
        taskList.setBoard(board);
        // Set position to the end
        taskList.setPosition(board.getTaskLists().size());
        return taskListRepository.save(taskList);
    }

    public void deleteTaskList(Long id) {
        taskListRepository.deleteById(id);
    }

    public TaskList getTaskListById(Long id) {
        return taskListRepository.findById(id).orElseThrow(() -> new RuntimeException("TaskList not found"));
    }

    public void reorderLists(Long boardId, List<Long> listIds) {
        // Iterate through the provided ordered list of IDs and update their positions
        for (int i = 0; i < listIds.size(); i++) {
            Long listId = listIds.get(i);
            TaskList list = getTaskListById(listId);
            if (list.getBoard().getId().equals(boardId)) {
                list.setPosition(i);
                taskListRepository.save(list);
            }
        }
    }
}
