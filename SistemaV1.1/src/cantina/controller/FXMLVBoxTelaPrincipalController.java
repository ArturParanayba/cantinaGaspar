
package cantina.controller;


import cantina.model.POJO.Venda;
import java.io.IOException;
import java.net.URL;
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
    private Button btnRealizarVenda;

    @FXML
    private Label labelVendasRealizadas;

    @FXML
    private Label qtdVendasRealizadas;

    @FXML
    private Label labelValorVendasRealizadas;

    @FXML
    private Label qtdValorVendasRealizadas;

    @FXML
    private Label labelClientesSaldoNegativo;

    @FXML
    private Label qtdClientesSaldoNegativo;

    @FXML
    private Label labelValorSaldoNegativo;

    @FXML
    private Label qtdSaldoNegativo;
    
    boolean btnMenuPrincipalClicked = false;
    boolean btnRealizarVendaClicked = false;
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
    
    public void abrirTelaCadastroDeVendas() throws IOException{
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/cantina/view/AnchorPaneProcessoDeVenda.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void abrirTelaInsercaoDeVendas() throws IOException{
        Venda venda = new Venda();
        btnRealizarVendaClicked = showAnchorPaneInsercaoDeVenda(venda);
    }
    
        public boolean showAnchorPaneInsercaoDeVenda(Venda venda) throws IOException {
        //este parte coloca na memoria a pagina de cadastro de clientes e gera a pagina
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AnchorPaneInsercaoDeVendaController.class.getResource("/cantina/view/AnchorPaneInsercaoDeVenda.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registrar nova Venda");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //Setando o cliente no controller
        AnchorPaneInsercaoDeVendaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVenda(venda);

        //Monstra o Dialog e espera até que o usuario feche
        dialogStage.showAndWait();

        return btnRealizarVendaClicked;

    }
   
    
    
    
}
