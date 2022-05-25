package br.com.alura.agenda.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.alura.agenda.database.converter.ConversorCalendar;
import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;

import static br.com.alura.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

// @Database indica que a classe que estamos criando representa um database
@Database(entities = {Aluno.class}, version = 4, exportSchema = false)

// @TypeConverters para informar a conversão de tipo
@TypeConverters({ConversorCalendar.class})
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "agenda.db";

    // método abstrato que devolve uma instância de DAO,
    // para que outras classes tenham acesso ao AlunoDao a partir da AgendaDatabase
    public abstract AlunoDao getRoomAlunoDAO();

    // método para simplificarmos a chamada para a instancia de uma database
    public static AgendaDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, AgendaDatabase.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                // necessário caso nossa database tenha uma mudança estrutural
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }
}
