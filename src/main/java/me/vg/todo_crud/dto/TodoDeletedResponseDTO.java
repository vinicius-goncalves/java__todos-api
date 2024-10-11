package me.vg.todo_crud.dto;

import me.vg.todo_crud.entities.TodoEntity;

public record TodoDeletedResponseDTO(boolean deleted, TodoEntity todoDeleted) {}
