package unis.edu.crudalunos;

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
import unis.edu.crudalunos.model.CursoViewModel;
import unis.edu.crudalunos.model.CursoWithDisciplinas;

public class CursoSelecionarBottomSheet extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private TextInputLayout textInputLayoutPesquisar;
    private MaterialButton btCancelar;

    private CursoViewModel cursoViewModel;
    private CursoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_curso_selecionar, container, false);

        // SetUp views
        recyclerView = view.findViewById(R.id.recyclerView);
        textInputLayoutPesquisar = view.findViewById(R.id.textInputPesquisar);
        btCancelar = view.findViewById(R.id.btCancelar);

        // SetUp viewModel
        cursoViewModel = new ViewModelProvider(this).get(CursoViewModel.class);

        // SetUp recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        // SetUp adapter
        adapter = new CursoAdapter();
        adapter.setOnClickListener(curso -> {
            selecionar(curso);
            dismiss();
        });

        // SetUp events
        textInputLayoutPesquisar.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nome = "%" + s.toString() + "%";
                cursoViewModel.getByNome(nome).observe(getActivity(), (Observer<List<CursoWithDisciplinas>>) cursos -> {
                    if(cursos == null) {
                        return;
                    }
                    adapter.setCursos(cursos);
                    recyclerView.setAdapter(adapter);
                });
            }
        });
        btCancelar.setOnClickListener(v -> dismiss());

        return view;
    }

    private void selecionar(CursoWithDisciplinas curso) {
        String className = getActivity().getClass().getSimpleName();

        if(className.equals(DisciplinaActivity.class.getSimpleName()))
            ((DisciplinaActivity)getActivity()).selecionarCurso(curso.curso);
    }
}
