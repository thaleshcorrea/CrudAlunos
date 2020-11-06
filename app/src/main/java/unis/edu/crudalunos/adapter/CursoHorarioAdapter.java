package unis.edu.crudalunos.adapter;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unis.edu.crudalunos.R;
import unis.edu.crudalunos.model.CursoWithDisciplinasAndHorarios;
import unis.edu.crudalunos.model.Disciplina;
import unis.edu.crudalunos.model.DisciplinaHorarios;

public class CursoHorarioAdapter extends RecyclerView.Adapter<CursoHorarioAdapter.CursoHorarioHolder> {

    List<CursoWithDisciplinasAndHorarios> cursoWithDisciplinasAndHorariosList = new ArrayList<>();
    private OnItemClickListener listener;
    DisciplinaAdapter disciplinaAdapter;

    @NonNull
    @Override
    public CursoHorarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.curso_horario_item, parent, false);
        return new CursoHorarioHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoHorarioHolder holder, int position) {
        CursoWithDisciplinasAndHorarios cursoWithDisciplinasAndHorarios = cursoWithDisciplinasAndHorariosList.get(position);

        holder.textViewCurso.setText(cursoWithDisciplinasAndHorarios.getCurso().getNome());
        disciplinaAdapter = new DisciplinaAdapter();

        List<Disciplina> disciplinas = new ArrayList<>();
        for (DisciplinaHorarios disciplinaHorarios : cursoWithDisciplinasAndHorarios.getDisciplinaHorarios()) {
            disciplinas.add(disciplinaHorarios.getDisciplina());
        }
        disciplinaAdapter.setDisciplinas(disciplinas);
        holder.recyclerView.setAdapter(disciplinaAdapter);
    }

    public void setCursoWithDisciplinasAndHorariosList(List<CursoWithDisciplinasAndHorarios> cursoWithDisciplinasAndHorariosList) {
        this.cursoWithDisciplinasAndHorariosList = cursoWithDisciplinasAndHorariosList;
    }

    @Override
    public int getItemCount() {
        return cursoWithDisciplinasAndHorariosList.size();
    }

    class CursoHorarioHolder extends RecyclerView.ViewHolder {

        private TextView textViewCurso;
        private RecyclerView recyclerView;
        private ConstraintLayout expandableView;
        private Button arrowBtn;
        private CardView cardView;

        public CursoHorarioHolder(@NonNull View itemView) {
            super(itemView);

            textViewCurso = itemView.findViewById(R.id.textViewCursoName);

            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerView.setHasFixedSize(true);

            expandableView = itemView.findViewById(R.id.expandableView);
            arrowBtn = itemView.findViewById(R.id.arrowBtn);
            cardView = itemView.findViewById(R.id.cardView);

            arrowBtn.setOnClickListener(v -> {
                if (expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    arrowBtn.setBackgroundResource(R.drawable.ic_arrow_up);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    arrowBtn.setBackgroundResource(R.drawable.ic_arrow_down);
                }
            });

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(cursoWithDisciplinasAndHorariosList.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CursoWithDisciplinasAndHorarios cursoWithDisciplinasAndHorarios);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
