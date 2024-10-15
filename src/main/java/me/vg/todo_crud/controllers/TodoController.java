package me.vg.todo_crud.controllers;

import jakarta.servlet.http.HttpServletRequest;
import me.vg.todo_crud.dto.responses.TodoDeletedDTO;
import me.vg.todo_crud.entities.TodoEntity;
import me.vg.todo_crud.services.TodoService;
import me.vg.todo_crud.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static me.vg.todo_crud.utils.RequestUtils.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping(path = "/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoEntity>> getAll() {
        Optional<List<TodoEntity>> allTodos = this.todoService.getAllTodos();

        return allTodos
                .filter(todos -> !todos.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(path = "/query")
    public ResponseEntity<List<TodoEntity>> getFromQuery(
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(name = "sortBy", defaultValue = "asc") String sortBy,
            HttpServletRequest request
    ) {
        Map<String, String> requestParams = getRequestParams(request);
        List<Criteria> criteriaList = createCriteriaListFromParamsRequest(requestParams);

        Sort sort = Utils.createSort(sortBy, orderBy);
        Query query = createMongoQueryUsingCriteria(criteriaList, sort);

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
    public ResponseEntity<TodoDeletedDTO> delete(@PathVariable("id") String id) {
        Optional<TodoEntity> todoDeleted = this.todoService.deleteTodoById(id);

        return todoDeleted
                .map(todo -> ResponseEntity.ok(TodoDeletedDTO.of(true, HttpStatus.OK.value())))
                .orElseGet(() -> ResponseEntity.ok(TodoDeletedDTO.of(false, HttpStatus.NOT_FOUND.value())));
    }
}

