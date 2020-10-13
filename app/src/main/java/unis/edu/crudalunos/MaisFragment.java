package unis.edu.crudalunos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MaisFragment extends Fragment {

    private Button btNovoCurso;

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mais, container, false);

        this.view = view;
        initViews();
        atribuirEventos();

        return view;
    }

    private void initViews() {
        btNovoCurso = view.findViewById(R.id.btNovoCurso);
    }

    private void atribuirEventos() {
        btNovoCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), CursoActivity.class);
                startActivity(intent);
            }
        });
    }
}