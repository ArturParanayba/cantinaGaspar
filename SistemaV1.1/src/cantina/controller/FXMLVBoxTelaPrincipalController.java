
package cantina.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;


public class FXMLVBoxTelaPrincipalController implements Initializable {

    @FXML
    private MenuItem menuItemCadastrosClientes;
    @FXML
    private MenuItem menuItemProcessosVendas;
    @FXML
    private MenuItem menuItemCadastroProduto;  
    @FXML
    private MenuItem menuItemGraficosVendasPorMes;
    @FXML
    private MenuItem menuItemRelatoriosQuantidadeProdutosEstoque;
    @FXML
    private AnchorPane anchorPane;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    public void abrirTelaCadastroCliente() throws IOException{
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/cantina/view/AnchorPaneCadastrosDeClientesPrincipal.fxml"));
            anchorPane.getChildren().setAll(a);
    }
    
    public void abrirTelaCadastroDeProduto() throws IOException{
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/cantina/view/AnchorPaneCadastroDeProdutoPrincipal.fxml"));
            anchorPane.getChildren().setAll(a);
    } 
            
    
    
}
