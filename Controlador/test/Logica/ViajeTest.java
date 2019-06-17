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
public class ViajeTest {
    
    public ViajeTest() {
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
     * Test of addAcompañante method, of class Viaje.
     */
    @Test
    public void testAddAcompañante() {
        System.out.println("addAcompa\u00f1ante");
        int pID = 4;
        Viaje viaje = new Viaje(0);
        Viaje instance = viaje;
        instance.addAcompañante(pID);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of confirmarAcompañante method, of class Viaje.
     */
    @Test
    public void testConfirmarAcompañante() {
        System.out.println("confirmarAcompa\u00f1ante");
        int pId = 4;
        Viaje viaje = new Viaje(0);
        Viaje instance = viaje;
        instance.confirmarAcompañante(pId);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of mayorA70Porciento method, of class Viaje.
     */
    @Test
    public void testMayorA70Porciento() {
        System.out.println("mayorA70Porciento");
        Viaje viaje = new Viaje(3);
        Viaje instance = viaje;
        boolean expResult = false;
        boolean result = instance.mayorA70Porciento();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
