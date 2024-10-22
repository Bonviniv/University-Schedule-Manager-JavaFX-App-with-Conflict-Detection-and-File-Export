module com.example.projetoes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;
    requires opencsv;
    requires gs.core;
    requires javafx.swing;
    requires gs.ui.javafx;

    opens com.example.projetoes to javafx.fxml;
    exports com.example.projetoes;
}