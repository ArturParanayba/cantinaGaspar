package cantina.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import cantina.model.POJO.ItemDeVenda;
import cantina.model.POJO.Produto;
import cantina.model.POJO.Venda;

public class ItemDeVendaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ItemDeVenda itemDeVenda) {
        String sql = "INSERT INTO itensdevenda(quantidade, valor, codProduto, codVenda) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeVenda.getQuantidade());
            stmt.setDouble(2, itemDeVenda.getValor());
            stmt.setInt(3, itemDeVenda.getProduto().getCodProduto());
            stmt.setInt(4, itemDeVenda.getVenda().getCodVenda());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir item de venda");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar(ItemDeVenda itemDeVenda) {
        return true;
    }

    public boolean remover(ItemDeVenda itemDeVenda) {
        String sql = "DELETE FROM itensDeVenda WHERE codItemDeVenda=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeVenda.getCodItemDeVenda());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao remover Item de venda");
            ex.printStackTrace();
            return false;
        }
    }

    public List<ItemDeVenda> listar() {
        String sql = "SELECT * FROM itensDeVenda";
        List<ItemDeVenda> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItemDeVenda itemDeVenda = new ItemDeVenda();
                Produto produto = new Produto();
                Venda venda = new Venda();
                itemDeVenda.setCodItemDeVenda(resultado.getInt("codItemDeVenda"));
                itemDeVenda.setQuantidade(resultado.getInt("quantidade"));
                itemDeVenda.setValor(resultado.getDouble("valor"));
                
                produto.setCodProduto(resultado.getInt("codProduto"));
                venda.setCodVenda(resultado.getInt("codVenda"));
                
                //Obtendo os dados completos do Produto associado ao Item de Venda
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                VendaDAO vendaDAO = new VendaDAO();
                vendaDAO.setConnection(connection);
                venda = vendaDAO.buscar(venda);
                
                itemDeVenda.setProduto(produto);
                itemDeVenda.setVenda(venda);
                
                retorno.add(itemDeVenda);
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar itens de venda");
            ex.printStackTrace();
        }
        return retorno;
    }
    
    public List<ItemDeVenda> listarPorVenda(Venda venda) {
        String sql = "SELECT * FROM itensDeVenda WHERE codVenda=?";
        List<ItemDeVenda> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, venda.getCodVenda());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItemDeVenda itemDeVenda = new ItemDeVenda();
                Produto produto = new Produto();
                Venda v = new Venda();
                itemDeVenda.setCodItemDeVenda(resultado.getInt("codItemDeVenda"));
                itemDeVenda.setQuantidade(resultado.getInt("quantidade"));
                itemDeVenda.setValor(resultado.getDouble("valor"));
                
                produto.setCodProduto(resultado.getInt("codProduto"));
                v.setCodVenda(resultado.getInt("codVenda"));
                
                //Obtendo os dados completos do Produto associado ao Item de Venda
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                itemDeVenda.setProduto(produto);
                itemDeVenda.setVenda(v);
                
                retorno.add(itemDeVenda);
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar por venda");
            ex.printStackTrace();
        }
        return retorno;
    }

    public ItemDeVenda buscar(ItemDeVenda itemDeVenda) {
        String sql = "SELECT * FROM itensDeVenda WHERE cdItemDeVenda=?";
        ItemDeVenda retorno = new ItemDeVenda();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeVenda.getCodItemDeVenda());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Produto produto = new Produto();
                Venda venda = new Venda();
                itemDeVenda.setCodItemDeVenda(resultado.getInt("codItemDeVenda"));
                itemDeVenda.setQuantidade(resultado.getInt("quantidade"));
                itemDeVenda.setValor(resultado.getDouble("valor"));
                
                produto.setCodProduto(resultado.getInt("codProduto"));
                venda.setCodVenda(resultado.getInt("codVenda"));
                
                //Obtendo os dados completos do Cliente associado à Venda
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                VendaDAO vendaDAO = new VendaDAO();
                vendaDAO.setConnection(connection);
                venda = vendaDAO.buscar(venda);
                
                itemDeVenda.setProduto(produto);
                itemDeVenda.setVenda(venda);
                
                retorno = itemDeVenda;
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar item de venda");
            ex.printStackTrace();
        }
        return retorno;
    }
}
