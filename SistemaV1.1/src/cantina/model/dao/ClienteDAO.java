package cantina.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cantina.model.POJO.Cliente;
import cantina.model.POJO.MetodoDePagamento;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        //alterar nome da tabela de clientes de acordo com o banco
        String sql = "INSERT INTO cliente(nome, email, saldo) VALUES(?,?,?)";
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
        String sql = "UPDATE cliente SET nome=?, email=? WHERE codCliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setInt(3, cliente.getCodCliente());
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
            System.err.println("Erro ao inserir Remover. Verificar SQL");
            ex.printStackTrace();
            return false;
        }
    }

    public List<Cliente> listar() {
        //alterar nome da tabela de clientes de acordo com o banco!
        String sql = "SELECT * FROM cliente";
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
            System.err.println("Erro ao listar Cliente. Verificar SQL");
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
            System.err.println("Erro na busca. Verificar SQL");
            ex.printStackTrace();
        }
        return retorno;
    }
    
    //Verificar como colocar o metodo de pagamento
    public boolean inserirSaldo(Cliente cliente, MetodoDePagamento metodoDePagamento){     
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	LocalDate localDate = LocalDate.now();
        
        try {
            //INSERT
            String insert = "INSERT INTO insercaodecredito (metododepagamento, data, valor, codcliente) VALUES (?,?,?,?)";
            PreparedStatement insertStmt = connection.prepareStatement(insert);
            insertStmt.setString(1, metodoDePagamento.getMetodoDePagamento());
            insertStmt.setDate(2, Date.valueOf(dtf.format(localDate)));
            insertStmt.setDouble(3, cliente.getSaldo());
            insertStmt.setInt(4, cliente.getCodCliente());
            
            insertStmt.execute();
            
            //UPDATE
            String update = "UPDATE cliente SET saldo=? WHERE codCliente=?";
            PreparedStatement updateStmt = connection.prepareStatement(update);
            updateStmt.setDouble(1, cliente.getSaldo());
            updateStmt.setInt(2, cliente.getCodCliente());

            updateStmt.execute();
            
            return true;
            
            }catch(SQLException ex){
              System.err.println("Erro ao inserir Saldo. Verificar SQL");
            ex.printStackTrace();
            return false;      
            }
        

        
       
       
                
    }
    

}
