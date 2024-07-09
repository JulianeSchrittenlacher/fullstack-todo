package org.example.recapspring.service;

import lombok.RequiredArgsConstructor;
import org.example.recapspring.model.Status;
import org.example.recapspring.model.Todo;
import org.example.recapspring.model.TodoDTO;
import org.example.recapspring.repository.Repo;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Repository
@RequiredArgsConstructor

public class TodoService {
    private final Repo repo;
    private final UtilService utilService;

    public Todo saveTodo(TodoDTO todoDTO) {
        Todo newTodo = new Todo(utilService.generateId(),todoDTO.description(),todoDTO.status());
        return repo.save(newTodo);
    }
    public List<Todo> getAllTodos() {
        return repo.findAll();
    }

    public Todo getById(String id) {
        Optional<Todo> optionalTodo = repo.findById(id);
        return optionalTodo.orElseThrow(()-> new NoSuchElementException("Todo with id "+id+" not found"));
    }

    public Todo updateTodo(String id, TodoDTO todoDTO) {
        Todo foundTodo = repo.findById(id).orElseThrow();
        return repo.save(foundTodo.withDescription(todoDTO.description()).withStatus(todoDTO.status()));
    }

    public void deleteTodo(String id) {
            repo.deleteById(id);

    }
}
