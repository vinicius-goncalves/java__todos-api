package me.vg.todo_crud.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "todos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TodoEntity {

    @MongoId(targetType = FieldType.OBJECT_ID)
    private String _id;
    private String title;
    private String description;
    private boolean completed;
}
