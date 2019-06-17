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
public class MemoriaTest {
    
    public MemoriaTest() {
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
     * Test of getInstancia method, of class Memoria.
     */
    @Test
    public void testGetInstancia() {
        System.out.println("getInstancia");
        Memoria expResult = null;
        Memoria result = Memoria.getInstancia();
        assertNotSame(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addViaje method, of class Memoria.
     */
    @Test
    public void testAddViaje_int() {
        System.out.println("addViaje");
        int pCapacidad = 2;
        Memoria memoria=new Memoria();
        Memoria instance = memoria;
        instance.addViaje(pCapacidad);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addViaje method, of class Memoria.
     */
    @Test
    public void testAddViaje_Viaje() {
        System.out.println("addViaje");
        Viaje pViaje = null;
        Memoria memoria=new Memoria();
        Memoria instance = memoria;
        instance.addViaje(pViaje);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
