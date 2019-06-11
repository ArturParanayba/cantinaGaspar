package cantina.model.POJO;

import java.io.Serializable;

public class ItemDeVenda implements Serializable {

    private int codItemDeVenda;
    private int quantidade;
    private double valor;
    private Produto produto;
    private Venda venda;

    public ItemDeVenda() {
    }

    public int getCodItemDeVenda() {
        return codItemDeVenda;
    }

    public void setCodItemDeVenda(int codItemDeVenda) {
        this.codItemDeVenda = codItemDeVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    
}
