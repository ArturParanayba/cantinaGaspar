package cantina.model.POJO;

import java.io.Serializable;

public class Produto implements Serializable {

    private int codProduto;
    private String nome;
    private double preco;
    private int quantidade;
    

    public Produto() {
    }

    public Produto(int codProduto, String nome, double preco, int quantidade) {
        this.codProduto = codProduto;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
}
