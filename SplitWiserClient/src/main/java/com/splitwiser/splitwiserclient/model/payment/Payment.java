package com.splitwiser.splitwiserclient.model.payment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class Payment {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private ObjectProperty<Group> group = new SimpleObjectProperty<>();

    private ObjectProperty<BigDecimal> amount = new SimpleObjectProperty<>();

    private ObjectProperty<User> payer = new SimpleObjectProperty<>();

    private ObservableList<User> receivers = FXCollections.observableArrayList();

    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

    private StringProperty description = new SimpleStringProperty();

    public void setGroup(Group group) {
        this.group.set(group);
    }

    public BigDecimal getAmount() {
        return amount.get();
    }

    public ObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }


    //    group payment
    public Payment(Group group, BigDecimal amount, LocalDate date, String description, User payer, List<User> receivers) {
        this.group = new SimpleObjectProperty<>(group);
        this.amount = new SimpleObjectProperty<>(amount);
        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleStringProperty(description);
        this.payer = new SimpleObjectProperty<>(payer);

        this.receivers.addAll(receivers);
    }


    //    single payment
    public Payment(Group group, BigDecimal amount, LocalDate date, String description, User payer, User receiver) {
        this.group = new SimpleObjectProperty<>(group);
        this.amount = new SimpleObjectProperty<>(amount);
        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleStringProperty(description);
        this.payer = new SimpleObjectProperty<>(payer);
        this.receivers = FXCollections.observableArrayList();
        this.receivers.add(receiver);
    }

    // for Jackson
    public Payment() {
    }

    public Group getGroup() {
        return group.get();
    }

    public ObjectProperty<Group> groupProperty() {
        return group;
    }

    public BigDecimal getValue() {
        return amount.get();
    }

    public ObjectProperty<BigDecimal> valueProperty() {
        return amount;
    }

    public User getPayer() {
        return payer.get();
    }

    public ObjectProperty<User> payerProperty() {
        return payer;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public ObservableList<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers.addAll(receivers);
    }

    public void setAmount(BigDecimal amount) {
        this.amount.set(amount);
    }

    public void setPayer(User payer) {
        this.payer.set(payer);
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
