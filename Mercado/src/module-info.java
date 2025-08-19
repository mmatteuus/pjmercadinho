module Mercado {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;

    // JavaFX precisa instanciar sua Application e controllers
    exports application;
    opens application to javafx.graphics;

    exports controller;
    opens controller to javafx.fxml, javafx.graphics;

    exports dao;
    exports model;
}
