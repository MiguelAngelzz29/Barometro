/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.miguel.pruebas;

import es.miguel.barometro.BarometroController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class BarometroControllerTest /*extends ApplicationTest*/ {

    private ImageView imageViewIcono;

  //  @Override
    public void start(Stage stage) throws Exception {
        BarometroController controller = new BarometroController();
        
        new es.miguel.barometro.App().start(stage);
        // carga la vista desde FXML
      //  FXMLLoader loader = new FXMLLoader(getClass().getResource("Barometro.fxml"));
//        Parent root = loader.load();
//        controller = loader.getController();
      //  imageViewIcono = controller;

        // establece el controlador de la vista cargada
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

    @Test
    public void testImageViewIcono() {
        // verifique que la imagen cargada en ImageView sea el archivo "icono.png"
        String archivoEsperado = "icono.png";
        Image imagen = imageViewIcono.getImage();
        String archivoObtenido = imagen.getUrl().substring(imagen.getUrl().lastIndexOf("/") + 1);
        assertEquals(archivoEsperado, archivoObtenido);
    }
}
