package br.com.alura.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.asynctask.BuscaTodosTelefonesDoAlunoTask;
import br.com.alura.agenda.asynctask.EditaAlunoTask;
import br.com.alura.agenda.asynctask.SalvaAlunoTask;
import br.com.alura.agenda.database.AgendaDatabase;
import br.com.alura.agenda.database.dao.AlunoDao;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;
import br.com.alura.agenda.model.TipoTelefone;

import static br.com.alura.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefoneFixo; // adicionando telefone fixo
    private EditText campoTelefoneCelular; // adicionando telefone celular
    private EditText campoEmail;
    private AlunoDao alunoDao;
    private Aluno aluno;
    private TelefoneDAO telefoneDAO;
    private List<Telefone> telefonesDoAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        // instancia da database
        AgendaDatabase database = AgendaDatabase.getInstance(this);

        // alunoDao instanciada para utilizamros seus métodos
        alunoDao = database.getRoomAlunoDAO();

        // telefoneDAO instanciada para utilizarmos seus métodos
        telefoneDAO = database.getTelefoneDAO();

        inicializacaoDosCampos();
        carregaAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_formulario_aluno_menu_salvar) {
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    // método que coloca a atual informação do aluno no campo de edição
    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoEmail.setText(aluno.getEmail());
        preencheCamposDeTelefone();
    }

    private void preencheCamposDeTelefone() {
        // API para executarmos uma tarefa assincronamente
        new BuscaTodosTelefonesDoAlunoTask(telefoneDAO, aluno, telefones -> {
            this.telefonesDoAluno = telefones;
            // para cada número de telefone, diferencia qual é o tipo FIXO e qual é o tipo CELULAR
            for (Telefone telefone :
                    telefonesDoAluno) {
                if (telefone.getTipo() == TipoTelefone.FIXO) {
                    campoTelefoneFixo.setText((telefone.getNumero()));
                } else {
                    campoTelefoneCelular.setText(telefone.getNumero());
                }
            }
        }).execute(); // execute faz com que a tarefa seja executada fora da UI thread
    }

    // método que encerra a activity Formulario  faz o update das informações
    private void finalizaFormulario() {
        preencheAluno();

        Telefone telefoneFixo = criaTelefone(campoTelefoneFixo, TipoTelefone.FIXO);
        Telefone telefoneCelular = criaTelefone(campoTelefoneCelular, TipoTelefone.CELULAR);

        if (aluno.temIdValido()) {
            editaAluno(telefoneFixo, telefoneCelular);
        } else {
            salvaAluno(telefoneFixo, telefoneCelular);
        }
    }

    @NotNull
    private Telefone criaTelefone(EditText campoTelefoneFixo, TipoTelefone fixo) {
        String numeroFixo = campoTelefoneFixo.getText().toString(); // pega o telefoneFixo do EditText
        return new Telefone(numeroFixo,
                fixo);
    }

    private void salvaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        // API para executarmos uma tarefa assincronamente
        new SalvaAlunoTask(alunoDao, aluno, telefoneFixo,
                telefoneCelular, telefoneDAO, this::finish)
                .execute(); // execute faz com que a tarefa seja executada fora da UI thread
    }

    private void editaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        // API para executarmos uma tarefa assincronamente
        new EditaAlunoTask(alunoDao, aluno, telefoneFixo, telefoneCelular, telefoneDAO, telefonesDoAluno,
                this::finish).execute(); // execute faz com que a tarefa seja executada fora da UI thread
    }

    // findViewById para referienciarmos nossa view
    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefoneFixo = findViewById(R.id.activity_formulario_aluno_telefone_fixo);
        campoTelefoneCelular = findViewById(R.id.activity_formulario_aluno_telefone_celular);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    // método que pega o sobrenome digitado na view e coloca no objeto "aluno"
    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setEmail(email);
    }
}