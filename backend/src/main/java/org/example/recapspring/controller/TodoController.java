package org.example.recapspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.recapspring.model.Todo;
import org.example.recapspring.model.TodoDTO;
import org.example.recapspring.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todo")
@RequiredArgsConstructor


public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public Todo saveTodo(@RequestBody TodoDTO todoDTO) {
        return todoService.saveTodo(todoDTO);
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable String id) {
        return todoService.getById(id);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody TodoDTO todoDTO) {
        return todoService.updateTodo(id,todoDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
    }



}
