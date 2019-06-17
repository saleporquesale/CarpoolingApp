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
public class VehiculoTest {
    
    public VehiculoTest() {
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
     * Test of getModelo method, of class Vehiculo.
     */
    @Test
    public void testGetModelo() {
        System.out.println("getModelo");
        Vehiculo vehiculo = new Vehiculo ("Fiat","cfc002",5);
        Vehiculo instance = vehiculo;
        String expResult = "Fiat";
        String result = instance.getModelo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCapacidad method, of class Vehiculo.
     */
    @Test
    public void testGetCapacidad() {
        System.out.println("getCapacidad");
        Vehiculo vehiculo = new Vehiculo ("Fiat","cfc002",5);
        Vehiculo instance = vehiculo;
        int expResult = 5;
        int result = instance.getCapacidad();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPlaca method, of class Vehiculo.
     */
    @Test
    public void testGetPlaca() {
        System.out.println("getPlaca");
        Vehiculo vehiculo = new Vehiculo ("Fiat","cfc002",5);
        Vehiculo instance = vehiculo;
        String expResult = "cfc002";
        String result = instance.getPlaca();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
