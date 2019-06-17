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
public class AcompañanteTest {
    
    public AcompañanteTest() {
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
     * Test of estaConfirmado method, of class Acompañante.
     */
    @Test
    public void testEstaConfirmado() {
        System.out.println("estaConfirmado");
        Acompañante acompañante = new Acompañante(1);
        Acompañante instance = acompañante;
        boolean expResult = false;
        boolean result = instance.estaConfirmado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of confirmar method, of class Acompañante.
     */
    @Test
    public void testConfirmar() {
        System.out.println("confirmar");
        Acompañante acompañante = new Acompañante(0);
        Acompañante instance = acompañante;
        instance.confirmar();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
