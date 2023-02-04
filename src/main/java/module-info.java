module es.miguel.barometro {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires org.jfxtras.styles.jmetro;
    requires json.smart;
    requires org.controlsfx.controls;
    requires java.sql;
    requires javafx.graphics;
    requires java.base;
    
//    requires controlsfx;
    
//    opens org.controlsfx.controls to javafx.graphics;
//    opens javafx.graphics to org.controlsfx.controls;
    opens es.miguel.barometro to javafx.fxml;
    exports es.miguel.barometro;
}
