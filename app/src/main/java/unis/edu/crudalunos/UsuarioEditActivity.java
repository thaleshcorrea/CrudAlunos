package unis.edu.crudalunos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import unis.edu.crudalunos.adapter.CursoAdapter;
import unis.edu.crudalunos.helpers.app_parameters;
import unis.edu.crudalunos.model.Aluno;
import unis.edu.crudalunos.model.Curso;
import unis.edu.crudalunos.model.Usuario;
import unis.edu.crudalunos.model.UsuarioCursoViewModel;
import unis.edu.crudalunos.model.UsuarioWithCurso;

public class UsuarioEditActivity extends AppCompatActivity {

    private static final String USUARIO_CURSO = "USUARIO_CURSO";

    private TextInputLayout textInputLayoutTelefone;
    private TextInputLayout textInputLayoutEmail;
    private MaterialButton btAdd;
    private RecyclerView recyclerView;
    private FloatingActionButton btSalvar;

    private UsuarioCursoViewModel usuarioCursoViewModel;
    private CursoAdapter adapter;

    private Usuario usuario;
    private UsuarioWithCurso usuarioWithCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_edit_usuario));

        usuario = app_parameters.getLoggedUser();

        // SetUp views
        textInputLayoutTelefone = findViewById(R.id.textInputTelefone);
        textInputLayoutEmail = findViewById(R.id.textInputEmail);
        btAdd = findViewById(R.id.btAdd);
        btSalvar = findViewById(R.id.btSalvar);

        // SetUp recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // SetUp adapter
        adapter = new CursoAdapter();

        // SetUp viewModel
        usuarioCursoViewModel = new ViewModelProvider(this).get(UsuarioCursoViewModel.class);
        usuarioCursoViewModel.getUsuarioWithCurso(usuario.getId(), data -> {
            if(data == null) {
                return;
            }
            this.usuarioWithCurso = (UsuarioWithCurso)data;
            adapter.setCursos(usuarioWithCurso.getCursos());
            recyclerView.setAdapter(adapter);

            preencherInformacoes();
        });

        // SetUp events
        btAdd.setOnClickListener(v-> {
            CursoSelecionarBottomSheet cursoSelecionarBottomSheet = new CursoSelecionarBottomSheet();
            cursoSelecionarBottomSheet.show(getSupportFragmentManager(), "");
        });
        btSalvar.setOnClickListener(v -> save());
    }

    private void preencherInformacoes( ) {
        Usuario usuario = usuarioWithCurso.getUsuario();
        if(usuario.getAluno() == null) {
            return;
        }
        textInputLayoutEmail.getEditText().setText(usuario.getAluno().getEmail());
        textInputLayoutTelefone.getEditText().setText(usuario.getAluno().getTelefone());
    }

    public void setCurso(Curso curso) {
        if(!existsCurso(curso)) {
            showSnackbar(getString(R.string.aluno_ja_matriculado));
        }
        usuarioWithCurso.getCursos().add(curso);
        adapter.setCursos(usuarioWithCurso.getCursos());
        recyclerView.setAdapter(adapter);
    }

    private boolean existsCurso(Curso cursoSelecionado) {
        for(Curso curso : usuarioWithCurso.getCursos()) {
            if(curso.getCursoId() == cursoSelecionado.getCursoId())
                return false;
        }
        return true;
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.coordinator), message, Snackbar.LENGTH_SHORT)
                .show();
    }

    private void save() {
        String telefone = textInputLayoutTelefone.getEditText().getText().toString();
        String email = textInputLayoutEmail.getEditText().getText().toString();

        if(usuarioWithCurso.getUsuario().getAluno() == null) {
            usuarioWithCurso.getUsuario().setAluno(new Aluno());
        }
        usuarioWithCurso.getUsuario().getAluno().setTelefone(telefone);
        usuarioWithCurso.getUsuario().getAluno().setEmail(email);

        Intent returnIntent = new Intent();
        returnIntent.putExtra(USUARIO_CURSO, usuarioWithCurso);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}