module com.splitwiser.splitwiserclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.splitwiser.splitwiserclient to javafx.fxml;
    exports com.splitwiser.splitwiserclient;
}