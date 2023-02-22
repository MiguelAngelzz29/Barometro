/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.miguel.barometro;

import java.util.GregorianCalendar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author migue
 */
public class MedicionTest {
    
    public MedicionTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getTemperatura method, of class Medicion.
     */
    @Test
    public void testGetTemperatura() {
        System.out.println("getTemperatura");
        Medicion instance = new Medicion();
        double expResult = 0.0;
        double result = instance.getTemperatura();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTemperatura method, of class Medicion.
     */
    @Test
    public void testSetTemperatura() {
        System.out.println("setTemperatura");
        double temperatura = 0.0;
        Medicion instance = new Medicion();
        instance.setTemperatura(temperatura);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPresion method, of class Medicion.
     */
    @Test
    public void testGetPresion() {
        System.out.println("getPresion");
        Medicion instance = new Medicion();
        double expResult = 0.0;
        double result = instance.getPresion();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPresion method, of class Medicion.
     */
    @Test
    public void testSetPresion() {
        System.out.println("setPresion");
        double presion = 0.0;
        Medicion instance = new Medicion();
        instance.setPresion(presion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVelocidad method, of class Medicion.
     */
    @Test
    public void testGetVelocidad() {
        System.out.println("getVelocidad");
        Medicion instance = new Medicion();
        double expResult = 0.0;
        double result = instance.getVelocidad();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVelocidad method, of class Medicion.
     */
    @Test
    public void testSetVelocidad() {
        System.out.println("setVelocidad");
        double velocidad = 0.0;
        Medicion instance = new Medicion();
        instance.setVelocidad(velocidad);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFecha method, of class Medicion.
     */
    @Test
    public void testGetFecha() {
        System.out.println("getFecha");
        Medicion instance = new Medicion();
        GregorianCalendar expResult = null;
        GregorianCalendar result = instance.getFecha();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFecha method, of class Medicion.
     */
    @Test
    public void testSetFecha() {
        System.out.println("setFecha");
        GregorianCalendar fecha = null;
        Medicion instance = new Medicion();
        instance.setFecha(fecha);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAltitud method, of class Medicion.
     */
    @Test
    public void testGetAltitud() {
        System.out.println("getAltitud");
        Medicion instance = new Medicion();
        double expResult = 0.0;
        double result = instance.getAltitud();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAltitud method, of class Medicion.
     */
    @Test
    public void testSetAltitud() {
        System.out.println("setAltitud");
        double altitud = 0.0;
        Medicion instance = new Medicion();
        instance.setAltitud(altitud);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHora method, of class Medicion.
     */
    @Test
    public void testGetHora() {
        System.out.println("getHora");
        Medicion instance = new Medicion();
        int expResult = 0;
        int result = instance.getHora();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHora method, of class Medicion.
     */
    @Test
    public void testSetHora() {
        System.out.println("setHora");
        int hora = 0;
        Medicion instance = new Medicion();
        instance.setHora(hora);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPresionRef method, of class Medicion.
     */
    @Test
    public void testGetPresionRef() {
        System.out.println("getPresionRef");
        Medicion instance = new Medicion();
        double expResult = 0.0;
        double result = instance.getPresionRef();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPresionRef method, of class Medicion.
     */
    @Test
    public void testSetPresionRef() {
        System.out.println("setPresionRef");
        double presionRef = 0.0;
        Medicion instance = new Medicion();
        instance.setPresionRef(presionRef);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Medicion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Medicion instance = new Medicion();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
