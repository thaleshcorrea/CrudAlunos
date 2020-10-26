package unis.edu.crudalunos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import unis.edu.crudalunos.model.Curso;

public class CursoCadastrarFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_CURSOID = "cursoId";

    private View view;

    private Curso curso = null;
    private FloatingActionButton btSalvar;
    private TextInputLayout tbNome;

    public static CursoCadastrarFragment newInstance(Curso curso) {
        CursoCadastrarFragment fragment = new CursoCadastrarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURSOID, curso);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            curso = (Curso)getArguments().getSerializable(ARG_CURSOID);
        }
        if(curso != null) {
            PreencherValores();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_curso_cadastrar, container, false);
        this.view = view;

        initViews();

        return view;
    }

    private void initViews() {
        tbNome = view.findViewById(R.id.textInputNome);
        btSalvar = view.findViewById(R.id.btSalvar);
    }

    private void PreencherValores() {
        tbNome.getEditText().setText(curso.getNome());
    }
}