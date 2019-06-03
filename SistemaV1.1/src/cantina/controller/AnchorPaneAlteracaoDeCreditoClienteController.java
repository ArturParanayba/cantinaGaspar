/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.controller;

import cantina.model.POJO.Cliente;
import cantina.model.POJO.MetodoDePagamento;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Currency;
import java.util.Locale;


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
    private ComboBox comboBoxMtdPagamento;

    @FXML
    private Button btnAddCredito;

    @FXML
    private Button btnCancelar;
        
    @FXML
    private Button btnCalcular;
    
    
    
    public Stage dialogStage;
    public boolean btnAddCreditoClicked = false;
    public Cliente cliente;
    public MetodoDePagamento metodoDePagamento;
    
   ObservableList<String> mtdDePagamento = FXCollections.observableArrayList("Cartão", "Dinheiro", "Transferência");   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        //comboBoxMtdPagamento.setValue("Selecione");
        comboBoxMtdPagamento.setItems(mtdDePagamento);
        /*comboBoxMtdPagamento.getItems().add(1, "Cartão");
        comboBoxMtdPagamento.getItems().add(2, "Dinheiro");
        comboBoxMtdPagamento.getItems().add(3, "Transferencia"); */
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
        this.labelSaldoAtual.setText(Double.toString(cliente.getSaldo()).replace(".", ","));
        
    }
   
    @FXML
    public void btnCalcular(){
        
        //Logica da adição do crédito
        double deposito = Double.valueOf(textFieldValorDepositado.getText().replaceAll(",","."));
        double valorAtual = Double.valueOf(labelSaldoAtual.getText().replace(",", "."));
        double valorNovoSaldo = deposito + valorAtual;
        //String cbox = comboBoxMtdPagamento.getValue().toString();
        
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setGroupingUsed(true);
        nf.setMinimumFractionDigits(2);
        String SaldoEmReal = nf.format(valorNovoSaldo); 


        
                if (textFieldValorDepositado != null){
                    //esta linha coloca o valor em R$ com virgula
                    labelValorAposDeposito.setText((SaldoEmReal));
                    //labelValorAposDeposito.setText(Double.toString(valorNovoSaldo));         
                        if(valorNovoSaldo < 0){
                            labelValorAposDeposito.setTextFill(Color.RED);
                        } else if (valorNovoSaldo > 0){
                            labelValorAposDeposito.setTextFill(Color.GREEN); 
                        }              
                }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ops!");
                alert.setHeaderText("Por favor, digite um valor para realizar o cálculo.");
                alert.show();
                }
            
                
    }
     
    
    @FXML
    public void btnAddCredito() throws ParseException{
        
    //coloca valor para double   
    double saldoDepositado =   Double.parseDouble(labelValorAposDeposito.getText().replace(",", "."));
    cliente.setSaldo(saldoDepositado);
    
    //pega a string do método de pagamento
   //String metodo = comboBoxMtdPagamento.getSelectionModel().getSelectedItem().toString();
        System.out.println(comboBoxMtdPagamento.getValue().toString());    
    metodoDePagamento.setMetodoDePagamento(comboBoxMtdPagamento.getValue().toString());
    
    btnAddCreditoClicked = true;
    dialogStage.close();
    //carregarTableViewCliente();
    }
    
    @FXML
    public void btnCancelar(){
        
        dialogStage.close();
    }
    

}
    

