package unis.edu.crudalunos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.card.MaterialCardView;

import unis.edu.crudalunos.enums.UsuarioTipo;

public class AlunoProfessorSelectorActivity extends AppCompatActivity {

    private static final String USUARIO_TIPO = "USUARIO_TIPO";

    private MaterialCardView materialCardViewAluno;
    private MaterialCardView materialCardViewProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_professor_selector);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.escolher_tipo));

        // SetUp views
        materialCardViewAluno = findViewById(R.id.cardViewAluno);
        materialCardViewProfessor = findViewById(R.id.cardViewProfessor);

        // SetUp events
        materialCardViewAluno.setOnClickListener(v -> selecionar(UsuarioTipo.ALUNO.getValue()));
        materialCardViewProfessor.setOnClickListener(v -> selecionar(UsuarioTipo.PROFESSOR.getValue()));
    }

    private void selecionar(int tipo) {
        Intent intent = new Intent();
        intent.putExtra(USUARIO_TIPO, tipo);
        setResult(Activity.RESULT_OK, intent);

        finish();
    }
}