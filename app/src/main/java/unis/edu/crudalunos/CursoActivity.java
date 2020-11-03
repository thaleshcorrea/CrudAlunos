package unis.edu.crudalunos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import unis.edu.crudalunos.adapter.CursoAdapter;
import unis.edu.crudalunos.helpers.OnTaskCompleted;
import unis.edu.crudalunos.model.Curso;
import unis.edu.crudalunos.model.CursoViewModel;
import unis.edu.crudalunos.model.CursoWithDisciplinas;

public class CursoActivity extends AppCompatActivity {

    static final String CURSO = "CURSO";
    private static final int CURSO_ADD_REQUEST = 1;
    private static final int CURSO_EDIT_REQUEST = 2;

    private CursoViewModel cursoViewModel;
    private CursoAdapter adapter;
    private FloatingActionButton fbAdd;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);
        getSupportActionBar().setTitle(getText(R.string.title_curso));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initViews();
        registrarEventos();

        cursoViewModel = new ViewModelProvider(this).get(CursoViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new CursoAdapter();
        adapter.setOnClickListener(curso -> edit(curso));
    }

    @Override
    protected void onStart() {
        super.onStart();

        getCursosFromDb("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.curso_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.hint_pesquisar_cursos));
        
        searchView.setOnQueryTextListener(onQueryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    private SearchView.OnQueryTextListener onQueryTextListener =
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    getCursosFromDb(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    getCursosFromDb(newText);
                    return true;
                }
            };

    private void getCursosFromDb(String searchText) {
        searchText = "%" + searchText + "%";
        cursoViewModel.getByNome(searchText).observe(CursoActivity.this, (Observer<List<CursoWithDisciplinas>>) cursoWithDisciplinas -> {
            if(cursoWithDisciplinas == null) {
                return;
            }
            adapter.setCursos(cursoWithDisciplinas);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            final Curso curso = (Curso) data.getSerializableExtra(CURSO);

            if (requestCode == CURSO_ADD_REQUEST) {
                cursoViewModel.insert(curso, output -> {
                    if(output != null) {
                        long cursoId = (long)output;
                        curso.setCursoId(cursoId);
                    }
                });
            } else if (requestCode == CURSO_EDIT_REQUEST) {
                cursoViewModel.update(curso);
            }

            Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Registro salvo", Snackbar.LENGTH_SHORT)
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

    private void edit(CursoWithDisciplinas curso) {
        Intent intent = new Intent(this, CursoCadastrarActivity.class);
        intent.putExtra(CURSO, curso.curso);
        startActivityForResult(intent, CURSO_EDIT_REQUEST);
    }
}