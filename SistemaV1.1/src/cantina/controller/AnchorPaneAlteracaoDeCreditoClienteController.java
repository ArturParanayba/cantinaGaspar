/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.controller;

import cantina.model.POJO.Cliente;
import cantina.model.POJO.MetodoDePagamento;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author artur-paranayba
 */
public class AnchorPaneAlteracaoDeCreditoClienteController implements Initializable {



    @FXML
    private Label labelNomeClienteCrd;

    @FXML
    private Label labelSaldoAtual;

    @FXML
    private Label labelValorAposDeposito;

    @FXML
    private TextField textFieldValorDepositado;
            
    @FXML
    private ComboBox<MetodoDePagamento> comboBoxMtdPagamento;

    @FXML
    private Button btnAddCredito;

    @FXML
    private Button btnCancelar;
        
    @FXML
    private Button btnCalcular;
    
    
    
    public Stage dialogStage;
    public boolean btnAddCreditoClicked = false;
    public Cliente cliente;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // listarMtdPagamento();
    }    
    
    public Stage getDialogStage() {
        return dialogStage;
    }

    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    
    public boolean isBtnAddCreditoClicked() {
        return btnAddCreditoClicked;
    }

   
    public void setBtnAddCreditoClicked(boolean btnAddCreditoClicked) {
        this.btnAddCreditoClicked = btnAddCreditoClicked;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.labelNomeClienteCrd.setText(cliente.getNome());
        this.labelSaldoAtual.setText(Double.toString(cliente.getSaldo()));
        
    }
   
    
    @FXML
    public void btnCalcular(){
        
        double deposito = Double.valueOf(textFieldValorDepositado.getText());
        double valorAtual = Double.valueOf(labelSaldoAtual.getText());
        double valorNovoSaldo = deposito + valorAtual;
        
       try{
           if (textFieldValorDepositado != null){
            labelValorAposDeposito.setText(Double.toString(valorNovoSaldo));
           
                if(valorNovoSaldo < 0){
                   labelValorAposDeposito.setTextFill(Color.RED);
                } else if (valorNovoSaldo > 0){
                   labelValorAposDeposito.setTextFill(Color.GREEN); 
                }
               
               
            }
           
           //tratar este erro!
       } catch (NumberFormatException ex){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Ops!");
               alert.setHeaderText("Por favor, digite um valor para realizar o cálculo.");
               alert.showAndWait();
               }
//        if (textFieldValorDepositado != null){
//       
//           labelValorAposDeposito.setText(Double.toString(
//                   deposito + valorAtual
//           ));
//        } else {
//           Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Ops!");
//            alert.setHeaderText("Por favor, digite um valor para realizar o cálculo.");
//            alert.show();
           
       
    }
     
    
    @FXML
    public void btnAddCredito(){
    
    cliente.setSaldo(Double.valueOf(labelValorAposDeposito.getText()));
    btnAddCreditoClicked = true;
    dialogStage.close();
    
    }
    
    @FXML
    public void btnCancelar(){
        
        dialogStage.close();
    }
    

}
    

