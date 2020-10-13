package unis.edu.crudalunos;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.List;

import unis.edu.crudalunos.adapter.DisciplinaAdapter;
import unis.edu.crudalunos.adapter.UsuarioAdapter;
import unis.edu.crudalunos.model.CursoDisciplinas;
import unis.edu.crudalunos.model.CursoViewModel;
import unis.edu.crudalunos.model.Disciplina;
import unis.edu.crudalunos.model.Usuario;

public class CursoDisciplinasFragment extends Fragment {

    private View view;
    private CursoViewModel cursoViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curso_disciplinas, container, false);
        this.view = view;

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        final DisciplinaAdapter disciplinaAdapter = new DisciplinaAdapter();
        recyclerView.setAdapter(disciplinaAdapter);

        cursoViewModel= new ViewModelProvider(this).get(CursoViewModel.class);
        cursoViewModel.getCursoDisciplinas(1).observe((LifecycleOwner) view.getContext(), new Observer<List<CursoDisciplinas>>() {
            @Override
            public void onChanged(List<CursoDisciplinas> cursoDisciplinas) {
                disciplinaAdapter.setDisciplinas(cursoDisciplinas);
            }
        });

        disciplinaAdapter.setOnClickListener(new DisciplinaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CursoDisciplinas cursoDisciplinas) {
                editar(cursoDisciplinas.getDisciplinas());
            }
        });

        return view;
    }

    private void editar(Disciplina disciplina) {

    }
}