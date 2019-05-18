package cantina.model.POJO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Venda implements Serializable {

    private int codVenda;
    private LocalDate data;
    private double valor;
    private boolean pago;
    private List<ItemDeVenda> itensDeVenda;
    private Cliente cliente;

    public Venda() {
    }

    public Venda(int cdvenda, LocalDate data, double valor, boolean pago) {
        this.codVenda = cdvenda;
        this.data = data;
        this.valor = valor;
        this.pago = pago;
    }

    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int cdVenda) {
        this.codVenda = cdVenda;
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

    public boolean getPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
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
