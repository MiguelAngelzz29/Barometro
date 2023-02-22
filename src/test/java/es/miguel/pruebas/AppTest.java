/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.miguel.pruebas;

import es.miguel.barometro.BarometroController;
import javafx.scene.image.Image;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import static org.testfx.matcher.base.NodeMatchers.*;

public class AppTest extends ApplicationTest {

    public AppTest() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        new es.miguel.barometro.App().start(stage);
    }

    @Test
    public void testIconoEsVisible() {

        Image imagen = new Image(getClass().getResourceAsStream("/es/miguel/iconos/tormenta.png"));

        BarometroController bar = Mockito.mock(BarometroController.class);

        Mockito.when(bar.getImageViewIcono()).thenReturn(new ImageView(imagen));

        ImageView icono = bar.getImageViewIcono();

        FxAssert.verifyThat(icono, isVisible());
    }
}
