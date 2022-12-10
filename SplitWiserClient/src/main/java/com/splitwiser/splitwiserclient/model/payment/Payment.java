package com.splitwiser.splitwiserclient.model.payment;

import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Payment {
    private ObjectProperty<Group> group;

    private ObjectProperty<BigDecimal> value;

    private ObjectProperty<User> payer;

    private ObjectProperty<Date> date;

    private StringProperty description;

    public Group getGroup() {
        return group.get();
    }

    public ObjectProperty<Group> groupProperty() {
        return group;
    }

    public BigDecimal getValue() {
        return value.get();
    }

    public ObjectProperty<BigDecimal> valueProperty() {
        return value;
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
}
