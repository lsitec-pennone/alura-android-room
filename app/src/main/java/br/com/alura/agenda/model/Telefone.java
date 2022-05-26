package br.com.alura.agenda.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// @Entity informa que é uma entidade na nossa database
@Entity
// classe que representa o modelo de um telefone
public class Telefone {

    // @PrimaryKey indica que o id é a nossa chae primária
    @PrimaryKey(autoGenerate = true) // autoGenerate para que a chave seja gerada automaticamente
    private int id;
    private String numero;
    private TipoTelefone tipo; // tipo Fixo ou Celular
    // @ForeignKey para indicar que é chave estrangeira
    @ForeignKey(entity = Aluno.class,
            parentColumns = "id",
            childColumns = "alunoId",
            onUpdate = ForeignKey.CASCADE, // se o aluno for apagado, o telefone tbm é
            onDelete = ForeignKey.CASCADE)
    private int alunoId;

    // construtor
    public Telefone(String numero, TipoTelefone tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }


}
