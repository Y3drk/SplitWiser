package com.splitwiser.splitwiserclient.auxiliary;

import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import com.splitwiser.splitwiserclient.util.libs.graphs.brunomnsilva.smartgraph.graph.Digraph;
import com.splitwiser.splitwiserclient.util.libs.graphs.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.splitwiser.splitwiserclient.util.libs.graphs.brunomnsilva.smartgraph.graph.Graph;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class GraphDrawer {
    private int edgesCounter = 0;

    private final List<User> members;

    private final List<Payment> payments;

    public GraphDrawer(List<User> members, List<Payment> payments){
        this.members = members;
        this.payments = payments;
    }

    private void addAllMembersAsVertices(Digraph<String, String> graph){
        for (User member: this.members
        ) {
            graph.insertVertex(this.createMemberNameplate(member));
        }
    }

    private String createMemberNameplate(User member){
        return member.getFirstName()+ " " + member.getLastName();
    }

    private BigDecimal calculatePaymentValue(Payment payment){
        //if it's a group payment
        if (payment.getReceivers().contains(payment.getPayer())){
            return payment.getAmount().divide(BigDecimal.valueOf(payment.getReceivers().size()), RoundingMode.HALF_DOWN);
        }
        else{
            return payment.getAmount();
        }
    }

    private void addEdgesForPayment(Digraph<String, String> graph, Payment payment){
        User payer = payment.getPayer();

        for (User receiver: payment.getReceivers()
        ) {
            if (!receiver.equals(payer)) {
                graph.insertEdge(createMemberNameplate(payer), createMemberNameplate(receiver), "#"+this.edgesCounter+"-"+calculatePaymentValue(payment).toString()+"$");
                this.increaseEdgesCounter();
            }
        }
    }

    public Graph<String, String> buildGraph() {

        Digraph<String, String> newGraph = new DigraphEdgeList<>();

        this.addAllMembersAsVertices(newGraph);

//  diagnostic prints -> for now not deleted in case the need reemerges later
//        for (Payment payment: this.payments
//        ) {
//            System.out.println("payer:" +createMemberNameplate( payment.getPayer()));
//            System.out.println("amount:" + payment.getAmount());
//            System.out.println("Receivers: ");
//            for (User rec: payment.getReceivers()
//            ) {
//                System.out.println(createMemberNameplate(rec));
//            }
//            System.out.println("########################################");
//        }

        for (Payment payment: this.payments
        ) {
            this.addEdgesForPayment(newGraph, payment);
        }


        return newGraph;
    }

    private void increaseEdgesCounter(){
        this.edgesCounter++;
    }
}
