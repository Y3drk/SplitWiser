package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.auxiliary.GraphDrawer;
import com.splitwiser.splitwiserclient.auxiliary.GraphType;
import com.splitwiser.splitwiserclient.auxiliary.PaymentCellFactory;
import com.splitwiser.splitwiserclient.auxiliary.transitivity.GraphMatrixTransformer;
import com.splitwiser.splitwiserclient.auxiliary.transitivity.TransitivitySolver;
import com.splitwiser.splitwiserclient.data.DataProvider;
import com.splitwiser.splitwiserclient.model.category.Category;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import com.splitwiser.splitwiserclient.util.CalculateService;
import com.splitwiser.splitwiserclient.util.libs.graphs.brunomnsilva.smartgraph.graph.Graph;
import com.splitwiser.splitwiserclient.util.libs.graphs.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.splitwiser.splitwiserclient.util.libs.graphs.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.splitwiser.splitwiserclient.util.libs.graphs.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SummaryController {
    @FXML
    private Button transitivityButton;
    @FXML
    private Button displayTransitiveGraphButton;
    @FXML
    private ToggleButton otherCategoryButton;
    @FXML
    private ToggleButton ticketsCategoryButton;
    @FXML
    private ToggleButton eatingCategoryButton;
    @FXML
    private ToggleButton transportCategoryButton;
    @FXML
    private ToggleButton entertainmentCategoryButton;

    private ToggleGroup categoryButtons;

    @FXML
    private ToggleButton allCategoriesButton;
    @FXML
    private ListView<String> totalSummaryList;
    @FXML
    private Label groupLabel;
    @FXML
    private Label userInvolvedLabel;

    @FXML
    private ListView<Payment> otherPaymentList;

    @FXML
    private Label userInvolvedBalanceLabel;

    @FXML
    private Label userInvolvedBalanceValueLabel;
    @FXML
    private ListView<Payment> userInvolvedPaymentsList;

    private ObservableList<Payment> allPayments = FXCollections.observableArrayList();
    private AppController appController;

    private ObjectProperty<User> currentUser;

    private SimpleObjectProperty<BigDecimal> currentUsersBalance;

    private DataProvider dataProvider;

    private boolean isTransitivityEnabled;

    private GraphMatrixTransformer cachedTransitivityGraph = null;


    @FXML
    private void initialize() {
        this.currentUser = new SimpleObjectProperty<>();
        this.currentUsersBalance = new SimpleObjectProperty<>(BigDecimal.valueOf(0));
        this.otherPaymentList.setCellFactory(new PaymentCellFactory());
        this.userInvolvedPaymentsList.setCellFactory(new PaymentCellFactory());
        this.totalSummaryList.setFocusTraversable(false);

        this.disableAllCategoriesButton();
        this.allCategoriesButton.setSelected(true);

        this.categoryButtons = new ToggleGroup();
        this.otherCategoryButton.setToggleGroup(categoryButtons);
        this.ticketsCategoryButton.setToggleGroup(categoryButtons);
        this.eatingCategoryButton.setToggleGroup(categoryButtons);
        this.transportCategoryButton.setToggleGroup(categoryButtons);
        this.entertainmentCategoryButton.setToggleGroup(categoryButtons);
        this.allCategoriesButton.setToggleGroup(categoryButtons);

        this.isTransitivityEnabled = false;
        this.displayTransitiveGraphButton.setDisable(true);
    }

    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @FXML
    private void onCreatePaymentButtonClick() {
        Payment newPayment = new Payment(this.currentUser.get().getGroup(), BigDecimal.valueOf(0), LocalDate.of(2022, 1, 1), "", this.currentUser.get(), this.currentUser.get().getGroup().getMembers(), Category.OTHER);
        appController.showCreatePaymentDialog(newPayment);
        this.dataProvider.refetchSingleGroupData(this.currentUser.get().getGroup().getId());
        this.updateView();
    }

    @FXML
    private void onDisplayMainGraphButtonClick(ActionEvent actionEvent) {
        appController.showViewGraphDialog("All payments Graph", GraphType.ALL_PAYMENTS, createGraphView(this.allPayments));
    }

    @FXML
    private void onDisplayAggregatedGraphButtonClick(ActionEvent actionEvent) {
        appController.showViewGraphDialog("Aggregated payments Graph", GraphType.AGGREGATED_PAYMENTS, createGraphView(CalculateService.calculateAggregatedPayments(this.currentUser.get(), this.allPayments)));
    }

    @FXML
    private void onDisplayTransitiveGraphButtonClick(ActionEvent actionEvent) {
        appController.showViewGraphDialog("Transitive payments Graph", GraphType.TRANSITIVE_PAYMENTS, createGraphView(this.cachedTransitivityGraph.getOutputPayments()));
    }

    private SmartGraphPanel<String, String> createGraphView(List<Payment> payments) {
        GraphDrawer drawer = new GraphDrawer(this.currentUser.get().getGroup().getMembers(), payments);
        Graph<String, String> graph = drawer.buildGraph();

        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();

        return new SmartGraphPanel<>(graph, strategy);
    }

    public void initData() {
        this.dataProvider.refetchSingleGroupData(this.currentUser.get().getGroup().getId());
        this.updateView();
    }


    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        this.appController.initLoginLayout();
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public ObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser.set(currentUser);
        this.userInvolvedLabel.setText("Involving you");
        this.userInvolvedBalanceLabel.setText(this.currentUser.get().getFirstName() + " " + this.currentUser.get().getLastName() + " balance: ");
        this.groupLabel.setText(this.currentUser.get().getGroup().getName() + " balance");
    }

    public void updateView() {
        ObservableList<Payment> allPayments = dataProvider.getPaymentsData();
        ObservableList<Payment> userInvolvedPayments = dataProvider.getAllUserInvolvedPayments(this.currentUser.get(), allPayments);
        userInvolvedPaymentsList.setItems(userInvolvedPayments);
        otherPaymentList.setItems(allPayments.filtered((payment) -> !userInvolvedPayments.contains(payment)));
        totalSummaryList.setItems(CalculateService.calculateBalanceBetweenAll(this.currentUser.get(), allPayments));
        this.allPayments = allPayments;
        this.currentUsersBalance = new SimpleObjectProperty<>(CalculateService.calculateUserBalance(this.currentUser.get(), this.userInvolvedPaymentsList.getItems()));
        this.userInvolvedBalanceValueLabel.setText(this.currentUsersBalance.get().toString());
    }

    private void disableAllCategoriesButton() {
        this.allCategoriesButton.setDisable(true);
    }

    private void enableAllCategoriesButton() {
        this.allCategoriesButton.setDisable(false);
    }

    private ToggleButton recognizeSelectedButton(ActionEvent actionEvent) {
        if (actionEvent.getTarget() instanceof ToggleButton) {
            return (ToggleButton) actionEvent.getTarget();
        } else {
            throw new RuntimeException("Invalid element Clicked - should never Happen!!!");
        }
    }

    @FXML
    private void onAllCategoriesButtonClick(ActionEvent actionEvent) {
        this.disableAllCategoriesButton();
        this.recognizeSelectedButton(actionEvent);
        this.dataProvider.refetchSingleGroupData(this.currentUser.get().getGroup().getId());
        this.updateView();
    }

    @FXML
    private void onFilterBySingleCategoryButtonClick(ActionEvent actionEvent) {
        this.enableAllCategoriesButton();
        ToggleButton clickedButton = this.recognizeSelectedButton(actionEvent);
        Category clickedCategory = Category.valueOf(clickedButton.getText());
        this.dataProvider.refetchGroupDataByCategory(this.currentUser.get().getGroup().getId(), clickedCategory);
        this.updateView();
    }

    @FXML
    private void onChangeTransitivityButtonClick(ActionEvent actionEvent) {
        //TODO: transitivity is not synchronized with category filtering! Figure out if caching is even worth the effort and synchronize both options
        if (this.isTransitivityEnabled) {
            //Disabling
            this.changeTransitivityConnectedButtons(false, "Enable Transitivity", "-fx-background-color: #3BFD02");

            totalSummaryList.setItems(CalculateService.calculateBalanceBetweenAll(this.currentUser.get(), this.allPayments));
        } else {
            //Enabling
            this.changeTransitivityConnectedButtons(true, "Disable Transitivity","-fx-background-color: orange");

            // TODO: calculate the transitivity graph -> if it's done the first time it can be cached(?),
            if (this.cachedTransitivityGraph != null) {
                //if we have the graph cached we can just summon it
                totalSummaryList.setItems(CalculateService.calculateBalanceBetweenAll(this.currentUser.get(), this.cachedTransitivityGraph.getOutputPayments()));
            } else {
                //otherwise we need to calculate the graph from the beginning
                this.cachedTransitivityGraph = new GraphMatrixTransformer(this.currentUser.get().getGroup().getMembers(), FXCollections.observableArrayList(CalculateService.calculateAggregatedPayments(this.currentUser.get(), this.allPayments)));
                this.cachedTransitivityGraph.transformPaymentsToGraph();

                TransitivitySolver solver = new TransitivitySolver(this.cachedTransitivityGraph);

                solver.solve();

                this.cachedTransitivityGraph.transformGraphToPayments();

                totalSummaryList.setItems(CalculateService.calculateBalanceBetweenAll(this.currentUser.get(), this.cachedTransitivityGraph.getOutputPayments()));
            }

        }
    }

    private void changeTransitivityConnectedButtons(boolean newValue, String newChangeButtonText, String newChangeButtonStyle) {
        this.isTransitivityEnabled = newValue;
        this.displayTransitiveGraphButton.setDisable(!newValue);

        this.transitivityButton.setStyle(newChangeButtonStyle);
        this.transitivityButton.setText(newChangeButtonText);
    }
}
