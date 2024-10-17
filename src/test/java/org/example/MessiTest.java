package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessiTest {

    private Messi messi;

    @BeforeEach
    void setUp() {
        messi = new Messi();
    }

    @Test
    void messiTest() {
        assertEquals(37, messi.getEdad());
    }
}
