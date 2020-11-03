package unis.edu.crudalunos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import unis.edu.crudalunos.adapter.HorarioAdapter;
import unis.edu.crudalunos.model.Curso;
import unis.edu.crudalunos.model.Disciplina;
import unis.edu.crudalunos.model.Horario;
import unis.edu.crudalunos.model.HorarioViewModel;
import unis.edu.crudalunos.model.Usuario;

public class DisciplinaActivity extends AppCompatActivity {

    static final String DISCIPLINA = "DISCIPLINA";
    static final String HORARIO = "HORARIO";
    static final String HORARIO_INDEX = "HORARIO_INDEX";
    private static final int HORARIO_ADD_REQUEST = 1;
    private static final int HORARIO_EDIT_REQUEST = 2;

    private HorarioViewModel horarioViewModel;
    private HorarioAdapter horarioAdapter;
    private RecyclerView recyclerView;

    private TextInputLayout textInputLayoutNome;
    private FloatingActionButton fbSave;

    private Disciplina disciplina;
    private MaterialButton btProfessores;
    private MaterialButton btCursos;
    private MaterialButton btAdd;
    private Chip chipProfessores;
    private Chip chipCurso;

    private Usuario professorSelecionado = null;
    private Curso cursoSelecionado = null;

    private List<Horario> horarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);

        getSupportActionBar().setTitle(getText(R.string.title_disciplina));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        registerEvents();

        // SetUp viewModelHorario
        horarioViewModel = new ViewModelProvider(this).get(HorarioViewModel.class);

        // Get disciplina via Intent no caso de edição
        Intent intent = getIntent();
        Object data = intent.getSerializableExtra(DISCIPLINA);
        if (data != null) {
            disciplina = (Disciplina) data;
            preencherValores();

            horarios = horarioViewModel.getByDisciplina(disciplina.getDisciplinaId());
        } else {
            disciplina = new Disciplina();
        }

        // SetUp recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // SetUp adapterHorarios
        horarioAdapter = new HorarioAdapter();
        horarioAdapter.setOnClickListener(horario -> editHorario(horario, horarios.indexOf(horario)));

        // Atribuir colecao ao adapter
        horarioAdapter.setHorarioList(horarios);
        recyclerView.setAdapter(horarioAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Horario horario = (Horario) data.getSerializableExtra(HORARIO);
            if (requestCode == HORARIO_ADD_REQUEST) {
                horarios.add(horario);
            } else if (requestCode == HORARIO_EDIT_REQUEST) {
                int index = data.getIntExtra(HORARIO_INDEX, 0);
                horarios.set(index, horario);
            }
            horarioAdapter.setHorarioList(horarios);
            recyclerView.setAdapter(horarioAdapter);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        textInputLayoutNome = findViewById(R.id.textInputNome);
        fbSave = findViewById(R.id.btSalvar);
        btProfessores = findViewById(R.id.btProfessores);
        btCursos = findViewById(R.id.btCurso);
        chipProfessores = findViewById(R.id.chip_1);
        chipCurso = findViewById(R.id.chip_2);
        btAdd = findViewById(R.id.btAdd);
    }

    private void registerEvents() {
        fbSave.setOnClickListener(v -> save());
        btProfessores.setOnClickListener(v -> {
            ProfessorSelecionarBottomSheet sessaoBottomOptions = new ProfessorSelecionarBottomSheet();
            sessaoBottomOptions.show(getSupportFragmentManager(), "");
        });
        btCursos.setOnClickListener(v -> {
            CursoSelecionarBottomSheet cursoSelecionarBottomSheet = new CursoSelecionarBottomSheet();
            cursoSelecionarBottomSheet.show(getSupportFragmentManager(), "");

        });
        chipProfessores.setOnCloseIconClickListener(v -> clearProfessor());
        chipCurso.setOnCloseIconClickListener(v -> clearCurso());
        btAdd.setOnClickListener(v -> newHorario());
    }

    public void selecionarProfessor(Usuario usuario) {
        professorSelecionado = usuario;
        chipProfessores.setText(professorSelecionado.getNome());
    }

    public void selecionarCurso(Curso curso) {
        cursoSelecionado = curso;
        chipCurso.setText(cursoSelecionado.getNome());
    }

    private void clearProfessor() {
        if (professorSelecionado == null) {
            Snackbar.make(findViewById(R.id.coordinator), getString(R.string.nenhum_professor_selecionado), Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }

        Usuario usuarioTemp = professorSelecionado;
        professorSelecionado = null;
        chipProfessores.setText(R.string.nenhum_professor_selecionado);
        Snackbar.make(findViewById(R.id.coordinator), getString(R.string.professor_removido), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.desfazer), v -> {
                    professorSelecionado = usuarioTemp;
                    chipProfessores.setText(professorSelecionado.getNome());
                }).show();
    }

    private void clearCurso() {
        if (cursoSelecionado == null) {
            Snackbar.make(findViewById(R.id.coordinator), getString(R.string.nenhum_curso_selecionado), Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }

        Curso cursoTemp = cursoSelecionado;
        cursoSelecionado = null;
        chipCurso.setText(R.string.nenhum_curso_selecionado);
        Snackbar.make(findViewById(R.id.coordinator), getString(R.string.curso_removido), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.desfazer), v -> {
                    cursoSelecionado = cursoTemp;
                    chipCurso.setText(cursoSelecionado.getNome());
                }).show();
    }

    private void preencherValores() {
        textInputLayoutNome.getEditText().setText(disciplina.getDescricao());
    }

    private Boolean validateNome(String nome) {
        if (nome.isEmpty()) {
            textInputLayoutNome.setError(getText(R.string.required_nome));
            return false;
        } else {
            textInputLayoutNome.setError(null);
            return true;
        }
    }

    private Boolean validateProfessor() {
        if (professorSelecionado == null) {
            return false;
        }
        return true;
    }

    private Boolean validateCurso() {
        if (cursoSelecionado == null) {
            return false;
        }
        return true;
    }

    private void createDisciplina() {
        disciplina.setDescricao(textInputLayoutNome.getEditText().getText().toString());
        disciplina.setHorarios(horarios);
    }

    private void save() {
        createDisciplina();

        if (!validateNome(disciplina.getDescricao())
                || !validateProfessor()
                || !validateCurso()) {
            return;
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(DISCIPLINA, disciplina);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void newHorario() {
        Intent intent = new Intent(this, HorarioActivity.class);
        startActivityForResult(intent, HORARIO_ADD_REQUEST);
    }

    private void editHorario(Horario horario, int index) {
        Intent intent = new Intent(this, HorarioActivity.class);
        intent.putExtra(HORARIO, horario);
        intent.putExtra(HORARIO_INDEX, index);
        startActivityForResult(intent, HORARIO_EDIT_REQUEST);
    }
}