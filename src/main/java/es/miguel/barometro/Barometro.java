package es.miguel.barometro;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class Barometro {

    private static final String localizacion = "src/main/resources/es/miguel/registros/registros.txt";
    private int idBarometro;
    private ArrayList<Medicion> listaParametros;
    

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

    // método para escribir datos 
    public void escribirDatos(String misDatos) {

        File barometroRegistro = new File(localizacion);

        //Comprobamos si el archivo indicado en la ruta existe.
        if (!barometroRegistro.exists()) {

            try {

                File directorio = new File(barometroRegistro.getParent());
                if (!directorio.exists()) {
                    // mkdirs() crea el directorio expecificado en la ruta si no existe  
                    directorio.mkdirs();
                }

                //createNewFile() crea un nuevo archivo vacío si no existe en la ruta expecificada
                barometroRegistro.createNewFile();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try {

            FileWriter escribirRegistros;
            escribirRegistros = new FileWriter(localizacion);

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

    // método para leer registros desde un .txt con datos JSON
    public void leerRegistros() {

        Barometro barometro;
        Gson gson = new Gson();
        String json = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(localizacion));
            String linea;

            while ((linea = br.readLine()) != null) {
                json += linea;

                barometro = gson.fromJson(json, Barometro.class);
            }

            br.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String calcular(Barometro barometro) {
        

        ArrayList<Medicion> listaMediciones = barometro.cargarDatosJsonEnArrayList();

        ArrayList<Medicion> listaUltimas24h = new ArrayList<>(listaMediciones
                .subList(listaMediciones.size() - 24, listaMediciones.size()));
        String prediccion = "advertencia";
        int ultimo = listaUltimas24h.size() - 1;
        double presion1 = listaUltimas24h.get(0).getPresion()
                - (listaUltimas24h.get(ultimo).getPresion());
        double presionUltima = listaUltimas24h.get(ultimo).getPresion();
        double presionPenultima = listaUltimas24h.get(ultimo - 1).getPresion();
        double presionRef = listaUltimas24h.get(ultimo).getPresionRef();
        
        //Para calcular si sube la presión lentamente comparo una presion con la
        // anterior en las últimas 24h y si es superior o igual todas las comprobaciones
        // hago que un contador vaya sumando y si suma 24 (un día) paso el boolean 
        //subeLento a true
        int contador = 0;
        boolean subeLento = false;
        if(barometro.getListaParametros() != null){
        for (int i = 0; i < listaUltimas24h.size(); i++) {

            if (listaUltimas24h.get(i).getPresion() >= listaUltimas24h.get(i).getPresion()) {
                contador++;
            }
        }

        if (contador == 24) {
            subeLento = true;
        }

       if (presionUltima > presionRef && (presion1 - presionUltima) > 6) {
             prediccion = "sol";
        } else if (presionUltima < presionPenultima - 1) {
            prediccion = "tormenta";  
        } else if ((presionUltima > presionPenultima + 1)) {
            prediccion = "nubeSol";
        } else if (subeLento) {
            prediccion = "sol";  
        } else {
            prediccion = "advertencia";  
        }
        }

        /*
        si presión baja 6mm en 24h && presionUltima < presionUltima ref borrasca lejos (sol)
        else desciende 1mm en 1h borrasca profunda 
        else si sube bruscamente 1mm en 1h anticiclón temporal entre borrasca
        else si sube lento sol
        
        
         */
        return prediccion;
    }

    public void calcularPresionConAltura(TextField altura, TextField presionRef, ArrayList<Medicion> lista) {
        // presión a 0m = 1013hPa
        // 1hPa = 1mbar
        // 1hPa = 0.750mmHg
        double alt = Double.parseDouble(altura.getText().toString());
        double pres = 1013 - (alt * 0.09);
        presionRef.setText(pres + "");
    }

   

    //Método para leer los datos guardados en el .txt y llevarlos a un ArrayList
    //para poder trabajar con él
    public ArrayList<Medicion> cargarDatosJsonEnArrayList() {
        ArrayList<Medicion> lista = new ArrayList();
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(localizacion));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray listaMediciones = (JSONArray) jsonObject.get("listadatos");

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
                LocalDate fecha = LocalDate.of(anyo, mes, dia);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
        
    }
    
    public void paraProbarElBranch(){
        System.out.println("hola");
    }

}
