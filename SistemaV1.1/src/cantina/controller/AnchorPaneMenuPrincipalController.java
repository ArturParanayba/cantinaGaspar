/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantina.controller;

import cantina.model.dao.ClienteDAO;
import cantina.model.database.Database;
import cantina.model.database.DatabaseFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author artur
 */
public class AnchorPaneMenuPrincipalController implements Initializable {

    @FXML
    private MenuItem menuItemCadastrosClientes;

    @FXML
    private MenuItem menuItemProcessosVendas;

    @FXML
    private MenuItem menuItemCadastroProduto;

    @FXML
    private MenuItem menuItemRelatoriosQuantidadeVendas;

    @FXML
    private MenuItem menuItemRelatoriosQuantidadeProdutosEstoque;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnCadastrarClientes;

    @FXML
    private Button btnCadastrarProdutos;

    @FXML
    private Button btnCadastrarVendas;

    @FXML
    private Button btnVerificarEstoque;

    @FXML
    private Button btnConsultarNegativos;

    @FXML
    private Label labelQtdClientesNegativos;

    @FXML
    private Label labelVlrTotalSaldoNegativo;

    @FXML
    private Label qtdTotalVendas;

    boolean btnMenuPrincipalClicked = false;
    boolean btnRealizarVendaClicked = false;
    int totalDeVendas = 0;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClienteDAO clienteDAO = new ClienteDAO();
        database.conectar();
        labelQtdClientesNegativos.setText(String.valueOf(clienteDAO.contarTotalSaldoNegativo()));
        labelVlrTotalSaldoNegativo.setText(String.format("R$ " + "%.2f", clienteDAO.contarTotalSaldoDevedor()));
    }

    public void abrirTelaCadastroCliente() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AnchorPaneCadastrosDeClientesPrincipal.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    public void abrirTelaCadastroDeProduto() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AnchorPaneCadastroDeProdutoPrincipal.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    public void abrirTelaCadastroDeVendas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AnchorPaneProcessoDeVenda.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    public void abrirTelaRelatoriosQuantidadeProdutos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AnchorPaneRelatoriosQuantidadeProdutos.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    public void abrirTelaRelatoriosVendas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AnchorPaneRelatorioQuantidadeDeVendas.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    public void abrirTelaRelatoriosClientesNegativados() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AnchorPaneRelatorioClientesNegativos.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}
