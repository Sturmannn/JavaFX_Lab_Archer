module org.archer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens org.archer to javafx.fxml;
    exports org.archer;
}