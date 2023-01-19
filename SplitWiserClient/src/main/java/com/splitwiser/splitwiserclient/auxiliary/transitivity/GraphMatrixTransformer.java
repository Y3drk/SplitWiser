package com.splitwiser.splitwiserclient.auxiliary.transitivity;

import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.collections.ObservableList;

import java.util.List;

public class GraphMatrixTransformer {

    private ObservableList<User> membersAsVertices;

    private ObservableList<Payment> inputPayments;

//    private 2DArray matrixInitialGraphRepresentation;

    //    private 2DArray matrixFinalGraphRepresentation;

    private ObservableList<Payment> outputPayments;


    public GraphMatrixTransformer(ObservableList<User> members, ObservableList<Payment> inputPayments){
        this.membersAsVertices = members;
        this.inputPayments = inputPayments;
    }

    public ObservableList<Payment> getOutputPayments() {
        return outputPayments;
    }

    public void transformPaymentsToGraph(){


    }

    public void transformGraphToPayments(){

    }
}
