/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author osval
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnSair;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEntrar.setOnMouseClicked((MouseEvent e) ->{
            System.out.println("entrei");
        });
    
          btnSair.setOnMouseClicked((MouseEvent e) ->{
            System.out.println("sai");
        });
    
    }    
    
       
}
