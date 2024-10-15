package me.vg.todo_crud.repositories;

import me.vg.todo_crud.entities.TodoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<TodoEntity, String> {}
