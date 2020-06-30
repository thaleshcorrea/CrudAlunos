package unis.edu.crudalunos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import unis.edu.crudalunos.helpers.MyPreferences;
import unis.edu.crudalunos.helpers.OnTaskCompleted;
import unis.edu.crudalunos.helpers.UserPreferences;
import unis.edu.crudalunos.model.Usuario;
import unis.edu.crudalunos.model.UsuarioViewModel;

public class LoginActivity extends AppCompatActivity {

    public static final String USER = "USER";
    public static final String STAY_LOGGED = "STAY_LOGGED";
    public static final int ADD_USER_REQUEST = 1;

    private UsuarioViewModel usuarioViewModel;
    private MyPreferences userPreferences;

    private TextInputLayout textInputUsuario;
    private TextInputLayout textInputSenha;
    private CheckBox chManterConectado;
    private Button btEntrar;
    private Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getText(R.string.title_login));
        setContentView(R.layout.activity_login);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        userPreferences = new UserPreferences(getApplicationContext(), getText(R.string.sp_usuario).toString());

        initViews();
        registrarEventos();

        verificarUsuario();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_USER_REQUEST && resultCode == Activity.RESULT_OK) {
            Usuario usuario = (Usuario) data.getSerializableExtra(USER);
            salvarUsuario(usuario);
        }
    }

    private void verificarUsuario() {
        if (userPreferences.exist()) {
            int id = userPreferences.get();

            usuarioViewModel.getById(id, new OnTaskCompleted() {
                @Override
                public void processFinish(Object output) {
                    if(output != null) {
                        Usuario usuario = (Usuario) output;
                        entrar(usuario, true);
                    }
                }
            });
        }
    }

    private void initViews() {
        textInputUsuario = findViewById(R.id.textInputUsuario);
        textInputSenha = findViewById(R.id.textInputSenha);
        btEntrar = findViewById(R.id.btEntrar);
        btCadastrar = findViewById(R.id.btCadastrar);
        chManterConectado = findViewById(R.id.chManterConectado);
    }

    private void registrarEventos() {
        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUsuario();
            }
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    private Boolean validarUsuario(String usuario) {
        if (usuario.isEmpty()) {
            textInputUsuario.setError(getText(R.string.usuario_obrigatorio));
            return false;
        } else {
            return true;
        }
    }

    private Boolean validarSenha(String senha) {
        if (senha.isEmpty()) {
            textInputSenha.setError(getText(R.string.senha_obrigatoria));
            return false;
        } else {
            return true;
        }
    }

    private void salvarUsuario(Usuario usuario) {
        usuarioViewModel.insert(usuario);
        Toast.makeText(this, "Usuário cadastrado", Toast.LENGTH_SHORT);
    }

    private void buscarUsuario() {
        String usuario = textInputUsuario.getEditText().getText().toString();
        String senha = textInputSenha.getEditText().getText().toString();

        if (!validarUsuario(usuario) && !validarSenha(senha)) {
            return;
        }

        final boolean manterConectado = chManterConectado.isChecked();
        usuarioViewModel.Login(usuario, senha, new OnTaskCompleted() {
            @Override
            public void processFinish(Object output) {
                if (output != null) {
                    Usuario usuario = (Usuario) output;
                    entrar(usuario, manterConectado);
                } else {
                    showSnackBar();
                }

            }
        });
    }

    private void entrar(Usuario usuario, Boolean stayLogged) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(STAY_LOGGED, stayLogged);
        intent.putExtra(USER, usuario);
        startActivity(intent);
    }

    private void cadastrar() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, ADD_USER_REQUEST);
    }

    private void showSnackBar() {
        Snackbar.make(findViewById(android.R.id.content), getText(R.string.usuario_ou_senha_invalidos), Snackbar.LENGTH_SHORT).show();
    }
}