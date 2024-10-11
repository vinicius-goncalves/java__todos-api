package me.vg.todo_crud.repositories;

import me.vg.todo_crud.entities.TodoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends MongoRepository<TodoEntity, String> {

    @Query("{ ?0: { $eq: ?1 } }")
    Optional<List<TodoEntity>> findByField(@Param("field") String field, String fieldValue);
}
