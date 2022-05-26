package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.model.Telefone;

// classe base para as tasks de salvar e editar
abstract class BaseAlunoComTelefoneTask extends AsyncTask<Void, Void, Void> {

    private final FinalizadaListener listener;

    BaseAlunoComTelefoneTask(FinalizadaListener listener) {
        this.listener = listener;
    }

    void vinculaAlunoComTelefone(int alunoId, Telefone... telefones) {
        for (Telefone telefone :
                telefones) {
            telefone.setAlunoId(alunoId);
        }
    }

    @Override
    // necessário para que somente a thread principal que criou a hierarquia de view possa tocar nas views
    // somente a UI thread pode tocar nas views
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizada();
    }

    // listener para que o formulario só termine quando o aluno for salvo/editado
    public interface FinalizadaListener {
        void quandoFinalizada();
    }
}
