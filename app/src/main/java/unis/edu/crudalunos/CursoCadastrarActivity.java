package unis.edu.crudalunos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import unis.edu.crudalunos.model.Curso;

public class CursoCadastrarActivity extends AppCompatActivity {

    static final String CURSO = "CURSO";
    private Curso curso;

    private TextInputLayout textInputLayoutNome;
    private FloatingActionButton fbSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_cardastrar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        setUpEvents();

        Intent intent = getIntent();
        Object data = intent.getSerializableExtra(CURSO);
        if(data == null) {
            getSupportActionBar().setTitle(getText(R.string.title_novo_curso));
            curso = new Curso();
        }
        else {
            getSupportActionBar().setTitle(getText(R.string.title_editar_curso));
            curso = (Curso) data;
            fillValues();
        }
    }

    private void initViews() {
        textInputLayoutNome = findViewById(R.id.textInputNome);
        fbSalvar = findViewById(R.id.btSalvar);
    }

    private void setUpEvents() {
        fbSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void fillValues() {
        textInputLayoutNome.getEditText().setText(curso.getNome());
    }

    private void createDisciplina() {
        curso.setNome(textInputLayoutNome.getEditText().getText().toString());
    }

    private Boolean validateNome(String nome) {
        if(nome.isEmpty()) {
            textInputLayoutNome.setError(getText(R.string.required_nome));
            return false;
        } else {
            textInputLayoutNome.setError(null);
            return true;
        }
    }

    private void save() {
        createDisciplina();

        if(!validateNome(curso.getNome())) {
            return;
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(CURSO, curso);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}