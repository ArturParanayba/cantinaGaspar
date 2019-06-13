package cantina.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.PSQLException;

public class DatabasePostgreSQL implements Database {
    private Connection connection;

    @Override
    public Connection conectar() {
        try {
           Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5433/cantinadb", "postgres","123");
            return this.connection;
        } catch (ClassNotFoundException e) {
            System.out.println("Conexão falha por falta da biblioteca.");
        } catch (PSQLException ex) {
            //System.out.println("Conexão negada");
            Logger.getLogger(DatabasePostgreSQL.class.getName()).log(Level.SEVERE, "conexão negada", ex);
        } catch (SQLException ex) { 
            Logger.getLogger(DatabasePostgreSQL.class.getName()).log(Level.SEVERE, "Erro no SQL", ex);
        }
        return null;
      
    }

    @Override
    public void desconectar(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabasePostgreSQL.class.getName()).log(Level.SEVERE, "Desconexão impedida por erro no sistema", ex);
        }
    }
}
