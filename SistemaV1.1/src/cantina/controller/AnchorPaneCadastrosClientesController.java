
package cantina.controller;

import cantina.model.POJO.Cliente;
import java.net.URL;
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
public class AnchorPaneCadastrosClientesController implements Initializable {

      @FXML
    private TableView<?> tableViewClientes;

    @FXML
    private TableColumn<?, ?> tabeColumnClienteCodigo;

    @FXML
    private TableColumn<?, ?> tabeColumnClienteNome;

    @FXML
    private Label labelClienteCodigo;

    @FXML
    private Label labelClienteTelefone;

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
    
    private List<Cliente> listCLientes;
    private ObservableList<Cliente>observableListClientes;
    
    //manipulação de dados do banco
    
    
            
    @Override
            
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
