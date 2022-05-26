package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Telefone;

// classe necerrária para executarmos tarefas em segundo plano
public class BuscaPrimeiroTelefoneDoAlunoTask extends AsyncTask<Void, Void, Telefone> {

    private final TelefoneDAO dao;
    private final int alunoId;
    private final PrimeiroTelefoneEncontradoListener listener;

    // construtor
    public BuscaPrimeiroTelefoneDoAlunoTask(TelefoneDAO dao,
                                            int alunoId,
                                            PrimeiroTelefoneEncontradoListener listener) {
        this.dao = dao;
        this.alunoId = alunoId;
        this.listener = listener;
    }

    @Override
    // tarefa feita em segundo plano
    protected Telefone doInBackground(Void... voids) {
        return dao.buscaPrimeiroTelefoneDoAluno(alunoId); // busca o telefone
    }

    @Override
    // necessário para que somente a thread principal que criou a hierarquia de view possa tocar nas views
    // somente a UI thread pode tocar nas views
    protected void onPostExecute(Telefone primeiroTelefone) {
        super.onPostExecute(primeiroTelefone);
        listener.quandoEncontrado(primeiroTelefone);
    }

    // método feito para delegarmos responsabilidade e evitar vazamento de um objeto de contexto
    public interface PrimeiroTelefoneEncontradoListener {
        void quandoEncontrado(Telefone telefoneEncontrado);
    }
}
