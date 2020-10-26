package unis.edu.crudalunos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import unis.edu.crudalunos.adapter.DisciplinaAdapter;
import unis.edu.crudalunos.model.Curso;
import unis.edu.crudalunos.model.Disciplina;
import unis.edu.crudalunos.model.DisciplinaViewModel;
import unis.edu.crudalunos.model.DisciplinaViewModelFactory;

public class CursoDisciplinasFragment extends Fragment {

    private static final String ARG_CURSOID = "cursoId";
    private static final String DISCIPLINA = "DISCIPLINA";
    private static final int DISCIPLINA_ADD_REQUEST = 1;
    private static final int DISCIPLINA_EDIT_REQUEST = 2;

    private Curso curso = null;

    private View view;
    private DisciplinaViewModel disciplinaViewModel;

    private FloatingActionButton btAdicionar;

    public static CursoDisciplinasFragment newInstance(Curso curso) {
        CursoDisciplinasFragment fragment = new CursoDisciplinasFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURSOID, curso);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            curso = (Curso) getArguments().getSerializable(ARG_CURSOID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_curso_disciplinas, container, false);
        this.view = view;

        initViews();

        int cursoId = curso != null ? curso.getId() : 0;

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        final DisciplinaAdapter disciplinaAdapter = new DisciplinaAdapter();
        recyclerView.setAdapter(disciplinaAdapter);

        DisciplinaViewModelFactory disciplinaViewModelFactory = new DisciplinaViewModelFactory(getActivity().getApplication(), cursoId);
        disciplinaViewModel = new ViewModelProvider(this, disciplinaViewModelFactory).get(DisciplinaViewModel.class);
        disciplinaViewModel.getByCurso().observe((LifecycleOwner) view.getContext(), new Observer<List<Disciplina>>() {
            @Override
            public void onChanged(List<Disciplina> disciplinas) {
                disciplinaAdapter.setDisciplinas(disciplinas);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                disciplinaViewModel.delete(disciplinaAdapter.getDisciplinaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(view.getContext(), "Disciplina removida", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        disciplinaAdapter.setOnClickListener(new DisciplinaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Disciplina disciplina) {
                editar(disciplina);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            Disciplina disciplina = (Disciplina)data.getSerializableExtra(DISCIPLINA);
            if(requestCode == DISCIPLINA_ADD_REQUEST) {
                disciplinaViewModel.insert(disciplina);
            } else if(requestCode == DISCIPLINA_EDIT_REQUEST) {
                disciplinaViewModel.update(disciplina);
            }

            Toast.makeText(view.getContext(), "Registro salvo", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        btAdicionar = view.findViewById(R.id.btAdicionar);
    }

    private void atribuirEventos() {
        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novo();
            }
        });
    }

    private void novo() {
        Intent intent = new Intent(view.getContext(), DisciplinaCadastrarActivity.class);
        startActivityForResult(intent, DISCIPLINA_ADD_REQUEST);
    }

    private void editar(Disciplina disciplina) {
        Intent intent = new Intent(view.getContext(), DisciplinaCadastrarActivity.class);
        intent.putExtra(DISCIPLINA, disciplina);
        startActivityForResult(intent, DISCIPLINA_EDIT_REQUEST);
    }
}