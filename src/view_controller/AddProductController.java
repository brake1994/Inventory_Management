package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ControllerMethods;
import model.Inventory;
import model.Part;
import model.Product;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller for add product screen
 */
public class AddProductController implements Initializable {

    private Product newProduct = new Product();
    private ObservableList<Part> newAssociatedPartsList = newProduct.getAllAssociatedParts();

    /**
     * Initialize add product screen.
     * Tables are filled during this.
     * Unique product ID numbers are generated.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //initialize first table
        tableViewOne.setItems(Inventory.getAllParts());
        TablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        TablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //initialize second table
        tableViewTwo.setItems(newAssociatedPartsList);
        tablePartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //generate unique product Id
        generateProductId();
    }

    @FXML
    private TableView<Part> tableViewOne;
    @FXML
    private TableColumn<Part, Integer> TablePartID;
    @FXML
    private TableColumn<Part, String> TablePartName;
    @FXML
    private TableColumn<Part, Integer> TableInventory;
    @FXML
    private TableColumn<Part, Double> TablePrice;
    @FXML
    private TextField SearchPartField;
    @FXML
    private TextField enterID;
    @FXML
    private TextField enterName;
    @FXML
    private TextField enterInv;
    @FXML
    private TextField enterPrice;
    @FXML
    private TextField enterMax;
    @FXML
    private TextField enterMin;
    @FXML
    private TableView<Part> tableViewTwo;
    @FXML
    private TableColumn<Part, Integer> tablePartId;
    @FXML
    private TableColumn<Part, String> tablePartName;
    @FXML
    private TableColumn<Part, Integer> tableInventory;
    @FXML
    private TableColumn<Part, Double> tablePrice;

    private void generateProductId(){
        Random random = new Random();
        int upperBound = 200;
        int lowerBound = 100;
        int randId = random.nextInt((upperBound - lowerBound)) + lowerBound;
        if(isUniqueProductId(randId)){
            if(randId != 100) {
                enterID.setText(Integer.toString(randId));
                enterID.setEditable(false);
            }
            else{
                generateProductId();
            }
        }
        else{
            generateProductId();
        }
    }

    private boolean isUniqueProductId(int id){
        Product u = Inventory.lookupProduct(id);
        return u == null;
    }

    @FXML
    void OnActionAddSelectedPart() {
        newProduct.addAssociatedPart(tableViewOne.getSelectionModel().getSelectedItem());
        tableViewTwo.refresh();
    }

    @FXML
    void OnActionCancelChanges(ActionEvent event) {
        ControllerMethods.returnToMainScreen(event);
    }

    @FXML
    void OnActionRemoveSelectedPart() {
        if(ControllerMethods.deleteConfirmationPopup()) {
            newProduct.deleteAssociatedPart(tableViewTwo.getSelectionModel().getSelectedItem());
            tableViewTwo.refresh();
        }
    }

    @FXML
    void OnActionSaveChanges(ActionEvent event) {
        try {
            int id = Integer.parseInt(enterID.getText());
            String q = enterName.getText();
            String name = q.substring(0, 1).toUpperCase() + q.substring(1);
            int stock = Integer.parseInt(enterInv.getText());
            double price = Double.parseDouble(enterPrice.getText());
            int max = Integer.parseInt(enterMax.getText());
            int min = Integer.parseInt(enterMin.getText());
            boolean validationCheck = false;
            TextField[] variables = {enterID, enterName, enterInv, enterPrice, enterMin, enterMax};

            for(TextField testField : variables){
                boolean fieldError = ControllerMethods.checkForInvalidEntries(testField);
                if(fieldError){
                    validationCheck = true;
                    ControllerMethods.showErrorPopup("Fill in all fields");
                    break;
                }
            }
            if(ControllerMethods.checkForInvalidInventory(stock, min, max)){
                if(min > max){
                    ControllerMethods.showErrorPopup("Invalid inventory entry. Min > max!");
                    return;
                }
                if(stock < 0 || min < 0 || max < 0){
                    ControllerMethods.showErrorPopup("Error! All inventory values must be non-negative.");
                    return;
                }
                if(!(min <= stock && stock <= max)){
                    ControllerMethods.showErrorPopup("Error! Inventory not within min-max range.");
                    return;
                }
            }
            if(!ControllerMethods.productPriceValidation(price, newAssociatedPartsList)){
                ControllerMethods.showErrorPopup("Parts cost more than product!");
                return;
            }
            if(validationCheck){
                return;
            }
            else {
                Product newProduct = new Product(id, name, price, stock, min, max);
                for (Part part : newAssociatedPartsList) {
                    newProduct.addAssociatedPart(part);
                }
                Inventory.addProduct(newProduct);
            }

            ControllerMethods.returnToMainScreen(event);
        }
        catch(NumberFormatException e){
            ControllerMethods.showErrorPopup("Enter valid input: " + e.getMessage());
        }

    }

    @FXML
    void OnActionSearchPart() {
        try {
            if (!SearchPartField.getText().isEmpty()) {
                boolean isInt = ControllerMethods.isNumber(SearchPartField.getText().trim());
                if (!isInt) {
                    String q = SearchPartField.getText().trim();
                    String r = q.substring(0, 1).toUpperCase() + q.substring(1);  //changing first char of input to uppercase
                    ObservableList<Part> searchResultName = Inventory.lookupPart(r);
                    if(!searchResultName.isEmpty()) {
                        tableViewOne.setItems(searchResultName);
                    }
                    else{
                        ControllerMethods.showErrorPopup("Part not found!");
                        tableViewOne.setItems(Inventory.getAllParts());
                    }

                }
                else {
                    Part q = Inventory.lookupPart(Integer.parseInt(SearchPartField.getText().trim()));
                    ObservableList<Part> searchResultId = FXCollections.observableArrayList();
                    searchResultId.add(q);
                    if(q == null) {
                        ControllerMethods.showErrorPopup("Part not found!");
                        tableViewOne.setItems(Inventory.getAllParts());
                    }
                    else{
                        tableViewOne.setItems(searchResultId);
                    }
                }
            }
            tableViewOne.refresh();
        }
        catch (NumberFormatException e){
            ControllerMethods.showErrorPopup("Enter valid input: " + e.getMessage());
        }
        catch(NullPointerException e){
            ControllerMethods.showErrorPopup("Part not found!");
        }
    }


}
