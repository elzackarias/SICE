module com.nuzack.sice {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.prefs;

    opens com.nuzack.sice to javafx.fxml, com.google.gson;
    opens com.nuzack.sice.models to com.google.gson, javafx.base;
    exports com.nuzack.sice;
    exports com.nuzack.sice.controllers;
    opens com.nuzack.sice.controllers to com.google.gson, javafx.fxml;
    opens com.nuzack.sice.utils to com.google.gson, javafx.fxml;

}