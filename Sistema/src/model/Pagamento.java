package model;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private int idAluno;
    private LocalDate dataPagamento;
    private LocalDate dataVencimento;
    private double valor;
    private String status;

    public Pagamento(int idAluno, LocalDate dataPagamento, LocalDate dataVencimento, double valor, String status) {
        this.idAluno = idAluno;
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }

    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }

    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
