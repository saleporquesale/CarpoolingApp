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
 * @author Gustavo
 */
public class UsuarioTest {
    
    public UsuarioTest() {
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
     * Test of getNombre method, of class Usuario.
     */
    @Test
    public void testGetNombre() {
        System.out.println("getNombre");
        Usuario user = new Usuario("mario",116370416,"938838","tavo@gmail.com",1,2);
        Usuario instance = user;
        String expResult = "mario";
        String result = instance.getNombre();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getIdentificacion method, of class Usuario.
     */
    @Test
    public void testGetIdentificacion() {
        System.out.println("getIdentificacion");
        Usuario user = new Usuario("mario",116370416,"938838","tavo@gmail.com",1,2);
        Usuario instance = user;
        int expResult = 116370416;
        int result = instance.getIdentificacion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTelefono method, of class Usuario.
     */
    @Test
    public void testGetTelefono() {
        System.out.println("getTelefono");
        Usuario user = new Usuario("mario",116370416,"938838","tavo@gmail.com",1,2);
        Usuario instance = user;
        String expResult = "938838";
        String result = instance.getTelefono();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCorreo method, of class Usuario.
     */
    @Test
    public void testGetCorreo() {
        System.out.println("getCorreo");
        Usuario user = new Usuario("mario",116370416,"938838","tavo@gmail.com",1,2);
        Usuario instance = user;
        String expResult = "tavo@gmail.com";
        String result = instance.getCorreo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTipoDeUsuario method, of class Usuario.
     */
    @Test
    public void testGetTipoDeUsuario() {
        System.out.println("getTipoDeUsuario");
        Usuario user = new Usuario("mario",116370416,"938838","tavo@gmail.com",1,2);
        Usuario instance = user;
        int expResult = 1;
        int result = instance.getTipoDeUsuario();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTipoDeCategoria method, of class Usuario.
     */
    @Test
    public void testGetTipoDeCategoria() {
        System.out.println("getTipoDeCategoria");
        Usuario user = new Usuario("mario",116370416,"938838","tavo@gmail.com",1,2);
        Usuario instance = user;
        int expResult = 2;
        int result = instance.getTipoDeCategoria();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}