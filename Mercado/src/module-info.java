module Mercado {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
  

    opens application to javafx.graphics, javafx.fxml;
    opens controller to javafx.graphics, javafx.fxml;
}
