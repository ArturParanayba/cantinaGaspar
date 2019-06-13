
package cantina.controller;


import cantina.model.POJO.ItemDeVenda;
import cantina.model.POJO.Venda;
import cantina.model.dao.VendaDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 


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
    
    @FXML
    private Button btnConsultarProdutos;

    @FXML
    private Button btnConsultarVendas;
    
    @FXML
    private Button btnVerificarEstoque;
    
    boolean btnMenuPrincipalClicked = false;
    boolean btnRealizarVendaClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //qtdValorVendasRealizadas.setText(String.valueOf(VendaDAO.buscarVendasRealizadasHoje()));
    }   
    
    
    
    public void abrirTelaCadastroCliente() throws IOException{
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/cantina/view/AnchorPaneCadastrosDeClientesPrincipal.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void abrirTelaCadastroDeProduto() throws IOException{
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/cantina/view/AnchorPaneCadastroDeProdutoPrincipal.fxml"));
        anchorPane.getChildren().setAll(a);
    } 
    
    public void abrirTelaCadastroDeVendas() throws IOException{
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/cantina/view/AnchorPaneProcessoDeVenda.fxml"));
        anchorPane.getChildren().setAll(a);
    }  
    public void abrirTelaRelatoriosQuantidadeProdutos() throws IOException{
    AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/cantina/view/AnchorPaneRelatoriosQuantidadeProdutos.fxml"));
    anchorPane.getChildren().setAll(a);
    }  
    
}
