package unis.edu.crudalunos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import unis.edu.crudalunos.enums.UsuarioTipo;
import unis.edu.crudalunos.helpers.OnTaskCompleted;
import unis.edu.crudalunos.model.Usuario;
import unis.edu.crudalunos.model.UsuarioViewModel;

public class SignUpActivity extends AppCompatActivity {
    public static final String USER = "USER";
    private static final String USUARIO_TIPO = "USUARIO_TIPO";
    private static final int TIPO = 1;

    private UsuarioViewModel usuarioViewModel;

    private TextInputLayout textInputLayoutUsuario;
    private TextInputLayout textInputLayoutSenha;
    private TextInputLayout textInputLayoutConfirmarSenha;
    private TextInputLayout textInputLayoutNome;
    private Button btSalvar;

    private int tipo_usuario = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle(getText(R.string.title_usuario_cadastrar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        initViews();
        registrarEventos();

        Intent intent = new Intent(this, AlunoProfessorSelectorActivity.class);
        startActivityForResult(intent, TIPO);
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TIPO) {
                Object value = data.getIntExtra(USUARIO_TIPO, UsuarioTipo.ALUNO.getValue());
                tipo_usuario = (Integer) value;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        textInputLayoutUsuario = findViewById(R.id.textInputUsuario);
        textInputLayoutSenha = findViewById(R.id.textInputSenha);
        textInputLayoutConfirmarSenha = findViewById(R.id.textIputConfirmarSenha);
        textInputLayoutNome = findViewById(R.id.textInputNome);
        btSalvar = findViewById(R.id.btEntrar);
    }

    private void registrarEventos() {
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });
    }

    private Boolean validaNome() {
        String nome = textInputLayoutNome.getEditText().getText().toString();

        if (nome.isEmpty()) {
            textInputLayoutNome.setError(getText(R.string.usuario_obrigatorio));
            return false;
        } else {
            textInputLayoutNome.setError(null);
            return true;
        }
    }

    private Boolean validarUsuario() {
        String usuario = textInputLayoutUsuario.getEditText().getText().toString();

        if (usuario.isEmpty()) {
            textInputLayoutUsuario.setError(getText(R.string.usuario_obrigatorio));
            return false;
        } else {
            textInputLayoutUsuario.setError(null);
            return true;
        }
    }

    private Boolean validarSenha() {
        String senha = textInputLayoutSenha.getEditText().getText().toString();
        String confirmacaoSenha = textInputLayoutConfirmarSenha.getEditText().getText().toString();

        if (senha.isEmpty()) {
            textInputLayoutSenha.setError(getText(R.string.senha_obrigatoria));
            return false;
        } else if (!confirmacaoSenha.equals(senha)) {
            textInputLayoutConfirmarSenha.setError(getText(R.string.senhas_nao_correspondem));
            return false;
        } else {
            textInputLayoutSenha.setError(null);
            textInputLayoutConfirmarSenha.setError(null);
            return true;
        }
    }

    private void validar() {
        if (!validaNome() || !validarUsuario() || !validarSenha()) {
            return;
        }

        String nomeUsuario = textInputLayoutUsuario.getEditText().getText().toString();
        usuarioViewModel.getByLogin(nomeUsuario, new OnTaskCompleted() {
            @Override
            public void processFinish(Object output) {
                if (output != null) {
                    Usuario usuario = (Usuario) output;
                    if (usuario.getId() == 0) {
                        salvar();
                    } else {
                        textInputLayoutUsuario.setError(getText(R.string.usuario_ja_cadastrado));
                    }
                } else {
                    salvar();
                }
            }
        });
    }

    private void salvar() {
        String nome = textInputLayoutNome.getEditText().getText().toString();
        String nomeUsuario = textInputLayoutUsuario.getEditText().getText().toString();
        String senha = textInputLayoutSenha.getEditText().getText().toString();

        Usuario usuario = new Usuario(nome, nomeUsuario, senha);
        usuario.setUsuarioTipo(tipo_usuario);

        Intent returnIntent = new Intent();
        returnIntent.putExtra(USER, usuario);

        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}