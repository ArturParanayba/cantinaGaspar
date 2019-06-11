package cantina.model.POJO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Venda implements Serializable {

    private int codVenda;
    private LocalDate data;
    private double valor;
    private String metodoDePagamento;
    private List<ItemDeVenda> itensDeVenda;
    private Cliente cliente;
    

    public Venda() {
    }

    public Venda(int codvenda, LocalDate data, double valor, String metodoDePagamento) {
        this.codVenda = codvenda;
        this.data = data;
        this.valor = valor;
        this.metodoDePagamento = metodoDePagamento;
    }

    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

        public String getMetodoDePagamento() {
        return metodoDePagamento;
    }


    public void setMetodoDePagamento(String metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }
    
    
    public List<ItemDeVenda> getItensDeVenda() {
        return itensDeVenda;
    }

    public void setItensDeVenda(List<ItemDeVenda> itensDeVenda) {
        this.itensDeVenda = itensDeVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
}
