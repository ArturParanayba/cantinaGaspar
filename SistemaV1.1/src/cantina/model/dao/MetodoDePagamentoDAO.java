package cantina.model.dao;

import cantina.model.POJO.MetodoDePagamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;

public class MetodoDePagamentoDAO {
    
    //Conexão com o banco
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    
    public List<MetodoDePagamento> listarMtdPagamento(){
        //colocar SQL que faz a consulta na tabela de metodos de pagamento
        String sql = "SELECT descricao FROM metododepagamento";
        List<MetodoDePagamento> retorno = new ArrayList<>();
        try{
        PreparedStatement stmt = connection.prepareStatement(sql);    
        ResultSet resultado = stmt.executeQuery();
            while(resultado.next()) {
            MetodoDePagamento mtdDePgto = new MetodoDePagamento();
            mtdDePgto.setIdMetodoPagamento(resultado.getInt("codMetodoDePagamento"));
            mtdDePgto.setDescricao("descricao");
            retorno.add(mtdDePgto);
            }
        }catch(SQLException ex) {
            System.out.println("Erro ao listar Metodo de Pagamento. Verificar SQL");
            ex.printStackTrace();
        }
        return retorno;
    }
}
