package cantina.controller;

import cantina.model.POJO.Cliente;

import cantina.model.dao.ClienteDAO;
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
public class AnchorPaneCadastrosDeClientesPrincipalController implements Initializable {

    @FXML
    private TableView<Cliente> tableViewClientes;

    @FXML
    private TableColumn<Cliente, String> tableColumnClienteNome;

    @FXML
    private TableColumn<Cliente, Double> tableColumnClienteSaldo;

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

    @FXML
    private Button btnCredito;
    
    @FXML
    private Button btnMenuPrincipal;

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
    public void carregarTableViewCliente() {
        tableColumnClienteSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        listClientes = clienteDAO.listar();

        observableListClientes = FXCollections.observableArrayList(listClientes);

        tableViewClientes.setItems(observableListClientes);
    }

    public void selecionaCliente(Cliente cliente) {

        //condição para deixar tela em branco caso nada esteja selecionado
        if (cliente != null) {
            labelClienteCodigo.setText(String.valueOf(cliente.getCodCliente()));
            labelClienteNome.setText(cliente.getNome());
            labelClienteEmail.setText(cliente.getEmail());
            labelClienteSaldo.setText(Double.toString(cliente.getSaldo()));
        } else {
            labelClienteCodigo.setText("");
            labelClienteNome.setText("");
            labelClienteEmail.setText("");
            labelClienteSaldo.setText("");
        }
    }

    @FXML
    public void btnInserir() throws IOException {
        Cliente cliente = new Cliente();
        boolean btnConfirmarClicked = showAnchorPaneCadastroDeClientesDialog(cliente);
        if (btnConfirmarClicked) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso no Cadastro");
            alert.setHeaderText("Sucesso!");
            alert.setContentText(cliente.getNome() + " cadastrado(a)!");
            alert.show();
            clienteDAO.inserir(cliente);
            carregarTableViewCliente();
        }
    }

    @FXML
    public void btnAlterar() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            boolean btnConfirmarClicked = showAnchorPaneAlteracaoCadastroClienteDialog(cliente);
            if (btnConfirmarClicked) {
                clienteDAO.alterar(cliente);
                carregarTableViewCliente();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ops!");
            alert.setHeaderText("Por favor, selecione um cliente na tabela.");
            alert.show();
        }
    }

    @FXML
    public void btnRemover() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Você realmente deseja remover "+ cliente.getNome() +" ? Esta é IRREVERSÍVEL!",
                    ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmação de Remoção");
            alert.setHeaderText("Atenção!");

            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.get() == ButtonType.YES) {
                clienteDAO.remover(cliente);
                carregarTableViewCliente();
            }

            //carregarTableViewCliente();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ops!");
            alert.setHeaderText("Por favor, selecione um cliente na tabela.");
            alert.show();
        }
    }

    @FXML
    public void btnCredito() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        //InsercaoDeCredito insercaoDeCredito = new InsercaoDeCredito();
        if (cliente != null) {
            boolean btnAddCreditoClicked = showAnchorPaneAlteracaoDeCreditoClienteController(cliente);
            if (btnAddCreditoClicked) {
                clienteDAO.inserirSaldo(cliente);
                carregarTableViewCliente();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText("Depósito de crédito de " + cliente.getNome() + "\nno valor de: R$" + cliente.getValorDeDepositoCredito() + " foi realizado com sucesso!");
            alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ops!");
            alert.setHeaderText("Por favor, selecione um cliente na tabela.");
            alert.show();

        }

    }
    
    public void btnMenuPrincipal() throws IOException{
        Stage stage = (Stage) btnMenuPrincipal.getScene().getWindow(); 
        stage.close(); 
        
    }

    //ALTERAÇÃO DO CRÉDITO
    public boolean showAnchorPaneAlteracaoDeCreditoClienteController(Cliente cliente) throws IOException {
        //este parte coloca na memoria a pagina de cadastro de clientes e gera a pagina
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AnchorPaneAlteracaoDeCreditoClienteController.class.getResource("/cantina/view/AnchorPaneAlteracaoDeCreditoCliente.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Operação de Crédito");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //Setando o cliente no controller
        AnchorPaneAlteracaoDeCreditoClienteController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);

        //Monstra o Dialog e espera até que o usuario feche
        dialogStage.showAndWait();

        return controller.isBtnAddCreditoClicked();

    }

    // INSERIR CADASTRO CLIENTE
    public boolean showAnchorPaneCadastroDeClientesDialog(Cliente cliente) throws IOException {
        //este parte coloca na memoria a pagina de cadastro de clientes e gera a pagina
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AnchorPaneCadastroDeClientesController.class.getResource("/cantina/view/AnchorPaneCadastroDeClientes.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Clientes");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //Setando o cliente no controller
        AnchorPaneCadastroDeClientesController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);

        //Monstra o Dialog e espera até que o usuario feche
        dialogStage.showAndWait();

        return controller.isBtnConfirmarClicked();

    }
    //ALTERAÇÃO CADASTRO CLIENTE

    public boolean showAnchorPaneAlteracaoCadastroClienteDialog(Cliente cliente) throws IOException {
        //este parte coloca na memoria a pagina de cadastro de clientes e gera a pagina
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AnchorPaneAlteracaoCadastroClienteController.class.getResource("/cantina/view/AnchorPaneAlteracaoCadastroCliente.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Alteração de Cadastro");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //Setando o cliente no controller
        AnchorPaneAlteracaoCadastroClienteController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);

        //Monstra o Dialog e espera até que o usuario feche
        dialogStage.showAndWait();

        return controller.isBtnConfirmarClicked();
    }
}
