package unis.edu.crudalunos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import unis.edu.crudalunos.adapter.CursoAdapter;
import unis.edu.crudalunos.adapter.UsuarioAdapter;
import unis.edu.crudalunos.model.CursoViewModel;
import unis.edu.crudalunos.model.CursoWithDisciplinas;
import unis.edu.crudalunos.model.Usuario;
import unis.edu.crudalunos.model.UsuarioViewModel;


public class ProfessorSelecionarBottomSheet extends BottomSheetDialogFragment {

    private static final String USUARIO = "USUARIO";

    private TextInputLayout textInputLayoutPesquisar;
    private MaterialButton btCancelar;
    private RecyclerView recyclerView;
    private UsuarioAdapter adapter;

    private UsuarioViewModel usuarioViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_professor_selecionar, container, false);

        // SetUp views
        textInputLayoutPesquisar = view.findViewById(R.id.textInputPesquisar);
        btCancelar = view.findViewById(R.id.btCancelar);
        recyclerView = view.findViewById(R.id.recyclerView);

        // SetUp viewModel
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        // SetUp recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        // SetUp adapter
        adapter = new UsuarioAdapter();

        // SetUp events
        textInputLayoutPesquisar.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String nome = "%" + s.toString() + "%";
                usuarioViewModel.getByNome(nome).observe(getActivity(), (Observer<List<Usuario>>) usuarios -> {
                    if(usuarios == null) {
                        return;
                    }
                    adapter.setUsuarios(usuarios);
                    recyclerView.setAdapter(adapter);
                });
            }
        });
        btCancelar.setOnClickListener(v -> dismiss());

        adapter.setOnItemClickListener(usuario -> selecionar(usuario));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void selecionar(Usuario usuario) {
        String className = getActivity().getClass().getSimpleName();

        if(className.equals(DisciplinaActivity.class.getSimpleName()))
            ((DisciplinaActivity)getActivity()).selecionarProfessor(usuario);
        dismiss();
    }
}
