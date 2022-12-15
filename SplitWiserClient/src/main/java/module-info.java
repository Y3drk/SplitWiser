module com.splitwiser.splitwiserclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires retrofit2;
    requires io.reactivex.rxjava3;
    requires retrofit2.adapter.rxjava3;
    requires retrofit2.converter.jackson;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires java.sql;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.splitwiser.splitwiserclient to javafx.fxml;
    exports com.splitwiser.splitwiserclient;
    exports com.splitwiser.splitwiserclient.model.group;
    exports com.splitwiser.splitwiserclient.model.user;
    exports com.splitwiser.splitwiserclient.model.payment;
    exports com.splitwiser.splitwiserclient.controllers;
    exports com.splitwiser.splitwiserclient.mockData;
    opens com.splitwiser.splitwiserclient.controllers to javafx.fxml;
    exports com.splitwiser.splitwiserclient.data;
    exports com.splitwiser.splitwiserclient.util;
}