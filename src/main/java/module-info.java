module com.beloin.so_cashierclientproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.beloin.so_cashierclientproject to javafx.fxml;
    exports com.beloin.so_cashierclientproject;
}