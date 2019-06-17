package com.example.tab1;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the EmailValidator logic.
 */
public class ComprobarDatosTest {

    @Test
    public void ComprobarDatos_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(ComprobarDatos.isValidEmail("tavo.2360@estudiante.com"));
    }
    @Test
    public void ComprobarDatos_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(ComprobarDatos.isValidEmail("tavo.2360@estudias"));
    }
    @Test
    public void ComprobarDatos_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(ComprobarDatos.isValidEmail("tavo.2360@estudiante..com"));
    }
    @Test
    public void ComprobarDatos_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(ComprobarDatos.isValidEmail("@estudiante"));
    }
    @Test
    public void ComprobarDatos_EmptyString_ReturnsFalse() {
        assertFalse(ComprobarDatos.isValidEmail(""));
    }
    @Test
    public void ComprobarDatos_NullEmail_ReturnsFalse() {
        assertFalse(ComprobarDatos.isValidEmail(null));
    }
}