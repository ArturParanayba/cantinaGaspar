/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author osval
 */
public class Login extends Application {
    
    private static Stage stage;
    

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml")); // carrega fxml
        Scene scene = new Scene(root); // coloca o FXML em uma cena
        stage.setScene(scene); // Coloca a cena em uma janela
        stage.setTitle("Login");
        stage.show(); // abre a janela
        setStage(stage);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        stage = stage;
    }

    
    
    
}
