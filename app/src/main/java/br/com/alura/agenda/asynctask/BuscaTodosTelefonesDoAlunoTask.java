package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

// classe necerrária para executarmos tarefas em segundo plano
public class BuscaTodosTelefonesDoAlunoTask extends AsyncTask<Void, Void, List<Telefone>> {

    private final TelefoneDAO telefoneDAO;
    private final Aluno aluno;
    private final TelefonesDoAlunoEncontradosListener listener;

    // construtor
    public BuscaTodosTelefonesDoAlunoTask(TelefoneDAO telefoneDAO,
                                          Aluno aluno,
                                          TelefonesDoAlunoEncontradosListener listener) {
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.listener = listener;
    }

    @Override
    // tarefa feita em segundo plano
    protected List<Telefone> doInBackground(Void... voids) {
        return telefoneDAO
                .buscaTodosTelefonesDoAluno(aluno.getId());
    }

    @Override
    // necessário para que somente a thread principal que criou a hierarquia de view possa tocar nas views
    // somente a UI thread pode tocar nas views
    protected void onPostExecute(List<Telefone> telefones) {
        super.onPostExecute(telefones);
        listener.quandoEncontrados(telefones);
    }

    // método feito para delegarmos responsabilidade e evitar vazamento de um objeto de contexto
    public interface TelefonesDoAlunoEncontradosListener {
        void quandoEncontrados(List<Telefone> telefones);
    }
}
