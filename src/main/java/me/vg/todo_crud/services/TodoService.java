package me.vg.todo_crud.services;

import me.vg.todo_crud.entities.TodoEntity;
import me.vg.todo_crud.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<List<TodoEntity>> getAllTodos() {
        return Optional.of(this.todoRepository.findAll());
    }

    public List<TodoEntity> find(Query query) {
        return this.mongoTemplate.find(query, TodoEntity.class);
    }

    public Optional<List<TodoEntity>> find(Query query, Sort sort) {
        query.with(sort);
        List<TodoEntity> queryResult = this.mongoTemplate.find(query, TodoEntity.class);
        return !queryResult.isEmpty() ? Optional.of(queryResult) : Optional.empty();
    }

    public Optional<TodoEntity> getTodoById(String id) {
        return this.todoRepository.findById(id);
    }

    public TodoEntity createTodo(TodoEntity todo) {
        return this.todoRepository.save(todo);
    }

    public Optional<TodoEntity> deleteTodoById(String id) {
        Optional<TodoEntity> todoFound = this.getTodoById(id);
        return todoFound.map(todo -> {
            this.todoRepository.deleteById(id);
            return todo;
        });
    }
}
