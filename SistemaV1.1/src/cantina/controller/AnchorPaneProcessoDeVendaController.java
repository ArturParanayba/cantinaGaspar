/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.controller;

import cantina.model.POJO.Venda;
import cantina.model.dao.ItemDeVendaDAO;
import cantina.model.dao.ProdutoDAO;
import cantina.model.dao.VendaDAO;
import cantina.model.database.Database;
import cantina.model.database.DatabaseFactory;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author artur-paranayba
 */
public class AnchorPaneProcessoDeVendaController implements Initializable {

    @FXML
    private TableView<?> tableViewVendas;

    @FXML
    private TableColumn<Venda, Integer> tableColumnVendaCodigo;

    @FXML
    private TableColumn<Venda, Venda> tableColumnVendaCliente;

    @FXML
    private TableColumn<Venda, LocalDate> tableColumnVendaData;

    @FXML
    private Label labelVendaCod;

    @FXML
    private Label labelVendaCliente;

    @FXML
    private Label labelVendaValorTotal;

    @FXML
    private Label labelVendaMtdDePagamento;

    @FXML
    private Label labelVendaPrdComprados;

    @FXML
    private Label labelVendaData;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnAlterar;
    
    private List<Venda> listVendas;
    private ObservableList<Venda> observableListVendas;
    
    //manipulação do banco de dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final ItemDeVendaDAO itemDeVendaDAO = new ItemDeVendaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
