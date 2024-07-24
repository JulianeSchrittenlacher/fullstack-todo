package org.example.recapspring.service;

import org.example.recapspring.model.Status;
import org.example.recapspring.model.Todo;
import org.example.recapspring.model.TodoDTO;
import org.example.recapspring.repository.Repo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class TodoServiceTest {
    private final Repo mockRepo = mock(Repo.class);
    private final UtilService mockUtilService = mock(UtilService.class);
    TodoService service = new TodoService(mockRepo, mockUtilService);

    @Test
    void saveTodo() {
        //GIVEN
        TodoDTO dto1 = new TodoDTO("Hallo", Status.OPEN);
        Todo expected = new Todo("Test Id","Hallo", Status.OPEN);
        when(mockRepo.save(expected)).thenReturn(expected);
        when(mockUtilService.generateId()).thenReturn("Test Id");
        //WHEN THEN
        Todo actual = service.saveTodo(dto1);
        assertEquals(expected,actual);
        // assertEquals(1,actual.id());
        verify(mockRepo).save(expected);
        //  verify(service).save(dto);
        verify(mockUtilService).generateId();
    }

    @Test
    void getAllTodos() {
        //GIVEN
        Todo t1 = new Todo("1","Hallo",Status.OPEN);
        Todo t2 = new Todo("2","Grump",Status.OPEN);
        Todo t3 = new Todo("3","Jule",Status.OPEN);
        List<Todo> todos = List.of(t1,t2,t3);
        when(mockRepo.findAll()).thenReturn(todos);
        //WHEN
        List<Todo> actual = service.getAllTodos();
        //THEN
        verify(mockRepo).findAll();
        assertEquals(todos,actual);

    }

    @Test
    void getById_whenCalledWithValidId_ThenReturnTodo() {
        //GIVEN
        Todo t1 = new Todo("1","Hallo",Status.OPEN);
        when(mockRepo.findById(t1.id())).thenReturn(Optional.of(t1));
        //WHEN
        Todo actual = service.getById(t1.id());
        //THEN
        verify(mockRepo).findById(t1.id());
        assertEquals(t1,actual);
    }

    @Test
    void getById_whenCalledWithInvalidId_thenThrowException() {
        //GIVEN
        String invalidId = "1";
        when(mockRepo.findById(invalidId)).thenReturn(Optional.empty());
        //WHEN
        //THEN
        assertThrows(NoSuchElementException.class,()->service.getById(invalidId));
        verify(mockRepo).findById(invalidId);
    }

    @Test
    void updateTodo() {
        //GIVEN
        Todo todo = new Todo("1","Hallo",Status.OPEN);
        TodoDTO todoDTO = new TodoDTO("Hallo", Status.OPEN);
        when(mockRepo.findById("1")).thenReturn(Optional.of(todo));
        when(mockRepo.save(todo)).thenReturn(todo);
        //WHEN
        Todo actual = service.updateTodo("1",todoDTO);
        //THEN
        verify(mockRepo).findById("1");
        verify(mockRepo).save(todo);
        assertEquals(todo, actual);

    }

    @Test
    void updateTodo_whenCalledWithInvalidId_thenThrowException() {
        when(mockRepo.findById("1")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.updateTodo("1",new TodoDTO("Hallo", Status.OPEN)));
        verify(mockRepo).findById("1");

    }

    @Test
    void deleteTodo() {
        //GIVEN
        //mockRepo.save(new Todo("1","Hallo",Status.OPEN));
        //WHEN
        service.deleteTodo("1");
        //THEN
        verify(mockRepo).deleteById("1");


    }
}