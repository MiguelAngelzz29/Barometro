/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.miguel.pruebas;

import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import static org.testfx.matcher.base.NodeMatchers.*;

public class AppTest extends ApplicationTest  {
    
    public AppTest() {
    }
    
   @Override
    public void start(Stage stage) throws Exception {
        new es.miguel.barometro.App().start(stage);
    }

    @Test
    public void testIconoEsVisible() {
        // Obtener la imagen del icono
        ImageView icono = lookup("#imageViewIcono").query();
        // Comprobar que la imagen es visible
        FxAssert.verifyThat(icono, isVisible());
    }
        
}

