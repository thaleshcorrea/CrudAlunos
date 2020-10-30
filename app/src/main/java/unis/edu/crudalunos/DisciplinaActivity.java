package unis.edu.crudalunos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import unis.edu.crudalunos.model.Disciplina;
import unis.edu.crudalunos.model.DisciplinaViewModel;
import unis.edu.crudalunos.model.Usuario;

public class DisciplinaActivity extends AppCompatActivity {

    static final String DISCIPLINA = "DISCIPLINA";

    private TextInputLayout textInputLayoutNome;
    private FloatingActionButton fbSave;

    private Disciplina disciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);

        getSupportActionBar().setTitle(getText(R.string.title_disciplina));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        registerEvents();

        Intent intent = getIntent();

        Object data = intent.getSerializableExtra(DISCIPLINA);
        if(data != null) {
            disciplina = (Disciplina) data;
            preencherValores();
        } else {
            disciplina = new Disciplina();
        }
    }

    private void initViews() {
        textInputLayoutNome = findViewById(R.id.textInputNome);
        fbSave = findViewById(R.id.btSalvar);
    }

    private void registerEvents() {
        fbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void preencherValores() {
        textInputLayoutNome.getEditText().setText(disciplina.getDescricao());
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

    private void createDisciplina() {
        disciplina.setDescricao(textInputLayoutNome.getEditText().getText().toString());
    }

    private void save() {
        createDisciplina();

        if(!validateNome(disciplina.getDescricao())) {
            return;
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(DISCIPLINA, disciplina);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}