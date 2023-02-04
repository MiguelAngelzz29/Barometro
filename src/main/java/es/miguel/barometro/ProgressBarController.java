
package es.miguel.barometro;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class ProgressBarController implements Initializable {


    @FXML
    private Label lbProgressBar;
    private Task task;
    private BarometroController barometroController;
    private double progress;
     private Stage stage;
    @FXML
    private ProgressBar idProgressBar;
    @FXML
    private ProgressBar p2;
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stage = new Stage();
        task = createWorker();

        idProgressBar.progressProperty().bind(task.progressProperty());
        lbProgressBar.textProperty().bind(task.messageProperty());
       
        new Thread(task).start();
       }

    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                final String WORDS[] = {"cargando...", "espera un momento...",
                    "sigue cargando...", "casi está..."};
                // Espera 2 segundos durante 10 veces,
                // esto es, simula hacer algo
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(2000);
                    // Mensaje aleatorio
                    updateMessage(" "
                            + WORDS[(int) (Math.random() * (WORDS.length - 1))]);
                    updateProgress(i + 1, 10);
                    
                }
                updateMessage("Terminado!");
                
                return true;
            }
        };
        
    }
    
    public void cerrarVentana(Stage stage){
         idProgressBar.progressProperty().addListener((observable, oldValue, newValue) -> {
               if (newValue.doubleValue() == 1.0) {
                   stage.close();
                }
        });
    }
   
   public void setMainController(BarometroController barometroController) {
      this.barometroController = barometroController;
   }
   
   public void setStage(Stage stage) {
      this.stage = stage;
        
    }  
    
}