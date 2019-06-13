/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.controller;

import cantina.model.POJO.Produto;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author artur
 */
public class AnchorPaneRelatoriosQuantidadeProdutosController implements Initializable {
    @FXML
    private TableView<Produto> tableViewProdutos;
    
    @FXML
    private TableColumn<Produto, String> tableColumnProdutoNome;

    @FXML
    private TableColumn<Produto, Double> tableColumnProdutoPreco;

    @FXML
    private TableColumn<Produto, Integer> tableColumnProdutoQuantidade;

    @FXML
    private Button btnImprimir;
    
    private List<Produto> listProdutos;
    private ObservableList<Produto> observableListProdutos;
    
    // conexão com o db
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoDAO.setConnection(connection);
        carregarTableViewProdutos();
    }    
    
    public void carregarTableViewProdutos(){
        tableColumnProdutoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnProdutoPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tableColumnProdutoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        
        listProdutos = produtoDAO.listar();
        
        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        tableViewProdutos.setItems(observableListProdutos);
    }
    
}
