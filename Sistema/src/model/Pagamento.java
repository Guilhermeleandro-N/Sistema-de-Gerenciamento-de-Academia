package model;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private int idAluno;
    private LocalDate dataPagamento;
    private double valor;

    public Pagamento(int idAluno, LocalDate dataPagamento, double valor) {
        this.idAluno = idAluno;
        this.dataPagamento = dataPagamento;
        this.valor = valor;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getIdAluno() { return idAluno; }

    public LocalDate getDataPagamento() { return dataPagamento; }

    public double getValor() { return valor; }

    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }

    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }

    public void setValor(double valor) { this.valor = valor; }
}
