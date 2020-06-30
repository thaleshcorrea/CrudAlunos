package unis.edu.crudalunos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import unis.edu.crudalunos.adapter.AlunoAdapter;
import unis.edu.crudalunos.model.Aluno;
import unis.edu.crudalunos.model.AlunoViewModel;

public class AlunoFragment extends Fragment {

    static final String ALUNO = "ALUNO";
    private static final int ALUNO_ADD_REQUEST = 1;
    private static final int ALUNO_EDIT_REQUEST = 2;

    private AlunoViewModel alunoViewModel;

    private FloatingActionButton fbAdd;

    private View _view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aluno, container, false);

        this._view = view;

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        final AlunoAdapter adapter = new AlunoAdapter();
        recyclerView.setAdapter(adapter);

        alunoViewModel = new ViewModelProvider(this).get(AlunoViewModel.class);
        alunoViewModel.getAll().observe((LifecycleOwner) _view.getContext(), new Observer<List<Aluno>>() {
            @Override
            public void onChanged(List<Aluno> alunos) {
                adapter.setAlunos(alunos);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                alunoViewModel.delete(adapter.getAlunoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(_view.getContext(), "Aluno removido", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnImteClickListener(new AlunoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Aluno aluno) {
                editar(aluno);
            }
        });

        initViews();
        registrarEventos();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Aluno aluno = (Aluno) data.getSerializableExtra(ALUNO);

            if (requestCode == ALUNO_ADD_REQUEST) {
                alunoViewModel.insert(aluno);
            } else if (requestCode == ALUNO_EDIT_REQUEST) {
                alunoViewModel.update(aluno);
            }

            Toast.makeText(_view.getContext(), "Registro salvo", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        fbAdd = _view.findViewById(R.id.fbAdd);
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
        Intent intent = new Intent(_view.getContext(), AlunoCadastrar.class);
        startActivityForResult(intent, ALUNO_ADD_REQUEST);
    }

    private void editar(Aluno aluno) {
        Intent intent = new Intent(_view.getContext(), AlunoCadastrar.class);
        intent.putExtra(ALUNO, aluno);
        startActivityForResult(intent, ALUNO_EDIT_REQUEST);
    }
}