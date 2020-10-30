package unis.edu.crudalunos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import unis.edu.crudalunos.adapter.CursoAdapter;
import unis.edu.crudalunos.model.Curso;
import unis.edu.crudalunos.model.CursoViewModel;

public class CursoActivity extends AppCompatActivity {

    static final String CURSO = "CURSO";
    private static final int CURSO_ADD_REQUEST = 1;
    private static final int CURSO_EDIT_REQUEST = 2;

    private CursoViewModel cursoViewModel;
    private FloatingActionButton fbAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);
        getSupportActionBar().setTitle(getText(R.string.title_curso));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        registrarEventos();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CursoAdapter adapter = new CursoAdapter();
        recyclerView.setAdapter(adapter);

        cursoViewModel = new ViewModelProvider(this).get(CursoViewModel.class);
        cursoViewModel.getAll().observe(this, new Observer<List<Curso>>() {
            @Override
            public void onChanged(List<Curso> cursos) {
                adapter.setCursos(cursos);
            }
        });

        adapter.setOnClickListener(new CursoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Curso curso) {
                edit(curso);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Curso curso = (Curso) data.getSerializableExtra(CURSO);

            if (requestCode == CURSO_ADD_REQUEST) {
                cursoViewModel.insert(curso);
            } else if (requestCode == CURSO_EDIT_REQUEST) {
                cursoViewModel.update(curso);
            }

            View contextView = findViewById(R.id.content).getRootView();
            Snackbar.make(contextView, "Registro salvo", Snackbar.LENGTH_SHORT)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        fbAdd = findViewById(R.id.btAdicionar);
    }

    private void registrarEventos() {
        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novo();
            }
        });
    }

    private void novo() {
        Intent intent = new Intent(this, CursoCadastrarActivity.class);
        startActivityForResult(intent, CURSO_ADD_REQUEST);
    }

    private void edit(Curso curso) {
        Intent intent = new Intent(this, CursoCadastrarActivity.class);
        intent.putExtra(CURSO, curso);
        startActivityForResult(intent, CURSO_EDIT_REQUEST);
    }
}