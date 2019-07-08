package cantina.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import cantina.model.POJO.Cliente;
import cantina.model.POJO.ItemDeVenda;
import cantina.model.POJO.Venda;
import cantina.model.database.Database;
import cantina.model.database.DatabaseFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class VendaDAO {
    
    

    private  Database database = DatabaseFactory.getDatabase("postgresql");
    private  Connection connection1 = database.conectar();
    
    private Connection connection;
    

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Venda venda) {
        String sql = "INSERT INTO vendas(data, valor,metododepagamento, codcliente) VALUES(?,?,?,?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(venda.getData()));
            stmt.setDouble(2, venda.getValor());
            stmt.setString(3, venda.getMetodoDePagamento());
            stmt.setInt(4, venda.getCliente().getCodCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir Venda. Verificar SQL");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Venda venda) {
        String sql = "UPDATE vendasdata, SET data=?, valor=?, metododepagamento=?, codCliente=? WHERE codVenda=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(venda.getData()));
            stmt.setDouble(2, venda.getValor());
            stmt.setString(3, venda.getMetodoDePagamento());
            stmt.setInt(4, venda.getCliente().getCodCliente());
            stmt.setInt(5, venda.getCodVenda());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao alterar Venda. Verificar SQL");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean remover(Venda venda) {
        String sql = "DELETE FROM vendas WHERE codVenda=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, venda.getCodVenda());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao remover venda. Verificar SQL");
            ex.printStackTrace();
            return false;
        }
    }

    public List<Venda> listar() {
        String sql = "SELECT * FROM vendas";
        List<Venda> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Venda venda = new Venda();
                Cliente cliente = new Cliente();
                List<ItemDeVenda> itensDeVenda = new ArrayList();

                venda.setCodVenda(resultado.getInt("codvenda"));
                venda.setData(resultado.getDate("data").toLocalDate());
                venda.setValor(resultado.getDouble("valor"));
                venda.setMetodoDePagamento(resultado.getString("metododepagamento"));
                cliente.setCodCliente(resultado.getInt("codcliente"));

                //Obtendo os dados completos do Cliente associado à Venda
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                cliente = clienteDAO.buscar(cliente);

                //Obtendo os dados completos dos Itens de Venda associados à Venda
                ItemDeVendaDAO itemDeVendaDAO = new ItemDeVendaDAO();
                itemDeVendaDAO.setConnection(connection);
                itensDeVenda = itemDeVendaDAO.listarPorVenda(venda);

                venda.setCliente(cliente);
                venda.setItensDeVenda(itensDeVenda);
                retorno.add(venda);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar Vendas. Verificar SQL");
            ex.printStackTrace();
        }
        
        return retorno;
    }

    public Venda buscar(Venda venda) {
        String sql = "SELECT * FROM vendas WHERE codVenda=?";
        Venda retorno = new Venda();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, venda.getCodVenda());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cliente cliente = new Cliente();
                venda.setCodVenda(resultado.getInt("cdVenda"));
                venda.setData(resultado.getDate("data").toLocalDate());
                venda.setValor(resultado.getDouble("valor"));
                venda.setMetodoDePagamento(resultado.getString("metododepagamento"));
                cliente.setCodCliente(resultado.getInt("cdCliente"));
                venda.setCliente(cliente);
                retorno = venda;
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar Venda. Verificar SQL");
            ex.printStackTrace();
        }
        return retorno;
    }

    public Venda buscarUltimaVenda() {
        String sql = "SELECT max(codVenda) FROM vendas";
        Venda retorno = new Venda();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                retorno.setCodVenda(resultado.getInt("max"));
                return retorno;
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar ultima venda. Verficar SQL");
            ex.printStackTrace();
        }
        return retorno;
    }

    //alterar metodo de acordo com o relatorio pedido pelo cliente
    public Map<Integer, ArrayList> listarQuantidadeVendasPorMes() {
        String sql = "select count(codVenda), extract(year from data) as ano, extract(month from data) as mes from vendas group by ano, mes order by ano, mes";
        Map<Integer, ArrayList> retorno = new HashMap();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getInt("ano"))) {
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("count"));
                    retorno.put(resultado.getInt("ano"), linha);
                } else {
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("count"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            System.err.println("Erro ao listar quantidade de vendas por mes. Verificar SQL");
            ex.printStackTrace();
        }
        return retorno;
    }

    public int buscarVendasRealizadasHoje() {

        int qtdVendas = 0;
        LocalDate date = LocalDate.now();
        Date dateFormated = Date.valueOf(date);
  
        
        try {
            String sql = "SELECT COUNT(*) FROM vendas WHERE data=?";
            PreparedStatement stmt = connection1.prepareStatement(sql);
            stmt.setDate(1, dateFormated);            
            ResultSet resultado = stmt.executeQuery();
            resultado.next();
            int contagem = resultado.getInt(1);
            return contagem;
        } catch (SQLException ex) {
            System.err.println("Erro ao contagem de vendas. Verficar SQL");
            ex.printStackTrace();
        }
            return qtdVendas;
    }

    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}
