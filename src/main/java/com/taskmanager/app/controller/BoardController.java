package com.taskmanager.app.controller;

import com.taskmanager.app.entity.Board;
import com.taskmanager.app.service.BoardService;
import com.taskmanager.app.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final TaskListService taskListService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("boards", boardService.getAllBoards());
        return "index";
    }

    @PostMapping("/boards")
    public String createBoard(@RequestParam String name, @RequestParam String description) {
        if (name == null || name.trim().isEmpty()) {
            return "redirect:/"; // Do not create empty board
        }
        Board board = new Board();
        board.setName(name.trim());
        board.setDescription(description != null ? description.trim() : "");
        boardService.createBoard(board);
        return "redirect:/";
    }

    @GetMapping("/boards/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        Board board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "board";
    }

    @PostMapping("/boards/{boardId}/lists")
    public String createList(@PathVariable Long boardId, @RequestParam String name) {
        if (name != null && !name.trim().isEmpty()) {
            taskListService.createTaskList(boardId, name.trim());
        }
        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/boards/delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/";
    }

    @PostMapping("/boards/{boardId}/lists/{listId}/delete")
    public String deleteList(@PathVariable Long boardId, @PathVariable Long listId) {
        taskListService.deleteTaskList(listId);
        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/boards/{boardId}/lists/reorder")
    @ResponseBody
    public org.springframework.http.ResponseEntity<Void> reorderLists(@PathVariable Long boardId, @RequestBody java.util.List<Long> listIds) {
        taskListService.reorderLists(boardId, listIds);
        return org.springframework.http.ResponseEntity.ok().build();
    }
}
