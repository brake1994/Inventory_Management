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
import java.util.ResourceBundle;

/**
 * Controller for modify product screen
 */
public class ModifyProductController implements Initializable {
    /**
     * Product object sent data is stored to
     */
    private Product p;
    /**
     * Associated parts list to store sent data.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Removed parts list used in cancel button action.
     */
    private ObservableList<Part> removedParts = FXCollections.observableArrayList();

    /**
     * Initialize modify product screen.
     * Tables are filled during this.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Modify Product Loaded");

        //initialize first table
        tableViewOne.setItems(Inventory.getAllParts());
        TablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        TablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //initialize second table
        tableViewTwo.setItems(associatedParts);
        tablePartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tablePartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

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
    private TextField searchPartField;
    @FXML
    private TextField enterId;
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
    private TableColumn<Part, Integer> tablePartInv;
    @FXML
    private TableColumn<Part, Double> tablePrice;

    @FXML
    void OnActionAddSelectedPart() {
        Part part = tableViewOne.getSelectionModel().getSelectedItem();
        if(!associatedParts.contains(part)) {
            p.addAssociatedPart(part);
            associatedParts.add(part);
            tableViewTwo.refresh();
        }
        else if (associatedParts.contains(part)){
            ControllerMethods.showErrorPopup("Part already added.");
            tableViewTwo.refresh();
        }
        else{
            ControllerMethods.showErrorPopup("No part selected.");
        }
    }

    /**
     * Cancels changes and returns to main screen.
     * Associated parts would not be the same if user deleted one
     * so I added a local variable to store removed parts.
     * The removed parts are then added back into the product.
     * @param event
     */
    @FXML
    void OnActionCancelChanges(ActionEvent event) {

        ControllerMethods.returnToMainScreen(event);

        for(Part part: removedParts){
            p.addAssociatedPart(part);
        }
    }

    @FXML
    void OnActionRemoveSelectedPart() {
        Part part = tableViewTwo.getSelectionModel().getSelectedItem();
        if(ControllerMethods.deleteConfirmationPopup()) {
            p.deleteAssociatedPart(part);
            removedParts.add(part);
            associatedParts.remove(part);
            tableViewTwo.refresh();
        }
    }

    @FXML
    void OnActionSaveChanges(ActionEvent event) {
        try {
            int id = Integer.parseInt(enterId.getText());
            String q = enterName.getText();
            String name = q.substring(0, 1).toUpperCase() + q.substring(1);
            int stock = Integer.parseInt(enterInv.getText());
            double price = Double.parseDouble(enterPrice.getText());
            int max = Integer.parseInt(enterMax.getText());
            int min = Integer.parseInt(enterMin.getText());
            boolean validationCheck = false;
            TextField[] variables = {enterId, enterName, enterInv, enterPrice, enterMin, enterMax};

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
            if(!ControllerMethods.productPriceValidation(price, associatedParts)){
                ControllerMethods.showErrorPopup("Parts cost more than product!");
                return;
            }
            if(validationCheck){
                return;
            }
            else {
                Product newProduct = new Product(id, name, price, stock, min, max);
                int oldProductIndex = Inventory.getAllProducts().indexOf(p);
                for (Part associatedPart : associatedParts) {
                    newProduct.addAssociatedPart(associatedPart);
                }
                Inventory.updateProduct(oldProductIndex, newProduct);
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
            if (!searchPartField.getText().isEmpty()) {
                boolean isInt = ControllerMethods.isNumber(searchPartField.getText().trim());
                if (!isInt) {
                    String q = searchPartField.getText().trim();
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
                    Part q = Inventory.lookupPart(Integer.parseInt(searchPartField.getText().trim()));
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

    /**
     * Method used to send data from MainScreenController to this controller.
     * New product is initialized.
     * Data is parsed and stored in new product.
     * @param p Old product data is copied from.
     */
    public void sendProductData(Product p){
        System.out.println("Data sent.");
        this.p = p;

        for(int i = 0; i < p.getAllAssociatedParts().size(); i++){
            Part part;
            part = p.getAllAssociatedParts().get(i);
            if(part != null){
                associatedParts.add(part);
            }
        }
        //Populate textfields
        enterId.setText(Integer.toString(p.getProductId()));
        enterId.setEditable(false);
        enterName.setText(p.getProductName());
        enterInv.setText(Integer.toString(p.getProductStock()));
        enterPrice.setText(Double.toString(p.getProductPrice()));
        enterMax.setText(Integer.toString(p.getProductMax()));
        enterMin.setText(Integer.toString(p.getProductMin()));
    }

}
