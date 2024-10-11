package me.vg.todo_crud.controllers;

import jakarta.servlet.http.HttpServletRequest;
import me.vg.todo_crud.dto.TodoDeletedResponseDTO;
import me.vg.todo_crud.entities.TodoEntity;
import me.vg.todo_crud.repositories.TodoRepository;
import me.vg.todo_crud.services.TodoService;
import me.vg.todo_crud.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public ResponseEntity<List<TodoEntity>> getAll() {
        Optional<List<TodoEntity>> allTodos = this.todoService.getAllTodos();
        return allTodos
                .map(todos -> new ResponseEntity<>(todos, todos.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/query")
    public ResponseEntity<List<TodoEntity>> getFromQuery(
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(name = "sortBy", defaultValue = "asc") String sortBy,

            HttpServletRequest request

    ) {
        List<Criteria> criteriaList = new ArrayList<>();

        for(Map.Entry<String, String> params : Utils.getRequestParams(request)) {
            String key = params.getKey(), value = params.getValue();
            System.out.println(Utils.isBoolean(value));

            if(Utils.isBoolean(value)) {
                criteriaList.add(Criteria.where(key).is(Boolean.parseBoolean(value)));
            } else {
                criteriaList.add(Criteria.where(key).regex(value, "i"));
            }
        }

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(criteriaList));

        System.out.println(query);

        Sort sort = Sort.by(sortBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, orderBy);
        return this.todoService.find(query, sort)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<TodoEntity> getById(@PathVariable("id") String id) {
        Optional<TodoEntity> todoFound = this.todoService.getTodoById(id);

        return todoFound
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<TodoEntity> create(@RequestBody TodoEntity todo) {
        return ResponseEntity.ok(this.todoService.createTodo(todo));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<TodoDeletedResponseDTO> delete(@PathVariable("id") String id) {
        return this.todoService.deleteTodoById(id)
                .map(todo -> ResponseEntity.ok(new TodoDeletedResponseDTO(true, todo)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

