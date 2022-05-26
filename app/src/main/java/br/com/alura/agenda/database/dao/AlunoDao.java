package br.com.alura.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.alura.agenda.model.Aluno;

// @Dao para informar nosso Database que é uma classe DAO
@Dao
public interface AlunoDao {

    // @Insert para informar que salvaremos um "aluno" na nossa database
    @Insert
    // o "long" faz com que o Room devolva automaticamente o id criado
    Long salva(Aluno aluno);

    // @Query para informar que buscaremos um array de dados
    @Query("SELECT * FROM aluno")
    List<Aluno> todos();

    // @Delete para informar que deletaremos um "aluno" na nossa database
    @Delete
    void remove(Aluno aluno);

    // @Update para informar que aluno será editado
    @Update
    void edita(Aluno aluno);
}
