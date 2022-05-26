package br.com.alura.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.alura.agenda.model.Telefone;

// @Dao informa que é uma DAO
@Dao
// interface para acessarmos o telefone além de outros comportamentos
public interface TelefoneDAO {

    // @Query para informar que buscaremos um array de dados
    @Query("SELECT * FROM Telefone " +
            "WHERE alunoId = :alunoId " +
            "LIMIT 1")
    Telefone buscaPrimeiroTelefoneDoAluno(int alunoId);

    // @Insert para informar que salvaremos um "Telefone" na nossa database
    @Insert
    void salva(Telefone... telefones);

    // @Query para informar que buscaremos um array de dados
    @Query("SELECT * FROM Telefone " +
            "WHERE alunoId = :alunoId ")
    List<Telefone> buscaTodosTelefonesDoAluno(int alunoId);

    // @Insert para informar que salvaremos um "Telefone" na nossa database
    // embora seja uma atualização do conteúdo, mas ele irá substituir (REPLACE) caso haja um egistro igual ao outro
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void atualiza(Telefone... telefones);
}
