module br.dev.nicolas.dukemarket.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    
    requires java.sql;

    opens br.dev.nicolas.dukemarket.graphic to javafx.fxml;
    opens br.dev.nicolas.dukemarket.graphic.controller to javafx.fxml;
    
    exports br.dev.nicolas.dukemarket.graphic;
    exports br.dev.nicolas.dukemarket.graphic.controller;
    exports br.dev.nicolas.dukemarket.graphic.DAO;
    exports br.dev.nicolas.dukemarket.graphic.model;
    exports connection;
    
    requires mysql.connector.java;
    
}
