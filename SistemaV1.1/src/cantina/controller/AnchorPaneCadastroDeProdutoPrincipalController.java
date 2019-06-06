
package cantina.controller;

import cantina.model.POJO.Produto;
import cantina.model.dao.ProdutoDAO;
import cantina.model.database.Database;
import cantina.model.database.DatabaseFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author artur-paranayba
 */
public class AnchorPaneCadastroDeProdutoPrincipalController implements Initializable {

    @FXML
    private TableView<Produto> tableViewProdutos;

    @FXML
    private TableColumn<Produto, String> tableColumnProdutoNome;
    
    @FXML
    private TableColumn<Produto, Integer> tableColumnProdutoQtd;

    @FXML
    private TableColumn<Produto, Double> tableColumnProdutoPreco;

    @FXML
    private Label labelProdutoCodigo;
    
    @FXML
    private Label labelProdutoNome;
    
    @FXML
    private Label labelProdutoPreco;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnRemover;
    
    private List<Produto> listProdutos;
    private ObservableList<Produto> observableListProdutos;
    

    //manipulação de dados do banco
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoDAO.setConnection(connection);
        carregarTableViewProduto();

        //Listener acionado quando é selecionado o cliente na tableView
        tableViewProdutos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionaProduto(newValue)
        );
    }    
    public void carregarTableViewProduto() {
        tableColumnProdutoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnProdutoPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tableColumnProdutoQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        

        listProdutos = produtoDAO.listar();

        observableListProdutos = FXCollections.observableArrayList(listProdutos);

        tableViewProdutos.setItems(observableListProdutos);
    }
    
    public void selecionaProduto(Produto produto){
        
        if(produto != null){
            labelProdutoCodigo.setText(String.valueOf(produto.getCodProduto()));
            labelProdutoNome.setText(produto.getNome());
            labelProdutoPreco.setText(String.valueOf(produto.getPreco()));
  
        }else{
            labelProdutoCodigo.setText("");
            labelProdutoNome.setText("");
            labelProdutoPreco.setText("");
        }
    }

    
        public void btnInserir() throws IOException {
        Produto produto = new Produto();
        boolean btnConfirmarClicked = showAnchorPaneCadastroDeProdutoDialog(produto);
        if (btnConfirmarClicked) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso no Cadastro");
            alert.setHeaderText("Sucesso!");
            alert.setContentText(produto.getNome() + " cadastrado(a)!");
            alert.show();
            produtoDAO.inserir(produto);
            carregarTableViewProduto();
        }
    }
        
        public void btnAlterar() throws IOException {
        Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();
            if (produto != null) {
                boolean btnConfirmarClicked = showAnchorPaneAlteracaoDeProdutoDialog(produto);
                if (btnConfirmarClicked) {
                    produtoDAO.alterar(produto);
                    carregarTableViewProduto();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ops!");
                alert.setHeaderText("Por favor, selecione um produto na tabela.");
                alert.show();
            }
        }
        
        public void btnRemover() throws IOException {
        Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();
        if (produto != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Você realmente deseja remover "+ produto.getNome().toUpperCase() +" ? Esta é IRREVERSÍVEL!",
                    ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmação de Remoção");
            alert.setHeaderText("Atenção!");

            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.get() == ButtonType.YES) {
                produtoDAO.remover(produto);
                carregarTableViewProduto();
            }

            carregarTableViewProduto();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ops!");
            alert.setHeaderText("Por favor, selecione um produto na tabela.");
            alert.show();
        }
    }

       
    
      public boolean showAnchorPaneCadastroDeProdutoDialog(Produto produto) throws IOException {
        //este parte coloca na memoria a pagina de cadastro de clientes e gera a pagina
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AnchorPaneCadastroDeProdutoController.class.getResource("/cantina/view/AnchorPaneCadastroDeProduto.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Produtos");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //Setando o cliente no controller
        AnchorPaneCadastroDeProdutoController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setProduto(produto);

        //Monstra o Dialog e espera até que o usuario feche
        dialogStage.showAndWait();

        return controller.isBtnConfirmarClicked();

    }      
        
       public boolean showAnchorPaneAlteracaoDeProdutoDialog(Produto produto) throws IOException {
        //este parte coloca na memoria a pagina de cadastro de clientes e gera a pagina
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AnchorPaneAlteracaoDeProdutoController.class.getResource("/cantina/view/AnchorPaneAlteracaoDeProduto.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Alterar Produto");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //Setando o cliente no controller
        AnchorPaneAlteracaoDeProdutoController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setProduto(produto);

        //Monstra o Dialog e espera até que o usuario feche
        dialogStage.showAndWait();

        return controller.isBtnConfirmarClicked();

    }
}
