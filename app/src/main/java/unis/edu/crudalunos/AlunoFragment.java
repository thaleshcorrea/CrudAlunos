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

import unis.edu.crudalunos.adapter.UsuarioAdapter;
import unis.edu.crudalunos.model.Aluno;
import unis.edu.crudalunos.model.Usuario;
import unis.edu.crudalunos.model.UsuarioViewModel;

public class AlunoFragment extends Fragment {

    static final String USUARIO = "USUARIO";
    private static final int ALUNO_ADD_REQUEST = 1;
    private static final int ALUNO_EDIT_REQUEST = 2;

    private UsuarioViewModel usuarioViewModel;

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

        final UsuarioAdapter adapter = new UsuarioAdapter();
        recyclerView.setAdapter(adapter);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.getAllAlunos().observe((LifecycleOwner) _view.getContext(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                adapter.setUsuarios(usuarios);
            }
        });

        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                usuarioViewModel.delete(adapter.getAlunoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(_view.getContext(), "Aluno removido", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);*/

//        adapter.setOnImteClickListener(new UsuarioAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Usuario usuario) {
//                editar(usuario);
//            }
//        });

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
            Usuario usuario = (Usuario) data.getSerializableExtra(USUARIO);

            if (requestCode == ALUNO_ADD_REQUEST) {
                usuarioViewModel.insert(usuario);
            } else if (requestCode == ALUNO_EDIT_REQUEST) {
                usuarioViewModel.update(usuario);
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

    private void editar(Usuario usuario) {
        Intent intent = new Intent(_view.getContext(), AlunoCadastrar.class);
        intent.putExtra(USUARIO, usuario);
        startActivityForResult(intent, ALUNO_EDIT_REQUEST);
    }
}