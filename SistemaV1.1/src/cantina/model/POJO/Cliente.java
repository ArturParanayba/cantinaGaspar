package cantina.model.POJO;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int codCliente;
    private String nome;
    public String email;
    public double saldo;
    

    public Cliente(){
    }
    
    public Cliente(int codCliente, String nome, String email, double saldo) {
        this.codCliente = codCliente;
        this.nome = nome;
        this.email = email;
        this.saldo = saldo;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

  public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    

    @Override
    public String toString() {
        return this.nome;
    }

       
}
