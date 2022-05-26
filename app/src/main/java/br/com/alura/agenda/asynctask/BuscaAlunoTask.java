package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

// classe necerrária para executarmos tarefas em segundo plano
public class BuscaAlunoTask extends AsyncTask<Void, Void, List<Aluno>> {
    private final AlunoDao dao;
    private final ListaAlunosAdapter adapter;

    // construtor
    public BuscaAlunoTask(AlunoDao dao, ListaAlunosAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    // tarefa feita em segundo plano
    protected List<Aluno> doInBackground(Void[] objects) {
        return dao.todos();
    }

    @Override
    // necessário para que somente a thread principal que criou a hierarquia de view possa tocar nas views
    // somente a UI thread pode tocar nas views
    protected void onPostExecute(List<Aluno> todosAlunos) {
        super.onPostExecute(todosAlunos);
        adapter.atualiza(todosAlunos);
    }
}
