/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.miguel.pruebas;

import es.miguel.barometro.Barometro;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

/**
 *
 * @author migue
 */
public class BarometroTest {

    public BarometroTest() {
    }


    //camino 1
    @Test
    public void testCalcular_1() {
        System.out.println("calcular_1");
          Barometro barometro = new Barometro();
        String expResult = "advertencia";
        
       
        double presion = barometro.getListaMediciones().get(2).getPresion();
        double ultimaPresion = barometro.getListaMediciones().get(2).getPresionRef();
        double penultimaPresion = barometro.getListaMediciones().get(2).getPresionRef();
        double presionReferencia= barometro.getListaMediciones().get(2).getPresion();
        boolean subeLento= false;
        
        String result = barometro.calcular(ultimaPresion,presionReferencia,presion,
                penultimaPresion,subeLento);
        
         assertEquals(expResult, result);
        
    }

  //  Camino 2
    @Test
    public void testCalcular_2() {
          System.out.println("calcular_2");
         Barometro barometro = new Barometro();
        String expResult = "nubeSol";
     
        double presion = barometro.getListaMediciones().get(0).getPresion();
        double ultimaPresion = barometro.getListaMediciones().get(0).getPresion();
        double penultimaPresion = barometro.getListaMediciones().get(1).getPresion();
        double presionReferencia= barometro.getListaMediciones().get(0).getPresionRef();
        boolean subeLento= false;
     
        String result = barometro.calcular(ultimaPresion,presionReferencia,presion,
                penultimaPresion,subeLento);
        
         assertEquals(expResult, result);
        }
    

  //  Camino 3
    @Test
    public void testCalcular_3() {
          System.out.println("calcular_3");
     
         Barometro barometro = new Barometro();
        String expResult = "sol";
      
        double presion = barometro.getListaMediciones().get(1).getPresion();
        double ultimaPresion = barometro.getListaMediciones().get(0).getPresion();
        double penultimaPresion = barometro.getListaMediciones().get(1).getPresion();
        double presionReferencia= barometro.getListaMediciones().get(0).getPresionRef();
        boolean subeLento= false;
                   
        String result = barometro.calcular(ultimaPresion,presionReferencia,presion,
                penultimaPresion,subeLento);
        
         assertEquals(expResult, result);
        }
    //  Camino 4
    @Test
    public void testCalcular_4() {
          System.out.println("calcular_4");
         Barometro barometro = new Barometro();
        String expResult = "tormenta";
      
        double presion = barometro.getListaMediciones().get(1).getPresion();
        double ultimaPresion = barometro.getListaMediciones().get(1).getPresion();
        double penultimaPresion = barometro.getListaMediciones().get(0).getPresion();
        double presionReferencia= barometro.getListaMediciones().get(0).getPresionRef();
        boolean subeLento= false;
   
        String result = barometro.calcular(ultimaPresion,presionReferencia,presion,
                penultimaPresion,subeLento);
       
                 assertEquals(expResult, result);
        }
    
    //Camino 5
     @Test
    public void testCalcular_5() {
          System.out.println("calcular_5");
        Barometro barometro = new Barometro();
        String expResult = "sol";
      
        double presion = barometro.getListaMediciones().get(0).getPresion();
        double ultimaPresion = barometro.getListaMediciones().get(0).getPresion();
        double penultimaPresion = barometro.getListaMediciones().get(0).getPresion();
        double presionReferencia= barometro.getListaMediciones().get(0).getPresionRef();
        boolean subeLento= true;
        
        String result = barometro.calcular(ultimaPresion,presionReferencia,presion,
                penultimaPresion,subeLento);
        
         assertEquals(expResult, result);
        }
 
}

