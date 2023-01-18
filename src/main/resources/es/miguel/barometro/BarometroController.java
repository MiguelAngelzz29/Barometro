package es.miguel.barometro;

import com.google.gson.Gson;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BarometroController implements Initializable {

    private static Gson gson = new Gson();
    private static Barometro barometro = new Barometro();
    private static Medicion medicion = new Medicion();

    private static ArrayList<Medicion> listaDatos = new ArrayList<>();
    private static ObservableList<String> horas = FXCollections.observableArrayList("0:00", "1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00",
            "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00");

    @FXML
    private TextField txtPresion;
    @FXML
    private TextField txtTemperatura;
    @FXML
    private DatePicker txtFecha;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtAltitud;
    private TextField txtPresionCal;
    @FXML
    private TextField txtVelocidad;
    @FXML
    private Button btnActualizar;
    @FXML
    private ListView<Medicion> listView;
    @FXML
    private DatePicker txtFechaSelect;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnBuscar;
    @FXML
    private ImageView imageViewIcono;
    @FXML
    private TextField txtPresionRef;
    @FXML
    private Spinner<String> spinnerHora;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        SpinnerValueFactory<String> valueFactory
                = new SpinnerValueFactory.ListSpinnerValueFactory<String>(horas);
        valueFactory.setValue("12:00");
        spinnerHora.setValueFactory(valueFactory);
        txtAltitud.setText("0");
        barometro.cargarDatosJsonEnArrayList();
        barometro.setListaParametros(listaDatos);
        System.out.println("imagen " + barometro.calcular(barometro));
        mostrarImagen(barometro.calcular(barometro));
        barometro.calcularPresionConAltura(txtAltitud, txtPresionRef, listaDatos);

    }

    @FXML
    private void btnGuardarClick(MouseEvent event) {

        Medicion datos = new Medicion();

        // Antes de guarda compruebo que no quedan campos vacíos
        //si hay alguno vacío salta una alerta
        if (txtTemperatura.getText().isEmpty() || txtPresion.getText().isEmpty()
                || txtVelocidad.getText().isEmpty() || txtAltitud.getText().isEmpty()
                || txtPresionRef.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ADVERTENCIA");
            alert.setHeaderText("Revisar todos los campos");
            alert.setContentText("Hay algún campo vacío");
            alert.showAndWait();

            // si no hay campos vacíos recojo los datos y los voy parseando
            // para conseguir el tipo de dato que necesito
        } else {
            double temp = Double.parseDouble(txtTemperatura.getText());
            datos.setTemperatura(temp);
            double pres = Double.parseDouble(txtPresion.getText());
            datos.setPresion(pres);
            double vel = Double.parseDouble(txtVelocidad.getText());
            datos.setVelocidad(vel);
            LocalDate fech = txtFecha.getValue();
            datos.setFecha(fech);
            String[] hora1 = spinnerHora.getValue().toString().split(":");
            int hora = Integer.parseInt(hora1[0]);
            datos.setHora(hora);
            double alt = Double.parseDouble(txtAltitud.getText());
            datos.setAltitud(alt);
            double presionRef = Double.parseDouble(txtPresionRef.getText());
            datos.setPresionRef(presionRef);

            //Aquí compruebo que no hay registros repetidos por fecha y hora
            boolean repetido = false;
            for (int i = 0; i < listaDatos.size(); i++) {

                if (listaDatos.get(i).getFecha().equals(fech)
                        && listaDatos.get(i).getHora() == hora) {
                    repetido = true;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ADVERTENCIA");
                    alert.setHeaderText("Ya existe una medición en esta fecha y hora");
                    alert.showAndWait();
                }
            }

            if (!repetido) {
                listaDatos.add(datos);
            }

            barometro.calcular(barometro);
            barometro.setIdBarometro(1);
            barometro.setListaParametros(listaDatos);
            barometro.escribirDatos(gson.toJson(barometro));
            limpiar();
        }
    }

    @FXML
    private void btnActualizarClick(MouseEvent event) {
        barometro.leerRegistros();
        barometro.calcularPresionConAltura(txtAltitud, txtPresionRef, listaDatos);
    }

    @FXML
    private void btnSalirClick(MouseEvent event) {
        barometro.setIdBarometro(1);
        barometro.setListaParametros(listaDatos);
        barometro.escribirDatos(gson.toJson(barometro));
        System.exit(0);

    }

    //Método para limpiar los campos una vez guardada la medición
    private void limpiar() {

        txtTemperatura.setText("");
        txtPresion.setText("");
        txtVelocidad.setText("");
        txtFecha.setId("");

    }

    @FXML
    private void ListViewMostrarMediciones(MouseEvent event) {
            ListView<Medicion> listaMedicion ;
            ArrayList<Medicion> lista = barometro.cargarDatosJsonEnArrayList();
            ArrayList<Medicion> listaProvisional = new ArrayList<>();
            
        for (int i = 0; i < lista.size(); i++) {

            if (lista.get(i).getFecha().equals(txtFechaSelect.getValue())) {
                listaProvisional.add(lista.get(i));
            }
        }

        if (listaProvisional.isEmpty()) {
           Image advertencia = new Image(getClass().getResourceAsStream("/es/miguel/iconos/advertencia.png"));
                    imageViewIcono.setImage(advertencia);
            listView.getItems().clear();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ADVERTENCIA");
            alert.setHeaderText("No hay mediciones para esta fecha");
            alert.setContentText("");
            alert.showAndWait();

        }

        ObservableList<Medicion> listaObservable = FXCollections.observableArrayList(listaProvisional);
         listView.setItems(listaObservable);
         barometro.calcular( barometro);
        System.out.println("imagen2 " +barometro.calcular( barometro) );
    }
    
    private void mostrarImagen(String prediccion){
        
          switch(prediccion){
              
                case "sol":
                  Image imageSol = new Image(getClass().getResourceAsStream("/es/miguel/iconos/sol.png"));
                  imageViewIcono.setImage(imageSol);
                    break;
                case "nubeSol":
                    Image nubeSol = new Image(getClass().getResourceAsStream("/es/miguel/iconos/nubeSol.png"));
                    imageViewIcono.setImage(nubeSol);
                    break;
                case "tormenta":
                    Image tormenta = new Image(getClass().getResourceAsStream("/es/miguel/iconos/tormenta.png"));
                    imageViewIcono.setImage(tormenta);
                    break;
                case "advertencia":
                    Image advertencia = new Image(getClass().getResourceAsStream("/es/miguel/iconos/advertencia.png"));
                    imageViewIcono.setImage(advertencia);
                    break;
                default:
                    advertencia = new Image(getClass().getResourceAsStream("/es/miguel/iconos/advertencia.png"));
                    imageViewIcono.setImage(advertencia);
          }
    }
}
