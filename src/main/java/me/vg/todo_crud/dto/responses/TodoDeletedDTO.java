package me.vg.todo_crud.dto.responses;

public record TodoDeletedDTO(boolean deleted, int code) {

    public static TodoDeletedDTO of(boolean deleted, int code) {
        return new TodoDeletedDTO(deleted, code);
    }
}
