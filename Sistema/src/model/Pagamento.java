package model;

import java.time.LocalDate;

// Classe Pagamento representa os dados financeiros associados a um aluno
public class Pagamento {

    // Encapsulamento: atributos são privados
    private int id;
    private int idAluno;
    private LocalDate dataPagamento;
    private LocalDate dataVencimento;
    private double valor;
    private String status;

    // Construtor: usado para criar objetos de Pagamento
    public Pagamento(int idAluno, LocalDate dataPagamento, LocalDate dataVencimento, double valor, String status) {
        this.idAluno = idAluno;
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.status = status;
    }

    // Getters e Setters: aplicação do Encapsulamento
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
