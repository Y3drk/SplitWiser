package splitwiserclient;

import com.splitwiser.splitwiserclient.data.DataProvider;
import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import com.splitwiser.splitwiserclient.util.CalculateService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BalanceAndSummaryFunctionsTests {
    /**
     *                                      DISCLAIMER
     * We are all very well aware that true TDD should have had its units created before implementation,
     * but last week was really challenging to say the least...
     * Therefore, wanting to provide the best software possible,
     * we decided to cover both client and server sides with unit tests nevertheless.
     * The E2E tests were performed by the QAs of our team (ofc it's us but keep it a secret ;))
     */

    /**
     *                                     NECESSARY ASSUMPTIONS
     *                There are several things one should know about our payments model...
     *
     *             1. If it's a 1to1 payment then the receiver covers the whole cost of it.
     *          If somebody got you a bottle of beer you return it's entire value, not a half right?
     *
     *             2. if it's a 1toAll payment then we assume the payer also gets his share.
     *  if your friend buys a 5L jug of beer you will all drink it, including him, but it would still be nice to pull your weight.
     */



    DataProvider dataProvider = DataProvider.getInstance();

    Group group;

    ObservableList<User> members = FXCollections.observableArrayList();

    ObservableList<Payment> payments = FXCollections.observableArrayList();

    @BeforeEach
    void basicSetUp(){
        this.group = new Group("G1");

        User X = new User("X", "X", this.group);
        X.setId(1);

        User Y = new User("Y", "Y", this.group);
        Y.setId(2);

        User Z = new User("Z", "Z", this.group);
        Z.setId(3);

        this.members.add(X);
        this.members.add(Y);
        this.members.add(Z);

        this.group.setMembers(this.members);

        Payment p1 = new Payment(this.group, BigDecimal.valueOf(96), LocalDate.now(),"p1", X, this.members.stream().toList());
        Payment p2 = new Payment(this.group, BigDecimal.valueOf(12), LocalDate.now(),"p2", X, Y);
        Payment p3 = new Payment(this.group, BigDecimal.valueOf(28), LocalDate.now(),"p3", Z, Y);
        Payment p4 = new Payment(this.group, BigDecimal.valueOf(30), LocalDate.now(),"p4", Y, X);
        Payment p5 = new Payment(this.group, BigDecimal.valueOf(12), LocalDate.now(),"p5", Z, this.members.stream().toList());

        this.payments.add(p1);
        this.payments.add(p2);
        this.payments.add(p3);
        this.payments.add(p4);
        this.payments.add(p5);

        this.group.setPayments(this.payments);
    }

    private void addNewUser(){
        User W = new User("W", "W", this.group);
        W.setId(4);

        this.group.addUser(W);
        this.members.add(W);
    }

    private void addNewUsersPayments(){
        Payment p6 = new Payment(this.group, BigDecimal.valueOf(42), LocalDate.now(), "p6", this.members.get(3), this.members.get(0));
        Payment p7 = new Payment(this.group, BigDecimal.valueOf(16), LocalDate.now(), "p7", this.members.get(3), this.members);

        this.payments.add(p6);
        this.payments.add(p7);

        this.group.addPayment(p6);
        this.group.addPayment(p7);
    }

    @Test
    @DisplayName("first summary is correct")
    void testFirstSummary(){
        //given

        //when
        ObservableList<String> summaries = CalculateService.calculateBalanceBetweenAll(this.members.get(0), this.payments);

        //then
        assertEquals(summaries.get(0), "Y Y owns: X X -> 14.00$; Z Z -> 32.00$; ");
        assertEquals(summaries.get(1), "Z Z owns: X X -> 28.00$; ");
    }

    @Test
    @DisplayName("first balance for user X is correct")
    void testFirstBalance(){
        //given
        ObservableList<Payment> involvedX = FXCollections.observableArrayList();
        involvedX.add(payments.get(0));
        involvedX.add(payments.get(1));
        involvedX.add(payments.get(3));
        involvedX.add(payments.get(4));

        //when
        BigDecimal XBalance = CalculateService.calculateUserBalance(this.members.get(0), involvedX);

        //then
        assertEquals(0, BigDecimal.valueOf(42).compareTo(XBalance));
    }

    @Test
    @DisplayName("user X involved payments are correctly recognized")
    void testUserInvolvedPayments(){
        //given

        //when
        ObservableList<Payment> XInvolvedPayments = dataProvider.getAllUserInvolvedPayments(this.members.get(0), this.payments);

        //then
        assertEquals(4, XInvolvedPayments.size());
    }

    @Test
    @DisplayName("nothing changes when new user arrives")
    void testBalancesAfterUserAddition(){
        //given
        this.addNewUser();

        //when
        ObservableList<Payment> XInvolvedPayments = dataProvider.getAllUserInvolvedPayments(this.members.get(0), this.payments);
        BigDecimal XBalance = CalculateService.calculateUserBalance(this.members.get(0), XInvolvedPayments);

        //then
        assertEquals(0, BigDecimal.valueOf(42).compareTo(XBalance));
        assertEquals(4, XInvolvedPayments.size());
    }


    @Test
    @DisplayName("the newcomer involved transactions are empty")
    void testNewUsersInvolvedTransactions(){
        //given
        this.addNewUser();

        //when
        ObservableList<Payment> WInvolvedPayments = dataProvider.getAllUserInvolvedPayments(this.members.get(3), this.payments);

        //then
        assertEquals(0, WInvolvedPayments.size());
    }

    @Test
    @DisplayName("the newcomer balance is clean")
    void testNewUsersBalance(){
        //given
        this.addNewUser();

        //when
        ObservableList<Payment> WInvolvedPayments = dataProvider.getAllUserInvolvedPayments(this.members.get(3), this.payments);
        BigDecimal WBalance = CalculateService.calculateUserBalance(this.members.get(3), WInvolvedPayments);

        //then
        assertEquals(0, BigDecimal.valueOf(0).compareTo(WBalance));
        assertEquals(0, WInvolvedPayments.size());
    }

    @Test
    @DisplayName("other users summaries change when new user adds payments")
    void testSummariesAfterNewUsersPayments(){
        //given
        this.addNewUser();
        this.addNewUsersPayments();

        //when
        ObservableList<String> summaries = CalculateService.calculateBalanceBetweenAll(this.members.get(0), this.payments);

        //then
        assertEquals(summaries.get(0), "X X owns: W W -> 46.00$; ");
        assertEquals(summaries.get(1), "Y Y owns: X X -> 14.00$; Z Z -> 32.00$; W W -> 4.00$; ");
        assertEquals(summaries.get(2), "Z Z owns: X X -> 28.00$; W W -> 4.00$; ");
    }

    @Test
    @DisplayName("other users involved payments change correctly when new user adds payments regarding them")
    void testUserInvolvedTransactionsAfterNewUsersPayments(){
        //given
        this.addNewUser();
        this.addNewUsersPayments();

        //when
        ObservableList<Payment> XInvolvedPayments = dataProvider.getAllUserInvolvedPayments(this.members.get(0), this.payments);

        //then
        assertEquals(6, XInvolvedPayments.size());
    }

    @Test
    @DisplayName("other users balance changes accordingly when new user adds payments")
    void testUsersBalancesAfterNewUsersPayments(){
        //given
        this.addNewUser();
        this.addNewUsersPayments();

        //when
        ObservableList<Payment> XInvolvedPayments = dataProvider.getAllUserInvolvedPayments(this.members.get(0), this.payments);
        BigDecimal XBalance = CalculateService.calculateUserBalance(this.members.get(0), XInvolvedPayments);

        //then
        assertEquals(0, BigDecimal.valueOf(-4.00).compareTo(XBalance));
    }


    @Test
    @DisplayName("new users involved payments change correctly when he adds payments")
    void testNewUserInvolvedTransactionsAfterAddingPayments(){
        //given
        this.addNewUser();
        this.addNewUsersPayments();

        //when
        ObservableList<Payment> WInvolvedPayments = dataProvider.getAllUserInvolvedPayments(this.members.get(3), this.payments);

        //then
        assertEquals(2, WInvolvedPayments.size());
    }
}
