package br.com.alura.agenda.asynctask;

import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

// classe necerrária para executarmos tarefas em segundo plano
public class SalvaAlunoTask extends BaseAlunoComTelefoneTask {

    private final AlunoDao alunoDao;
    private final Aluno aluno;
    private final Telefone telefoneFixo;
    private final Telefone telefoneCelular;
    private final TelefoneDAO telefoneDAO;

    // construtor
    public SalvaAlunoTask(AlunoDao alunoDao,
                          Aluno aluno,
                          Telefone telefoneFixo,
                          Telefone telefoneCelular,
                          TelefoneDAO telefoneDAO, FinalizadaListener listener) {
        super(listener);
        this.alunoDao = alunoDao;
        this.aluno = aluno;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.telefoneDAO = telefoneDAO;
    }

    @Override
    // tarefa feita em segundo plano
    protected Void doInBackground(Void... voids) {
        int alunoId = alunoDao.salva(aluno).intValue(); // pega o id do aluno
        vinculaAlunoComTelefone(alunoId, telefoneFixo, telefoneCelular);
        telefoneDAO.salva(telefoneFixo, telefoneCelular); // salva na database
        return null;
    }

}
