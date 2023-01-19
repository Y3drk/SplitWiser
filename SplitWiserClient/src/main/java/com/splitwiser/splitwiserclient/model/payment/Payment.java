package com.splitwiser.splitwiserclient.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.splitwiser.splitwiserclient.model.category.Category;
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
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = {"group"})
public class Payment {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;

    private ObjectProperty<Group> group = new SimpleObjectProperty<>();

    private ObjectProperty<BigDecimal> amount = new SimpleObjectProperty<>();

    private ObjectProperty<User> payer = new SimpleObjectProperty<>();

    private ObservableList<User> receivers = FXCollections.observableArrayList();

    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

    private StringProperty description = new SimpleStringProperty();

    private ObjectProperty<Category> category;

    // for Jackson
    public Payment() {
        this(null, BigDecimal.valueOf(0), LocalDate.now(), "", null, new ArrayList<>(), Category.OTHER);
    }

    //    single payment
    public Payment(Group group, BigDecimal amount, LocalDate date, String description, User payer, User receiver, Category category) {
        this(group, amount, date, description, payer, List.of(receiver), category);
    }

    //    group payment
    public Payment(Group group, BigDecimal amount, LocalDate date, String description, User payer, List<User> receivers, Category category) {
        this.group = new SimpleObjectProperty<>(group);
        this.amount = new SimpleObjectProperty<>(amount);
        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleStringProperty(description);
        this.payer = new SimpleObjectProperty<>(payer);
        this.category = new SimpleObjectProperty<>(category);
        this.receivers.addAll(receivers);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group.get();
    }

    public void setGroup(Group group) {
        this.group.set(group);
    }

    public ObjectProperty<Group> groupProperty() {
        return group;
    }

    public BigDecimal getAmount() {
        return amount.get();
    }

    public void setAmount(BigDecimal amount) {
        this.amount.set(amount);
    }

    private ObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }

    public User getPayer() {
        return payer.get();
    }

    public void setPayer(User payer) {
        this.payer.set(payer);
    }

    public ObjectProperty<User> payerProperty() {
        return payer;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public ObservableList<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers.setAll(receivers);
    }

    public Category getCategory() {
        return category.get();
    }

    public void setCategory(Category category) {
        this.category.set(category);
    }

    public ObjectProperty<Category> categoryProperty() {
        return category;
    }
}
