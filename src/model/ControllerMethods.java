package model;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;

import javax.swing.*;
import java.io.IOException;
/**
 * Created this class to reduce redundancy throughout program.
 * Contains methods only used within controller classes.
 */

public class ControllerMethods {

    /**
     * This method is used to check for invalid entries that may be submitted.
     * Invalid entries include: null or empty
     * @param testField textfields to be checked
     * @return boolean
     */
    public static boolean checkForInvalidEntries(TextField testField){
        return testField == null || testField.getText().isEmpty();
    }

    /**
     * Checks inventory validity.
     * Inventory(Stock) must be with range of min-max.
     * Parameters must be non-negative integers.
     * min must also be less than max
     * @param inv Inventory(stock)
     * @param min Minimum stock
     * @param max Maximum stock
     * @return boolean
     */
    public static boolean checkForInvalidInventory(int inv, int min, int max){
        if(inv < min || inv > max) return true;
        else return inv < 0 || min < 0 || min > max;
    }

    /**
     * Function used to return to main screen GUI
     * @param event Click
     */
    public static void returnToMainScreen(Event event){
        try{
            Stage stage;
            Parent scene;
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Main.class.getResource("/view_controller/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a popup window to display error messages
     * @param message Error message to be displayed
     */
    public static void showErrorPopup(String message){
        Stage popup = new Stage();
        Label errorMessage = new Label(message);
        Font errorFont = new Font("Aeriel", 14);
        errorMessage.setFont(errorFont);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popup.close());
        VBox layout = new VBox(20);
        layout.getChildren().addAll(errorMessage, closeButton);
        Scene popupScene = new Scene(layout, 250, 100);
        popup.setScene(popupScene);
        popup.setResizable(true);
        popup.showAndWait();
    }

    /**
     * Popup confirmation when delete or remove actions are called
     * @return Boolean value. True if user selects yes.
     */
    public static boolean deleteConfirmationPopup(){
        JFrame j = new JFrame();
        j.setSize(200, 200);
        j.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        int w = JOptionPane.showConfirmDialog(j, "Are you sure?");
        if(w == JOptionPane.YES_OPTION){
            j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            return true;
        }
        else if(w == JOptionPane.NO_OPTION) return false;
        else return false;
    }

    /**
     * Checking to see if input is a number to determine which search to use
     * @param string Input being checked
     * @return Boolean value. True if number.
     */
    public static boolean isNumber(String string){
        for ( int i = 0; i < string.length(); i++){
            if(!Character.isDigit(string.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Check to see if product price is valid.
     * For a price to be valid: product price >= sum of associated parts price
     * @param productPrice Product price
     * @param associatedParts Associated parts checked
     * @return Boolean value. True if price is valid
     */
    public static boolean productPriceValidation(double productPrice, ObservableList<Part> associatedParts){
        double partsPrice = 0;

        for (Part part : associatedParts) {
            partsPrice += part.getPrice();
        }
        return productPrice >= partsPrice;
    }
}
