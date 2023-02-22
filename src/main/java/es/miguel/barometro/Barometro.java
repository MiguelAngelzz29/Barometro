package es.miguel.barometro;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javafx.scene.control.TextField;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class Barometro {

    private static final String localizacion = "/es/miguel/registros/registros.txt";

    private int idBarometro;
    private ArrayList<Medicion> listaParametros;
    private ArrayList<Medicion> listaMediciones = this.cargarDatosJsonEnArrayList();

    ;
    

    public Barometro() {
    }

    public Barometro(int idBarometro, ArrayList<Medicion> listadatos) {
        this.idBarometro = idBarometro;
        this.listaParametros = listadatos;
    }

    public int getIdBarometro() {
        return idBarometro;
    }

    public void setIdBarometro(int idBarometro) {
        this.idBarometro = idBarometro;
    }

    public ArrayList<Medicion> getListaParametros() {
        return listaParametros;
    }

    public void setListaParametros(ArrayList<Medicion> listaParametros) {
        this.listaParametros = listaParametros;
    }

    public ArrayList<Medicion> cargarDatosJsonEnArrayList() {
        ArrayList<Medicion> lista = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            // cargar el archivo como un recurso en el classpath
            InputStream is = getClass().getResourceAsStream(localizacion);

            //   leer el archivo utilizando un BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            Object obj = parser.parse(br);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray listaMediciones = (JSONArray) jsonObject.get("listaParametros");

            for (int i = 0; i < listaMediciones.size(); i++) {
                Medicion medicion = new Medicion();
                JSONObject jsonObject1 = (JSONObject) listaMediciones.get(i);
                String date = jsonObject1.getAsString("fecha");
                String[] arrSplit = date.split(",");
                String year = arrSplit[1];
                String[] arrSplit2 = year.split(":");
                int anyo = Integer.parseInt(arrSplit2[1]);
                String month = arrSplit[0];
                String[] arrSplit3 = month.split(":");
                int mes = Integer.parseInt(arrSplit3[1]);
                String day = arrSplit[2];
                String[] arrSplit4 = day.split(":");
                String[] arrSplit5 = arrSplit4[1].split("}");
                int dia = Integer.parseInt(arrSplit5[0]);
                GregorianCalendar fecha = new GregorianCalendar(anyo, mes, dia);
                medicion.setFecha(fecha);

                int hora = Integer.parseInt(jsonObject1.getAsString("hora"));
                medicion.setHora(hora);
                double temperatura = Double.parseDouble(jsonObject1.getAsString("temperatura"));
                medicion.setTemperatura(temperatura);
                double velocidad = Double.parseDouble(jsonObject1.getAsString("velocidad"));
                medicion.setVelocidad(velocidad);
                double presion = Double.parseDouble(jsonObject1.getAsString("presion"));
                medicion.setPresion(presion);
                double altitud = Double.parseDouble(jsonObject1.getAsString("altitud"));
                medicion.setAltitud(altitud);
                double presionRef = Double.parseDouble(jsonObject1.getAsString("presionRef"));
                medicion.setPresionRef(presionRef);

                lista.add(medicion);

            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public void escribirDatos(String misDatos) {
        try {
            // cargar el archivo como un recurso en el classpath
            File barometroRegistro = new File(getClass().getResource(localizacion).toURI());

            //Comprobamos si el archivo indicado en la ruta existe.
            if (!barometroRegistro.exists()) {
                File directorio = new File(barometroRegistro.getParent());
                if (!directorio.exists()) {
                    // mkdirs() crea el directorio expecificado en la ruta si no existe  
                    directorio.mkdirs();
                }

                //createNewFile() crea un nuevo archivo vacío si no existe en la ruta expecificada
                barometroRegistro.createNewFile();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            // cargar el archivo como un recurso en el classpath
            FileWriter escribirRegistros = new FileWriter(getClass().getResource(localizacion).getFile());

            BufferedWriter bufferWriter = new BufferedWriter(escribirRegistros);
            bufferWriter.write("");
            bufferWriter.write(misDatos.toString());
            bufferWriter.close();

            System.out.println("Datos guardados en " + localizacion);
        } catch (Exception e) {
            System.out.println("Error al guardar datos en " + localizacion);
            System.out.println(e.getMessage());
        }
    }

    //Para calcular si sube la presión lentamente comparo una presion con la
    // anterior en las últimas 24h y si es superior o igual todas las comprobaciones
    // hago que un contador vaya sumando y si suma 24 (un día) paso el boolean 
    //subeLento a true
    public String calcular(double ultimaPresion, double presionRef, double presion,
            double penultimaPresion, boolean subeLento) {

        String prediccion = "advertencia";

        if (ultimaPresion > presionRef && (ultimaPresion - presion) > 6) {
            prediccion = "sol";
        } else if (ultimaPresion < penultimaPresion - 1) {
            prediccion = "tormenta";
        } else if ((ultimaPresion > penultimaPresion + 1)) {
            prediccion = "nubeSol";
        } else if (subeLento) {
            prediccion = "sol";
        } else {
            prediccion = "advertencia";
        }

        return prediccion;
    }

    public double presionReferencia() {

        double presionRef = 0;

        ArrayList<Medicion> listaUltimas24h = null;
        if (listaMediciones.size() >= 24) {
            listaUltimas24h = new ArrayList<>(listaMediciones
                    .subList(listaMediciones.size() - 24, listaMediciones.size()));

            int ultimo = listaUltimas24h.size() - 1;
            presionRef = listaUltimas24h.get(ultimo).getPresionRef();
        }
        return presionRef;
    }

    public double ultimaPresion() {

        double presionUltima = 0;

        ArrayList<Medicion> listaUltimas24h = null;
        if (listaMediciones.size() >= 24) {
            listaUltimas24h = new ArrayList<>(listaMediciones
                    .subList(listaMediciones.size() - 24, listaMediciones.size()));

            int ultimo = listaUltimas24h.size() - 1;

            presionUltima = listaUltimas24h.get(ultimo).getPresion();

        }

        return presionUltima;
    }

//    public double ultimaPresion() {
//        
//        ArrayList<Medicion> listaMediciones = this.cargarDatosJsonEnArrayList();
//        double presionUltima = 0;
//
//        ArrayList<Medicion> listaUltimas24h = null;
//        if (listaMediciones.size() >= 24) {
//            listaUltimas24h = new ArrayList<>(listaMediciones
//                    .subList(listaMediciones.size() - 24, listaMediciones.size()));
//
//            int ultimo = listaUltimas24h.size() - 1;
//
//            presionUltima = listaUltimas24h.get(ultimo).getPresion();
//
//        }
//
//        return presionUltima;
//    }
    public double penultimaPresion() {

        double presion1 = 0;
        double presionPenultima = 0;

        ArrayList<Medicion> listaUltimas24h = null;
        if (listaMediciones.size() >= 24) {
            listaUltimas24h = new ArrayList<>(listaMediciones
                    .subList(listaMediciones.size() - 24, listaMediciones.size()));

            int ultimo = listaUltimas24h.size() - 1;

            presionPenultima = listaUltimas24h.get(ultimo - 1).getPresion();

        }
        return presionPenultima;

    }

    public boolean caso1() {
        if (ultimaPresionMayorPresionRef() && presionMenosUltimaPresionMayor6()) {
            return true;
        }
        return false;
    }

    public boolean ultimaPresionMayorPresionRef() {
        if (ultimaPresion() > presionReferencia()) {
            return true;
        }
        return false;
    }

    public boolean presionMenosUltimaPresionMayor6() {
        if ((presion() - ultimaPresion()) > 6) {
            return true;
        }
        return false;
    }

    public boolean caso2() {
        if (ultimaPresion() < penultimaPresion() - 1) {
            return true;
        }
        return false;
    }

    public boolean caso3() {
        if (ultimaPresion() > penultimaPresion() + 1) {
            return true;
        }
        return false;
    }

    public boolean caso4() {
        if (subeLento()) {
            return true;
        }
        return false;
    }

    public boolean subeLento() {

        boolean subeLento = false;
        int contador = 0;

        ArrayList<Medicion> listaUltimas24h = null;
        if (listaMediciones.size() >= 24) {
            listaUltimas24h = new ArrayList<>(listaMediciones
                    .subList(listaMediciones.size() - 24, listaMediciones.size()));
        }

        if (this.getListaParametros() != null) {
            for (int i = 0; i < listaUltimas24h.size(); i++) {

                if (listaUltimas24h.get(i).getPresion() >= listaUltimas24h.get(i).getPresion()) {
                    contador++;
                }
            }
        }

        if (contador == 24) {
            subeLento = true;
        }

        return subeLento;
    }

    public double presion() {

        double presion = 0;

        ArrayList<Medicion> listaUltimas24h = null;
        if (listaMediciones.size() >= 24) {
            listaUltimas24h = new ArrayList<>(listaMediciones
                    .subList(listaMediciones.size() - 24, listaMediciones.size()));

            int ultimo = listaUltimas24h.size() - 1;
            presion = listaUltimas24h.get(0).getPresion()
                    - (listaUltimas24h.get(ultimo).getPresion());

        }

        return presion;

    }

    public void calcularPresionConAltura(TextField altura, TextField presionRef, ArrayList<Medicion> lista) {
        // presión a 0m = 1013hPa
        // 1hPa = 1mbar
        // 1hPa = 0.750mmHg
        double alt = Double.parseDouble(altura.getText().toString());
        double pres = 1013 - (alt * 0.09);
        presionRef.setText(pres + "");
    }

    public ArrayList<Medicion> getListaMediciones() {
        return listaMediciones;
    }

    public void setListaMediciones(ArrayList<Medicion> listaMediciones) {
        this.listaMediciones = listaMediciones;
    }

}
