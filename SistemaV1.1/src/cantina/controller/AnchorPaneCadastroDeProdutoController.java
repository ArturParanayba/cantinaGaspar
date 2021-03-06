
package cantina.controller;

import cantina.model.POJO.Produto;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author artur-paranayba
 */
public class AnchorPaneCadastroDeProdutoController implements Initializable {



    @FXML
    private Label labelNomeProduto;

    @FXML
    private Label labelPreco;

    @FXML
    private Label labelQtd;

    @FXML
    private TextField textFieldNomeProduto;

    @FXML
    private TextField textFieldQtdProduto;

    @FXML
    private TextField textFieldPreco;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnCancelar;

    
    public Stage dialogStage;
    public boolean btnConfirmarClicked = false;
    public Produto produto;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Stage getDialogStage() {
        return dialogStage;
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isBtnConfirmarClicked() {
        return btnConfirmarClicked;
    }


    public void setBtnConfirmarClicked(boolean btnConfirmarClicked) {
        this.btnConfirmarClicked = btnConfirmarClicked;
    }

     Produto getProduto() {
        return produto;
    }


    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    
    @FXML
    public void btnConfirmar(){
        produto.setNome(textFieldNomeProduto.getText());
        produto.setQuantidade(Integer.parseInt(textFieldQtdProduto.getText()));
        produto.setPreco(Double.valueOf(textFieldPreco.getText().replace(",", ".")));
        
        btnConfirmarClicked = true;
        dialogStage.close();
    }
    
    public void btnCancelar(){
              dialogStage.close();  
    }
}
