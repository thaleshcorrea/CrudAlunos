package unis.edu.crudalunos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import unis.edu.crudalunos.helpers.app_parameters;
import unis.edu.crudalunos.model.DisciplinaViewModel;
import unis.edu.crudalunos.model.Usuario;
import unis.edu.crudalunos.model.UsuarioCursoViewModel;

public class AlunoHomeFragment extends Fragment {

    // Views
    private TextView textViewBemVindo;

    private UsuarioCursoViewModel usuarioCursoViewModel;
    private DisciplinaViewModel disciplinaViewModel;

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

        return view;
    }
}