package unis.edu.crudalunos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unis.edu.crudalunos.R;
import unis.edu.crudalunos.model.CursoWithDisciplinasAndHorarios;

public class CursoHorarioAdapter extends RecyclerView.Adapter<CursoHorarioAdapter.CursoHorarioHolder> {

    List<CursoWithDisciplinasAndHorarios> cursoWithDisciplinasAndHorariosList = new ArrayList<>();
    private OnItemClickListener listener;

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
    }

    @Override
    public int getItemCount() {
        return cursoWithDisciplinasAndHorariosList.size();
    }

    class CursoHorarioHolder extends RecyclerView.ViewHolder {

        private TextView textViewCurso;
        private RecyclerView recyclerView;

        public CursoHorarioHolder(@NonNull View itemView) {
            super(itemView);

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
