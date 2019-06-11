/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.controller;

import cantina.model.POJO.Cliente;
import cantina.model.POJO.ItemDeVenda;
import cantina.model.POJO.Produto;
import cantina.model.POJO.Venda;
import cantina.model.dao.ClienteDAO;
import cantina.model.dao.ProdutoDAO;
import cantina.model.database.Database;
import cantina.model.database.DatabaseFactory;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author artur-paranayba
 */
public class AnchorPaneInsercaoDeVendaController implements Initializable {


    @FXML
    private ComboBox comboBoxVendaClientes;

    @FXML
    private ComboBox comboBoxMetodoDePagamento;

    @FXML
    private DatePicker datePickerVendaData;

    @FXML
    private TextField textFieldValor;

    @FXML
    private ComboBox ComboBoxVendaProduto;
    
    @FXML
    private TableView<ItemDeVenda> tableViewVendaItensDeVenda;
    
    @FXML
    private TableColumn<ItemDeVenda, Produto> tableColumnItemDeVendaProduto;
    
    @FXML
    private TableColumn<ItemDeVenda, Integer> tableColumnItemDeVendaQuantidade;

    @FXML
    private TableColumn<ItemDeVenda, Double> tableColumnItemDeVendaValor;

    @FXML
    private TextField textFieldVendaItemDeVendaQuantidade;

    @FXML
    private Button buttonAdicionar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;
    
    private List<Cliente> listClientes;
    private List<Produto> listProdutos;
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<Produto> observableListProdutos;
    private ObservableList<ItemDeVenda> observableListItensDeVenda;
    ObservableList<String> mtdDePagamento; 
    
    //manipulação do BD
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    
    private Venda venda;
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        produtoDAO.setConnection(connection);
        carregarComboBoxMetodoDePagamento();
        carregarComboBoxClientes();
        carregarComboBoxProdutos();
        tableColumnItemDeVendaProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
       tableColumnItemDeVendaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
       tableColumnItemDeVendaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        
    }   
    
    public void carregarComboBoxMetodoDePagamento(){
       mtdDePagamento = FXCollections.observableArrayList("Cartão", "Dinheiro", "Transferência");
       comboBoxMetodoDePagamento.setItems(mtdDePagamento);
    }
    
    
    public void carregarComboBoxClientes(){
        listClientes = clienteDAO.listar();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxVendaClientes.setItems(observableListClientes);
        
    }
    
    public void carregarComboBoxProdutos() {
        listProdutos = produtoDAO.listar();
        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        ComboBoxVendaProduto.setItems(observableListProdutos);
        
    }
    

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }
    
}
