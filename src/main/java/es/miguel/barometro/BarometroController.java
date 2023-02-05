package es.miguel.barometro;

import com.google.gson.Gson;
import es.miguel.barometro.Barometro;
import es.miguel.barometro.Medicion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import java.util.Date;
import java.util.GregorianCalendar;
import javafx.scene.control.ListCell;

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
    @FXML
    private TextField txtTemperatura;
    private ResourceBundle bundle;
    @FXML
    private Label labelRegistro;
    @FXML
    private Label labelPresionRef;
    @FXML
    private Label labelPresion;
    @FXML
    private Label labeltemperatura;
    @FXML
    private Label labelFecha;
    @FXML
    private Label labelHora;
    @FXML
    private Label labelCalibracion;
    @FXML
    private Label labelAltitud;
    @FXML
    private Label labelRegistros;
    @FXML
    private Label labelSeleccionarFecha;
    @FXML
    private Label labelVelocidad;
    private ValidationSupport validar;
    private ValidationSupport validar2;
    private ValidationSupport validar3;
    @FXML
    private Button btnIngles;
    @FXML
    private Button btnFrances;
    @FXML
    private Button btnEspanol;
    private Properties config;
    private Locale locale;
    private ResourceBundle bundles;
    private Task task;
    private String idioma;
    private String pais;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cargarIdioma();
        locale = new Locale(idioma, pais);
        bundle = ResourceBundle.getBundle("es.miguel.idiomas.idioma", locale);
        cargarImagenesBotones();
        internalizacion(bundle);
        validarTextFields();

        SpinnerValueFactory<String> valueFactory
                = new SpinnerValueFactory.ListSpinnerValueFactory<String>(horas);
        valueFactory.setValue("12:00");
        spinnerHora.setValueFactory(valueFactory);
        txtAltitud.setText("0");
        barometro.cargarDatosJsonEnArrayList();
        barometro.setListaParametros(listaDatos);
        txtPresionRef.setEditable(false);
        mostrarImagen(barometro.calcular(barometro));
        barometro.calcularPresionConAltura(txtAltitud, txtPresionRef, listaDatos);

    }

    @FXML
    private void btnGuardarClick(MouseEvent event) {

        Medicion datos = new Medicion();

        double temp = Double.parseDouble(txtTemperatura.getText());
        datos.setTemperatura(temp);
        double pres = Double.parseDouble(txtPresion.getText());
        datos.setPresion(pres);
        double vel = Double.parseDouble(txtVelocidad.getText());
        datos.setVelocidad(vel);
        GregorianCalendar fech = new GregorianCalendar();
        String fecha1 = txtFecha.getValue().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(fecha1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fech.setTime(date);
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
        System.out.println(listaDatos);

        barometro.calcular(barometro);
        barometro.setIdBarometro(1);
        barometro.setListaParametros(listaDatos);
        barometro.escribirDatos(gson.toJson(barometro));
        limpiar();
    }

    @FXML
    private void btnActualizarClick(MouseEvent event) {
        cargarBarraProgreso();
        //barometro.leerRegistros();
        barometro.calcularPresionConAltura(txtAltitud, txtPresionRef, listaDatos);
    }

    @FXML
    private void btnSalirClick(MouseEvent event) {

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
        ListView<Medicion> listaMedicion;
        ArrayList<Medicion> lista = barometro.cargarDatosJsonEnArrayList();
        ArrayList<Medicion> listaProvisional = new ArrayList<>();
        GregorianCalendar fecha;
        for (int i = 0; i < lista.size(); i++) {
            fecha = new GregorianCalendar();
            fecha = lista.get(i).getFecha();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String fech = dateFormat.format(fecha.getTime());

            if (fech.equals(txtFechaSelect.getValue().toString())) {
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
        listView.setCellFactory(lv -> new ListCell<Medicion>() {
            @Override
            protected void updateItem(Medicion medicion, boolean empty) {
                super.updateItem(medicion, empty);

                if (empty || medicion == null) {
                    setText(null);
                } else {
                    setText("Hora: " + medicion.getHora() + " Temperatura "
                            + medicion.getTemperatura() + " Presión: " + medicion.getPresion()
                            + " Velocidad Viento: " + medicion.getVelocidad());
                }
            }
        });
        listView.setItems(listaObservable);
        barometro.calcular(barometro);

    }

    private void mostrarImagen(String prediccion) {

        switch (prediccion) {

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

    private void internalizacion(ResourceBundle rb) {
        //Asignamos el valor de cada etiqueta para la internalización.
        bundle = rb;

        labeltemperatura.setText(bundle.getString("labelTemperatura"));
        labelRegistro.setText(bundle.getString("labelRegistro"));
        labelPresion.setText(bundle.getString("labelPresion"));
        labelVelocidad.setText(bundle.getString("labelVelocidad"));
        labelFecha.setText(bundle.getString("labelFecha"));
        labelHora.setText(bundle.getString("labelHora"));
        labelCalibracion.setText(bundle.getString("labelCalibracion"));
        labelPresionRef.setText(bundle.getString("labelPresionRef"));
        labelAltitud.setText(bundle.getString("labelAltitud"));
        labelRegistros.setText(bundle.getString("labelRegistros"));
        labelSeleccionarFecha.setText(bundle.getString("labelSeleccionarFecha"));
        btnGuardar.setText(bundle.getString("btnGuardar"));
        btnActualizar.setText(bundle.getString("btnActualizar"));
        btnBuscar.setText(bundle.getString("btnSeleccionarFecha"));

    }

    private void validarTextFields() {
        validar = new ValidationSupport();
        validar2 = new ValidationSupport();
        validar3 = new ValidationSupport();
        validar.registerValidator(txtFecha, Validator
                .createEmptyValidator("Este campo es obligatorio"));

        Validator<String> numberValidator = new Validator<String>() {
            @Override
            public ValidationResult apply(Control control, String value) {

                boolean condition;

                if (value != null) {
                    condition = !value.matches("^-?\\d+$");
                } else {
                    condition = value == null;
                }

                return ValidationResult.fromMessageIf(control, "no es un número",
                        Severity.ERROR, condition);
            }
        };
        validar.registerValidator(txtPresion, true, numberValidator);
        validar.registerValidator(txtTemperatura, true, numberValidator);
        validar.registerValidator(txtVelocidad, true, numberValidator);

        btnGuardar.disableProperty().bind(validar.invalidProperty());

        validar2.registerValidator(txtAltitud, true, numberValidator);
        btnActualizar.disableProperty().bind(validar2.invalidProperty());

        validar3.registerValidator(txtFechaSelect, Validator
                .createEmptyValidator("Este campo es obligatorio"));
        btnBuscar.disableProperty().bind(validar3.invalidProperty());
    }

    @FXML
    private void btnEspanolClick(MouseEvent event) {
        locale = new Locale("es", "ES");
        ResourceBundle idioma = ResourceBundle.getBundle("es.miguel.idiomas.idioma", locale);
        internalizacion(idioma);
        guardarIdioma();
    }

    @FXML
    private void btnInglesClick(MouseEvent event) {
        locale = new Locale("en", "UK");
        ResourceBundle idioma = ResourceBundle.getBundle("es.miguel.idiomas.idioma", locale);
        internalizacion(idioma);
        guardarIdioma();

    }

    @FXML
    private void btnFrancesClick(MouseEvent event) {
        locale = new Locale("fr", "FR");
        ResourceBundle idioma = ResourceBundle.getBundle("es.miguel.idiomas.idioma", locale);
        internalizacion(idioma);
        guardarIdioma();

    }

    private void guardarIdioma() {
        String localizacion = "src/main/resources/es/miguel/registros/idioma.txt";
        try {

            FileWriter escribirRegistros;
            escribirRegistros = new FileWriter(localizacion);

            BufferedWriter bufferWriter = new BufferedWriter(escribirRegistros);
            bufferWriter.write("");
            bufferWriter.write(locale.toString());
            bufferWriter.close();

        } catch (Exception e) {
            System.out.println("Error al guardar datos en " + localizacion);
            System.out.println(e.getMessage());
        }
    }

    private void cargarIdioma() {
        String localizacion = "src/main/resources/es/miguel/registros/idioma.txt";
        String cadena = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(localizacion));
            String linea;

            while ((linea = br.readLine()) != null) {
                cadena += linea;
            }

            String[] cadena2 = cadena.split("_");
            idioma = cadena2[0];
            pais = cadena2[1];

            br.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void cargarBarraProgreso() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("progressBar.fxml"));
            stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            ProgressBarController progresBar = loader.getController();
            progresBar.setMainController(this);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            progresBar.cerrarVentana(stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarImagenesBotones() {
        btnEspanol.setBorder(Border.EMPTY);
        btnIngles.setBorder(Border.EMPTY);
        btnFrances.setBorder(Border.EMPTY);

        URL linkBanderaEs = getClass().getResource("/es/miguel/iconos/banderaEsp.png");
        Image banderaEs = new Image(linkBanderaEs.toString(), 32, 32, false, true);
        btnEspanol.setGraphic(new ImageView(banderaEs));
        URL linkBanderaIng = getClass().getResource("/es/miguel/iconos/banderaIng.png");
        Image banderaIng = new Image(linkBanderaIng.toString(), 32, 32, false, true);
        btnIngles.setGraphic(new ImageView(banderaIng));
        URL linkBanderaFr = getClass().getResource("/es/miguel/iconos/banderaFran.png");
        Image banderaFr = new Image(linkBanderaFr.toString(), 32, 32, false, true);
        btnFrances.setGraphic(new ImageView(banderaFr));
    }

}
