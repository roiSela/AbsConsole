package clientcomponents.clientscramblebody;

import app.AppController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Bank;
import model.classesForTables.LoanTableObj;
import org.controlsfx.control.CheckComboBox;

public class ClientScrambleBody {

        @FXML
        private Button scrambleButton;

        @FXML
        private TextField sumToInvestText;

        @FXML
        private TextField minimumInterestText;

        @FXML
        private TextField minimumYazForLoanText;

        @FXML
        private TextField openLoansText;

        @FXML
        private TextField maximunOwenership;

        @FXML
        private CheckComboBox<String> categoryCheckBox;

        @FXML
        private CheckComboBox<String> loansChosen;

        @FXML
        private Button findLoansButton;

        @FXML
        private TableView<LoanTableObj> loansTable;

        @FXML
        private TableColumn<LoanTableObj, String> id;

        @FXML
        private TableColumn<LoanTableObj, String> owner;

        @FXML
        private TableColumn<LoanTableObj, String> category;

        @FXML
        private TableColumn<LoanTableObj, String> amount;

        @FXML
        private TableColumn<LoanTableObj, String> time;

        @FXML
        private TableColumn<LoanTableObj, String> interest;

        @FXML
        private TableColumn<LoanTableObj, String> rate;

        @FXML
        private TableColumn<LoanTableObj, String> status;

        @FXML
        private TableColumn<LoanTableObj, String> pending;

        @FXML
        private TableColumn<LoanTableObj, String> active;

        @FXML
        private TableColumn<LoanTableObj, String> risk;

        @FXML
        private TableColumn<LoanTableObj, String> finished;



        ObservableList<LoanTableObj> loansInformation;

        private AppController mainController;
        private Bank businessLogic;


        @FXML
        public void initialize() {
                //initialize loansTable
                id.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("id"));
                owner.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("owner"));
                category.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("category"));
                amount.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("amount"));
                time.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("time"));
                interest.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("interest"));
                rate.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("rate"));
                status.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("status"));
                pending.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("pending"));
                active.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("active"));
                risk.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("risk"));
                finished.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("finished"));
                loansInformation = null;
        }

        @FXML
        void findLoansButtonPressed(ActionEvent event) {

        }

        @FXML
        void scrambleButtonPressed(ActionEvent event) {

        }

        public void updateLoansInformationForTable() {
                if (loansInformation != null) {
                        loansInformation.clear();
                }
                loansInformation = businessLogic.getLoansInformationForTable();
                loansTable.setItems(loansInformation);
        }



        public void setMainController(AppController mainController) {
                this.mainController = mainController;
        }

        public void setBusinessLogic(Bank businessLogic) {
                this.businessLogic = businessLogic;
        }

}

