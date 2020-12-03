package unis.edu.crudalunos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import unis.edu.crudalunos.helpers.app_parameters;
import unis.edu.crudalunos.model.Usuario;

public class AlunoHomeFragment extends Fragment {

    private MaterialCardView materialCardView;
    private TextView textViewDisciplina;
    private TextView textViewHorario;
    private TextView textViewStatus;

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

        // SetUp views
        materialCardView = view.findViewById(R.id.materialCardViewProximaAula);
        textViewDisciplina = view.findViewById(R.id.textViewDisciplina);
        textViewHorario = view.findViewById(R.id.textViewHorario);
        textViewStatus = view.findViewById(R.id.textViewStatus);

        Usuario usuario = app_parameters.getLoggedUser();


        return view;
    }
}