package unis.edu.crudalunos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import unis.edu.crudalunos.adapter.CursoAdapter;
import unis.edu.crudalunos.model.Curso;
import unis.edu.crudalunos.model.CursoViewModel;

public class CursoFragmentMain extends Fragment {

    static final String CURSO = "CURSO";
    private static final int CURSO_ADD_REQUEST = 1;
    private static final int CURSO_EDIT_REQUEST = 2;

    private CursoViewModel cursoViewModel;
    private FloatingActionButton fbAdd;

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curso_main, container, false);

        this.view = view;

        initViews();
        registrarEventos();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        final CursoAdapter adapter = new CursoAdapter();
        recyclerView.setAdapter(adapter);

        cursoViewModel = new ViewModelProvider(this).get(CursoViewModel.class);
        cursoViewModel.getAll().observe((LifecycleOwner) view.getContext(), new Observer<List<Curso>>() {
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
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Curso curso = (Curso) data.getSerializableExtra(CURSO);

            if (requestCode == CURSO_ADD_REQUEST) {
                cursoViewModel.insert(curso);
            } else if (requestCode == CURSO_EDIT_REQUEST) {
                cursoViewModel.update(curso);
            }

            Toast.makeText(view.getContext(), "Registro salvo", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        fbAdd = view.findViewById(R.id.btAdicionar);
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
        Intent intent = new Intent(view.getContext(), CursoActivity.class);
        startActivityForResult(intent, CURSO_ADD_REQUEST);
    }

    private void edit(Curso curso) {
//        Intent intent = new Intent(view.getContext(), CursoActivity.class);
//        intent.putExtra(CURSO, curso);
//        startActivityForResult(intent, CURSO_EDIT_REQUEST);
    }
}