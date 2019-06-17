package com.example.tab1;

import org.junit.Test;

import static org.junit.Assert.*;

public class Frag2Test {

    @Test
    public void onCreateView() {
    }

    @Test
    public void onActivityResult() {
    }
    @Test
    public void Test_comprobarEmail(){
        Frag2 Test =new Frag2();
        Test.correo="tavo.23";
        assertTrue(Frag2.comprobarEmail);
    }

}