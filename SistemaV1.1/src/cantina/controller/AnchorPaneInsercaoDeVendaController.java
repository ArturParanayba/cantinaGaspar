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
import javafx.scene.control.Alert;
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
    private ComboBox comboBoxVendaProduto;
    
    @FXML
    private TableView<ItemDeVenda> tableViewItensDeVenda;
    
    @FXML
    private TableColumn<ItemDeVenda, Produto> tableColumnItemDeVendaProduto;
    
    @FXML
    private TableColumn<ItemDeVenda, Integer> tableColumnItemDeVendaQuantidade;

    @FXML
    private TableColumn<ItemDeVenda, Double> tableColumnItemDeVendaValor;

    @FXML
    private TextField textFieldItemDeVendaQuantidade;

    @FXML
    private Button btnAdicionar;

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
    private Cliente cliente;
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
       mtdDePagamento = FXCollections.observableArrayList("Cartão", "Dinheiro", "Transferência", "Saldo em Conta");
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
        comboBoxVendaProduto.setItems(observableListProdutos);
        
    }
    
    @FXML
    public void btnAdicionar(){
        Produto produto;
        ItemDeVenda itemDeVenda = new ItemDeVenda();
        if(comboBoxVendaProduto.getSelectionModel().getSelectedItem() != null){
           produto = (Produto) comboBoxVendaProduto.getSelectionModel().getSelectedItem();
           if(produto.getQuantidade() >= Integer.parseInt(textFieldItemDeVendaQuantidade.getText())){
               itemDeVenda.setProduto((Produto) comboBoxVendaProduto.getSelectionModel().getSelectedItem());
               itemDeVenda.setQuantidade(Integer.parseInt(textFieldItemDeVendaQuantidade.getText()));
               itemDeVenda.setValor(itemDeVenda.getProduto().getPreco() * itemDeVenda.getQuantidade());
               venda.getItensDeVenda().add(itemDeVenda);
               venda.setValor(venda.getValor() + itemDeVenda.getValor());
               observableListItensDeVenda = FXCollections.observableArrayList(venda.getItensDeVenda());
               tableViewItensDeVenda.setItems(observableListItensDeVenda);
               textFieldValor.setText(String.format("R$ " + "%.2f", venda.getValor()));
           }else {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText("Erro ao selecionar produto!");
               alert.setContentText("O estoque não possuí esta quantidade de produto.\n Por favor, verificar antes de realizar a venda.");
               alert.show();
           }    
        }
    }
    
    @FXML
    public void btnConfirmar(){
        
        
        
        if (validarEntradaDeDados()) {
            venda.setCliente((Cliente) comboBoxVendaClientes.getSelectionModel().getSelectedItem());
            venda.setMetodoDePagamento(comboBoxMetodoDePagamento.getSelectionModel().getSelectedItem().toString());
            verificarMetodoDePagamento();
            venda.setData(datePickerVendaData.getValue());
            buttonConfirmarClicked = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Venda cadastrada com sucesso!");
            alert.setContentText("A venda nº" + venda.getCodVenda() + " no valor de: " + venda.getValor() +",\n para o(a) " + venda.getCliente() + " foi realizada com sucesso!" );
            alert.show();

        }
    }
    
    public void btnCancelar(){
        getDialogStage().close();
    }
    
    public boolean validarEntradaDeDados(){
        String errorMessage = "";
        
        if(comboBoxVendaClientes.getSelectionModel().getSelectedItem() == null){
                errorMessage += "Nenhum cliente foi selecionado.\n";
        }
        if(datePickerVendaData.getValue() == null) {
                errorMessage += "Nenhuma data foi selecionada.\n";
        }
        if(comboBoxVendaClientes.getSelectionModel().getSelectedItem() == null){
                errorMessage += "Nenhum metodo de pagamento foi selecionado.\n";
        }
        if(observableListItensDeVenda == null){
                errorMessage += "Nenhum item de venda foi selecionado.\n";
        }
        if(errorMessage.length() == 0){
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Opa, parece que temos um problema aqui...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        } 
 
    }
    public void verificarMetodoDePagamento() {
        String metodoDePagamento = comboBoxMetodoDePagamento.getSelectionModel().getSelectedItem().toString();
        if (metodoDePagamento.contentEquals("Saldo em Conta")) {
            double valorAlterado = venda.getCliente().getSaldo() - venda.getValor();
            String valorFormatado = String.format("R$ " + "%.2f", valorAlterado);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Atenção!");
            alert.setContentText("Esta venda está sendo realizada com o saldo em conta. Sendo assim o credito do(a)" + venda.getCliente() + "\n passará a ser de: " + valorFormatado);
            alert.showAndWait();
            alterarSaldo();
        }
    }
    
        public void alterarSaldo() {
           int codCliente = venda.getCliente().getCodCliente();
            double valorADepositar = venda.getCliente().getSaldo() - venda.getValor();
            clienteDAO.alterarSaldoNaVenda(valorADepositar, codCliente);
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
