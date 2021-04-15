package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHousePart;
import model.Inventory;
import model.OutsourcedPart;
import model.Product;

/**
 * This application is an inventory management system.
 * Two type of items are stored in the inventory, Parts and Products.
 * Products have the ability to contain parts.
 *
 *
 * Rubric G:
 *  * 1. Runtime errors were handled through exception handling and if statements (see save buttons).
 *  *    Another issue I found was Products not retaining associated parts if the user removed a part and then cancelled to the main screen.
 *  *    To solve this problem I stored the removed parts locally and added them back if the user clicked the cancel button.
 *  * 2. I have many future plans for this application. My goal is to expand it into a more broad use business application.
 *  *    Some features I plan on adding include finance and employee systems.
 *  *    A main menu screen would be opened upon running the application and the user can select the system they want.
 *  *    As for this version of the application, I want to have the application save changes locally.
 *  *    Currently the application only stores data during runtime.
 * @author Tanner Brake
 */
public class Main extends Application {

    /**
     * Start method to display/load initial screen
     * @param stage Screen
     * @throws Exception Exception for loader
     */
    @Override
    public void start(Stage stage) throws Exception{
        //load fxml file
        Parent parent = FXMLLoader.load(getClass().getResource("/view_controller/MainScreen.fxml"));

        //build scene
        Scene scene = new Scene(parent);

        //display
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Main method of the application
     * @param args Arguments
     */
    public static void main(String[] args) {

        //test data
        InHousePart part1 = new InHousePart(1,"Brakes", 59.99, 5, 1, 10,20);
        OutsourcedPart part2 = new OutsourcedPart(2, "Tires", 85.99, 8, 1, 10, "Michelin");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Product bike = new Product(101, "Bike", 255.99, 5, 1, 5);
        Inventory.addProduct(bike);
        bike.addAssociatedPart(part1);


        launch(args);





    }
}
