module com.ve.weather {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires log4j;

    opens com.ve.weather to javafx.fxml;
    exports com.ve.weather;
}