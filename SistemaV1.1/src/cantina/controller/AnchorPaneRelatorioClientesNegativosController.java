/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.controller;

import cantina.model.POJO.Cliente;
import cantina.model.dao.ClienteDAO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author artur
 */
public class AnchorPaneRelatorioClientesNegativosController implements Initializable {

    @FXML
    private TableView<Cliente> tableViewClientes;
    
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteNome;

    @FXML
    private TableColumn<Cliente, Double> tableColumnSaldo;
    
    @FXML
    private Label labelQtdTotalDevedores;

    @FXML
    private Label labelValorTotalDevedor;

    @FXML
    private Button btnMenuPrincipal;

    @FXML
    private Button btnSaldarDebitos;


    @FXML
    private Button btnImprimir;
    
    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListProdutos;
    
    // conexão com o db
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        carregarTableViewClientes();
        
        labelQtdTotalDevedores.setText(String.valueOf(clienteDAO.contarTotalSaldoNegativo()));
        System.out.println(clienteDAO.contarTotalSaldoDevedor() + " MAIN");
        labelValorTotalDevedor.setText(String.format("R$ " + "%.2f", clienteDAO.contarTotalSaldoDevedor()));
        
    }    
    
        public void carregarTableViewClientes(){
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        
        listClientes = clienteDAO.listarNegativados();
        
        observableListProdutos = FXCollections.observableArrayList(listClientes);
        tableViewClientes.setItems(observableListProdutos);
    }
    
}
    
