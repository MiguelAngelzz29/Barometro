/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.miguel.pruebas;

import es.miguel.barometro.Barometro;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;

public class CucumberTest {

    private final Barometro bar = Mockito.mock(Barometro.class);

    @Cuando("no hay suficientes mediciones")
    public void no_hay_suficientes_mediciones() {
        when(bar.calcular(1020, 1020, 1010, 1013, false)).thenReturn("advertencia");
    }

    @Entonces("advertencia")
    public void advertencia() {
        Barometro bar = new Barometro();

        double presion1 = bar.getListaMediciones().get(2).getPresion();
        double ultimaPresion1 = bar.getListaMediciones().get(2).getPresionRef();
        double penultimaPresion1 = bar.getListaMediciones().get(2).getPresionRef();
        double presionReferencia1 = bar.getListaMediciones().get(2).getPresion();
        boolean subeLento1 = false;

        String resultado = bar.calcular(presion1, ultimaPresion1, penultimaPresion1,
                presionReferencia1, subeLento1);

        String resultadoEsperado = "advertencia";
        assertEquals(resultadoEsperado, resultado);

    }

    @Cuando("ultima presion es mayor que presion Referencia")
    public void ultima_presion_es_mayor_que_presion_Referencia() {

        when(bar.calcular(1010, 1020, 1010, 1013, false)).thenReturn("sol");
    }

    @Entonces("sol")
    public void sol() {
        Barometro barometro = new Barometro();
        String expResult = "sol";

        double presion = barometro.getListaMediciones().get(1).getPresion();
        double ultimaPresion = barometro.getListaMediciones().get(0).getPresion();
        double penultimaPresion = barometro.getListaMediciones().get(1).getPresion();
        double presionReferencia = barometro.getListaMediciones().get(0).getPresionRef();
        boolean subeLento = false;

        String result = barometro.calcular(ultimaPresion, presionReferencia, presion,
                penultimaPresion, subeLento);

        assertEquals(expResult, result);

    }

    @Cuando("ultima presion menor que penultima presion menos uno")
    public void ultima_presion_menor_que_penultima_presion_menos_uno() {

        when(bar.calcular(1010, 1020, 1010, 1013, false)).thenReturn("sol");
    }

    @Entonces("tormenta")
    public void tormenta() {
        Barometro barometro = new Barometro();
        String expResult = "tormenta";

        double presion = barometro.getListaMediciones().get(1).getPresion();
        double ultimaPresion = barometro.getListaMediciones().get(1).getPresion();
        double penultimaPresion = barometro.getListaMediciones().get(0).getPresion();
        double presionReferencia = barometro.getListaMediciones().get(0).getPresionRef();
        boolean subeLento = false;

        String result = barometro.calcular(ultimaPresion, presionReferencia, presion,
                penultimaPresion, subeLento);

        assertEquals(expResult, result);

    }

    @Cuando("ultima presion es mayor que penultima presion mas uno")
    public void ultima_presion_es_mayor_que_penultima_presion_mas_uno() {

        when(bar.calcular(1010, 1020, 1010, 1013, false)).thenReturn("sol");
    }

    @Entonces("nubes")
    public void nubes() {

        Barometro barometro = new Barometro();
        String expResult = "nubeSol";

        double presion = barometro.getListaMediciones().get(0).getPresion();
        double ultimaPresion = barometro.getListaMediciones().get(0).getPresion();
        double penultimaPresion = barometro.getListaMediciones().get(1).getPresion();
        double presionReferencia = barometro.getListaMediciones().get(0).getPresionRef();
        boolean subeLento = false;

        String result = barometro.calcular(ultimaPresion, presionReferencia, presion,
                penultimaPresion, subeLento);

        assertEquals(expResult, result);

    }

    @Cuando("sube la presion en 24h")
    public void sube_la_presion_en_24h() {

        when(bar.calcular(1020, 1020, 1020, 1013, true)).thenReturn("sol");
    }

    @Entonces("sol 24h")
    public void sol24h() {

        Barometro barometro = new Barometro();
        String expResult = "sol";

        double presion = barometro.getListaMediciones().get(0).getPresion();
        double ultimaPresion = barometro.getListaMediciones().get(0).getPresion();
        double penultimaPresion = barometro.getListaMediciones().get(0).getPresion();
        double presionReferencia = barometro.getListaMediciones().get(0).getPresionRef();
        boolean subeLento = true;

        String result = barometro.calcular(ultimaPresion, presionReferencia, presion,
                penultimaPresion, subeLento);

        assertEquals(expResult, result);

    }

}


