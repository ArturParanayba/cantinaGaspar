/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.controller;

import cantina.model.POJO.ItemDeVenda;
import cantina.model.POJO.Produto;
import cantina.model.POJO.Venda;
import cantina.model.dao.ItemDeVendaDAO;
import cantina.model.dao.ProdutoDAO;
import cantina.model.dao.VendaDAO;
import cantina.model.database.Database;
import cantina.model.database.DatabaseFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class AnchorPaneProcessoDeVendaController implements Initializable {

    @FXML
    private TableView<Venda> tableViewVendas;

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

    @FXML
    private Button btnRemover;
    
    @FXML
    private Button btnMenuPrincipal;

    private List<Venda> listVendas;
    private ObservableList<Venda> observableListVendas;
    
    public Stage dialogStage;
    public boolean btnConsultarVendasClicked = false;

    //manipulação do banco de dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final ItemDeVendaDAO itemDeVendaDAO = new ItemDeVendaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaDAO.setConnection(connection);
        carregarTableViewVendas();

        tableViewVendas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVendas(newValue)
        );

    }

    public void selecionarItemTableViewVendas(Venda venda) {
        if (venda != null) {
            labelVendaCod.setText(String.valueOf(venda.getCodVenda()));;
            labelVendaData.setText(String.valueOf(venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelVendaValorTotal.setText(String.format("R$ " + "%.2f", venda.getValor()));
            labelVendaMtdDePagamento.setText(String.valueOf(venda.getMetodoDePagamento()));
            labelVendaCliente.setText(venda.getCliente().toString());
        } else {
            labelVendaCod.setText("");
            labelVendaData.setText("");
            labelVendaValorTotal.setText("");
            labelVendaMtdDePagamento.setText("");
            labelVendaCliente.setText("");
        }
    }

    public void carregarTableViewVendas() {
        tableColumnVendaCodigo.setCellValueFactory(new PropertyValueFactory<>("codvenda"));
        tableColumnVendaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tableColumnVendaData.setCellValueFactory(new PropertyValueFactory<>("data"));

        listVendas = vendaDAO.listar();

        observableListVendas = FXCollections.observableArrayList(listVendas);
        tableViewVendas.setItems(observableListVendas);

    }
    
        public Stage getDialogStage() {
        return dialogStage;
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean btnConsultarVendasClicked() {
        return btnConsultarVendasClicked;
    }


    public void btnConsultarVendasClicked(boolean btnConsultarVendasClicked) {
        this.btnConsultarVendasClicked = btnConsultarVendasClicked;
    }
    
    @FXML
    public void btnInserir() throws IOException {
        Venda venda = new Venda();
        List<ItemDeVenda> listItensDeVenda = new ArrayList<>();
        venda.setItensDeVenda(listItensDeVenda);
        boolean btnConfirmarClicked = showAnchorPaneInsercaoDeVenda(venda);
        if (btnConfirmarClicked) {
            try {
                connection.setAutoCommit(false);
                vendaDAO.setConnection(connection);
                vendaDAO.inserir(venda);
                itemDeVendaDAO.setConnection(connection);
                produtoDAO.setConnection(connection);
                for (ItemDeVenda listItemDeVenda : venda.getItensDeVenda()) {
                    Produto produto = listItemDeVenda.getProduto();
                    listItemDeVenda.setVenda(vendaDAO.buscarUltimaVenda());
                    itemDeVendaDAO.inserir(listItemDeVenda);
                    produto.setQuantidade(produto.getQuantidade() - listItemDeVenda.getQuantidade());
                    produtoDAO.alterar(produto);
                }
                connection.commit();
                carregarTableViewVendas();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    System.err.println("Erro ao fazer rollback no salvando da venda");
                }
                System.err.println("Erro !!! ");
            }
        }

    }
    
    public void btnRemover() throws IOException, SQLException{
        Venda venda = tableViewVendas.getSelectionModel().getSelectedItem();
        if(venda != null){
            connection.setAutoCommit(false);
            vendaDAO.setConnection(connection);
            itemDeVendaDAO.setConnection(connection);
            produtoDAO.setConnection(connection);
            for(ItemDeVenda listItemDeVenda : venda.getItensDeVenda()){
                Produto produto = listItemDeVenda.getProduto();
                produto.setQuantidade(produto.getCodProduto() + listItemDeVenda.getQuantidade());
                produtoDAO.alterar(produto);
                itemDeVendaDAO.remover(listItemDeVenda);
            }
                vendaDAO.remover(venda);
                connection.commit();
                carregarTableViewVendas();
        } else {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setContentText("Para remover, escolha uma venda na tabela.");
            alert.show();
        }
    }
            
            
    //INSERÇÃO DE VENDA
    public boolean showAnchorPaneInsercaoDeVenda(Venda venda) throws IOException {
        //este parte coloca na memoria a pagina de cadastro de clientes e gera a pagina
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AnchorPaneInsercaoDeVendaController.class.getResource("/cantina/view/AnchorPaneInsercaoDeVenda.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registrar nova Venda");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //Setando o cliente no controller
        AnchorPaneInsercaoDeVendaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVenda(venda);

        //Monstra o Dialog e espera até que o usuario feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

}
