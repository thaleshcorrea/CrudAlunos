package unis.edu.crudalunos;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import unis.edu.crudalunos.adapter.CursoHorarioAdapter;
import unis.edu.crudalunos.helpers.app_parameters;
import unis.edu.crudalunos.model.Curso;
import unis.edu.crudalunos.model.CursoViewModel;
import unis.edu.crudalunos.model.CursoWithDisciplinas;
import unis.edu.crudalunos.model.CursoWithDisciplinasAndHorarios;
import unis.edu.crudalunos.model.Disciplina;
import unis.edu.crudalunos.model.DisciplinaViewModel;
import unis.edu.crudalunos.model.Usuario;
import unis.edu.crudalunos.model.UsuarioCursoViewModel;
import unis.edu.crudalunos.model.UsuarioWithCurso;

public class AlunoHomeFragment extends Fragment {

    private TextView textViewBemVindo;

    private UsuarioCursoViewModel usuarioCursoViewModel;
    private CursoViewModel cursoViewModel;
    private CursoHorarioAdapter cursoHorarioAdapter;
    private RecyclerView recyclerView;

    public AlunoHomeFragment() {

    }

    public static AlunoHomeFragment newInstance() {
        return new AlunoHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aluno_home, container, false);

        Usuario usuario = app_parameters.getLoggedUser();
        textViewBemVindo = view.findViewById(R.id.textViewBemVindo);
        textViewBemVindo.setText(getString(R.string.bem_vindo) + ", " + usuario.getNome());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        // SetUp viewModel
        usuarioCursoViewModel = new ViewModelProvider(this).get(UsuarioCursoViewModel.class);
        cursoViewModel = new ViewModelProvider(this).get(CursoViewModel.class);

        getUsuarioWithCursos(usuario.getId());

        return view;
    }

    private void getUsuarioWithCursos(int usuarioId) {
        usuarioCursoViewModel.getUsuarioWithCurso(usuarioId, output -> {
            if(output == null) {
                return;
            }

            UsuarioWithCurso usuarioWithCursos = (UsuarioWithCurso)output;
            List<Long> cursoIds = new ArrayList<>();
            for(Curso curso : usuarioWithCursos.getCursos()) {
                cursoIds.add(curso.getCursoId());
            }

            cursoViewModel.getCursoWithDisciplinasAndHorarios(cursoIds, output1 -> {
                if(output1 == null) {
                    return;
                }
                List<CursoWithDisciplinasAndHorarios> cursoWithDisciplinasAndHorarios = (List<CursoWithDisciplinasAndHorarios>)output1;

                cursoHorarioAdapter = new CursoHorarioAdapter();
                cursoHorarioAdapter.setCursoWithDisciplinasAndHorariosList(cursoWithDisciplinasAndHorarios);
                recyclerView.setAdapter(cursoHorarioAdapter);
            });
        });
    }
}