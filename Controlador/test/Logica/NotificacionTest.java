/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jeja1
 */
public class NotificacionTest {
    
    public NotificacionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getID method, of class Notificacion.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        Notificacion notificacion = new Notificacion(1,"hola",3);
        Notificacion instance = notificacion;
        int expResult = 1;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTexto method, of class Notificacion.
     */
    @Test
    public void testGetTexto() {
        System.out.println("getTexto");
        Notificacion notificacion = new Notificacion(1,"hola",3);
        Notificacion instance = notificacion;
        String expResult = "hola";
        String result = instance.getTexto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTipo method, of class Notificacion.
     */
    @Test
    public void testGetTipo() {
        System.out.println("getTipo");
        Notificacion notificacion = new Notificacion(1,"hola",3);
        Notificacion instance = notificacion;
        int expResult = 3;
        int result = instance.getTipo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
