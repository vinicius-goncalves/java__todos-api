package me.vg.todo_crud.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void booleanStringShouldBeTrue() {
        assertTrue(Utils.isBoolean("true"));
        assertTrue(Utils.isBoolean("false"));
        assertTrue(Utils.isBoolean("fAlSe"));
        assertTrue(Utils.isBoolean("TrUe"));
    }

    @Test
    void notBooleanStringShouldBeFalse() {
        assertFalse(Utils.isBoolean("1"));
        assertFalse(Utils.isBoolean("{}"));
        assertFalse(Utils.isBoolean("hEllO_WorLD"));
    }
}