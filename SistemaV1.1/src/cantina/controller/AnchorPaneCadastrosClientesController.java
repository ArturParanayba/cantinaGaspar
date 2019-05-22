
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
 * @author artur-paranayba
 */
public class AnchorPaneCadastrosClientesController implements Initializable {

    @FXML
    private TableView<Cliente> tableViewClientes;

    @FXML
    private TableColumn<Cliente,String> tableColumnClienteNome;

    @FXML
    private TableColumn<Cliente,Float> tableColumnClienteSaldo;

    @FXML
    private Label labelClienteCodigo;

    @FXML
    private Label labelClienteSaldo;

    @FXML
    private Label labelClienteEmail;

    @FXML
    private Label labelClienteNome;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnAlterar;
    
    @FXML
    private Button btnRemover;
    
    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;
    
    //manipulação de dados do banco
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
            
    @Override        
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        carregarTableViewCliente();
        
        //Listener acionado quando é selecionado o cliente na tableView
        tableViewClientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionaCliente(newValue)
        );
    }   
    
    //carrega os dados do cliente na tabela
    public void carregarTableViewCliente(){
        tableColumnClienteSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    
        listClientes = clienteDAO.listar();
        
        observableListClientes = FXCollections.observableArrayList(listClientes);
        
        tableViewClientes.setItems(observableListClientes);
    }
    
    public void selecionaCliente(Cliente cliente){
        labelClienteCodigo.setText(String.valueOf(cliente.getCodCliente()));
        labelClienteNome.setText(cliente.getNome());
        labelClienteEmail.setText(cliente.getEmail());
        labelClienteSaldo.setText(Double.toString(cliente.getSaldo()));
        
        
    }

}

    