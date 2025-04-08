package model;

import java.time.LocalDateTime;

public class Acesso {
    private int id;
    private int idAluno;
    private LocalDateTime dataHora;

    public Acesso(int idAluno, LocalDateTime dataHora) {
        this.idAluno = idAluno;
        this.dataHora = dataHora;
    }

    public int getId() { return id; }

    public int getIdAluno() { return idAluno; }

    public LocalDateTime getDataHora() { return dataHora; }

    public void setId(int id) { this.id = id; }

    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }

    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
