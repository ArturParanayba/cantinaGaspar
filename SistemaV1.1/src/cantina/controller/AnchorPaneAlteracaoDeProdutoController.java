
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

    
public class AnchorPaneAlteracaoDeProdutoController implements Initializable {

    @FXML
    private Label labelNomeProduto;

    @FXML
    private Label labelPreco;

    @FXML
    private TextField textFieldNomeProduto;

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
        this.textFieldNomeProduto.setText(produto.getNome());
        this.textFieldPreco.setText(Double.toString(produto.getPreco()).replace(".", ","));
    }
    
    
    @FXML
    public void btnConfirmar(){
        produto.setNome(textFieldNomeProduto.getText());
        produto.setPreco(Double.valueOf(textFieldPreco.getText().replace(",", ".")));
        
        btnConfirmarClicked = true;
        dialogStage.close();
    }
    
    public void btnCancelar(){
        
        if(textFieldNomeProduto != null){
            Alert alert = new Alert(Alert.AlertType.WARNING,
            "Verificamos que existem dados preenchidos que ainda não foram salvos.\n Você deseja realmente cancelar esta operação?",
                    ButtonType.YES, ButtonType.NO);
            alert.setTitle("Atenção!");
            Optional<ButtonType> resultado = alert.showAndWait();
            
            if(resultado.get() == ButtonType.YES){
              dialogStage.close();  
            }     
        }
    }
}
