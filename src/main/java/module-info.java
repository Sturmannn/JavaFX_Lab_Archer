open module org.archer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.google.gson;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;
    requires java.sql;

//    opens org.archer to javafx.fxml, com.google.gson;
//    opens org.archer.game to com.google.gson;
//    opens org.archer.elements to com.google.gson;
    exports org.archer;
}