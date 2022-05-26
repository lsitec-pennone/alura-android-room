package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

// classe necerrária para executarmos tarefas em segundo plano
public class RemoveAlunoTask extends AsyncTask<Void, Void, Void> {

    private final AlunoDao dao;
    private final ListaAlunosAdapter adapter;
    private  final Aluno aluno;

    // construtor
    public RemoveAlunoTask(AlunoDao dao, ListaAlunosAdapter adapter, Aluno aluno) {
        this.dao = dao;
        this.adapter = adapter;
        this.aluno = aluno;
    }

    @Override
    // tarefa feita em segundo plano
    protected Void doInBackground(Void... voids) {
        dao.remove(aluno);
        return null;
    }

    @Override
    // necessário para que somente a thread principal que criou a hierarquia de view possa tocar nas views
    // somente a UI thread pode tocar nas views
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.remove(aluno);
    }
}
