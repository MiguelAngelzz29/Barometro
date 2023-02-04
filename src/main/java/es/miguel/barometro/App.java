package es.miguel.barometro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class App extends Application {

    private ResourceBundle resourceBundle;
    private static Scene scene;
    private Locale locale;
    

    @Override
    public void start(Stage stage) throws IOException {
        
        locale = new Locale("es","ES");
        loadLanguage(locale);
        scene = new Scene(loadFXML("barometro"), 770, 600);
        
        //Aquí utlizo el Theme JMetro
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stage.setScene(scene);
        stage.show();

    }

    private void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    //Método para cargar el lenguaje
    public void loadLanguage(Locale locale) {
       
        resourceBundle = ResourceBundle.getBundle("es.miguel.idiomas.idioma",locale);
       
       
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"),resourceBundle);
        return fxmlLoader.load();

    }

    public static void main(String[] args) {
        launch();

    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    

}
