package com.splitwiser.splitwiserclient.model.payment;

import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.util.Date;

public class Payment {
    private ObjectProperty<Group> group;

    private ObjectProperty<BigDecimal> amount;

    private ObjectProperty<User> payer;

    private ObjectProperty<User> receiver = new SimpleObjectProperty<User>(null);

    private ObjectProperty<Date> date;

    private StringProperty description;

    //    group payment
    public Payment(Group group, BigDecimal amount, Date date, String description, User payer) {
        this.group = new SimpleObjectProperty<Group>(group);
        this.amount = new SimpleObjectProperty<BigDecimal>(amount);
        this.date = new SimpleObjectProperty<Date>(date);
        this.description = new SimpleStringProperty(description);
        this.payer = new SimpleObjectProperty<User>(payer);
    }

    //    single payment
    public Payment(Group group, BigDecimal amount, Date date, String description, User payer, User receiver) {
        this.group = new SimpleObjectProperty<Group>(group);
        this.amount = new SimpleObjectProperty<BigDecimal>(amount);
        this.date = new SimpleObjectProperty<Date>(date);
        this.description = new SimpleStringProperty(description);
        this.payer = new SimpleObjectProperty<User>(payer);
        this.receiver = new SimpleObjectProperty<User>(receiver);
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

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public User getReceiver() {
        return receiver.get();
    }

    public ObjectProperty<User> receiverProperty() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver.set(receiver);
    }
}
