package cantina.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import cantina.model.POJO.Cliente;

public class ClienteDAO {
//conexão com o banco 
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
//inicio dos metodos CRUD
    public boolean inserir(Cliente cliente) {
        //alterar nome da tabela de clientes de acordo com o banco!
        String sql = "INSERT INTO clientes(nome, email, saldo) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setDouble(3, cliente.getSaldo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir Cliente. Verificar SQL");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Cliente cliente) {
        //alterar nome da tabela de clientes de acordo com o banco!
        String sql = "UPDATE clientes SET nome=?, email=?, saldo=? WHERE codCliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            //stmt.setInt(0, cliente.getCodCliente());
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setDouble(3, cliente.getSaldo());
            stmt.setInt(4, cliente.getCodCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao alterar Cliente. Verificar SQL");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean remover(Cliente cliente) {
        //alterar nome da tabela de clientes de acordo com o banco!
        String sql = "DELETE FROM clientes WHERE codCliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getCodCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir Remover. Verificar SQL");
            ex.printStackTrace();
            return false;
        }
    }

    public List<Cliente> listar() {
        //alterar nome da tabela de clientes de acordo com o banco!
        String sql = "SELECT * FROM clientes";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setCodCliente(resultado.getInt("codCliente"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setSaldo(resultado.getDouble("saldo"));
                retorno.add(cliente);
            }
        } catch (SQLException ex) {
            //System.err.println("Erro ao listar clientes");
            System.out.println("Erro ao listar Cliente. Verificar SQL");
            ex.printStackTrace();
     
        }
        return retorno;
    }

    public Cliente buscar(Cliente cliente) {
        //alterar nome da tabela de clientes de acordo com o banco!
        String sql = "SELECT * FROM clientes WHERE codCliente=?";
        Cliente retorno = new Cliente();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getCodCliente());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                cliente.setNome(resultado.getString("nome"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setSaldo(resultado.getDouble("saldo"));
                retorno = cliente;
            }
        } catch (SQLException ex) {
            System.out.println("Erro na busca. Verificar SQL");
            ex.printStackTrace();
        }
        return retorno;
    }
    
    public boolean inserirSaldo(Cliente cliente){
        
        String sql = "INSERT INTO clientes(saldo) WHERE codCliente=?, VALUES(?)";
                try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1,cliente.getSaldo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir Saldo. Verificar SQL");
            ex.printStackTrace();
            return false;
        }
    }
}
