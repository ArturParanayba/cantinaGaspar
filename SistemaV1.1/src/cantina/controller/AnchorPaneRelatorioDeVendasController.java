/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.controller;

import cantina.model.POJO.Venda;
import cantina.model.dao.VendaDAO;
import cantina.model.database.Database;
import cantina.model.database.DatabaseFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author artur-paranayba
 */
public class AnchorPaneRelatorioDeVendasController implements Initializable {

    @FXML
    private TableView<Venda> tableViewVendas;
    
    @FXML
    private TableColumn<Venda, Integer> tableColumnVendaCodigo;

    @FXML
    private TableColumn<Venda, String> tableColumnVendaCliente;

    @FXML
    private TableColumn<Venda, Double> tableColumnProdutoValor;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button btnMenuPrincipal;

    @FXML
    private Button btnImprimir;
    
    private List<Venda> listProdutos;
    private ObservableList<Venda> observableListProdutos;
    
    // conexão com o db
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        vendaDAO.setConnection(connection);
        carregarTableViewVendas();
    }    
    
        public void carregarTableViewVendas(){
        tableColumnVendaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnVendaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tableColumnProdutoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        
        listProdutos = vendaDAO.listar();
        
        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        tableViewVendas.setItems(observableListProdutos);
    }
        
    public void abrirTelaMenuPrincipal() throws IOException{
    VBox a = (VBox) FXMLLoader.load(getClass().getResource("/cantina/view/VBoxTelaPrincipal.fxml"));
    anchorPane.getChildren().setAll(a);
    }
    
}
