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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unis.edu.crudalunos.R;
import unis.edu.crudalunos.model.CursoWithDisciplinas;

public class CursoWithDisciplinaAdapter extends RecyclerView.Adapter<CursoWithDisciplinaAdapter.CursoHolder> {
    private List<CursoWithDisciplinas> cursos = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CursoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.curso_item,parent,false);
        return new CursoWithDisciplinaAdapter.CursoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoHolder holder, int position) {
        CursoWithDisciplinas curso = cursos.get(position);
        holder.tbCurso.setText(curso.curso.getNome());
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }

    public void setCursos(List<CursoWithDisciplinas> cursos) {
        this.cursos = cursos;
        notifyDataSetChanged();
    }

    public CursoWithDisciplinas getCursoAt(int position) {
        return cursos.get(position);
    }

    class CursoHolder extends RecyclerView.ViewHolder{
        private TextView tbCurso;

        public CursoHolder(@NonNull View itemView) {
            super(itemView);

            tbCurso = itemView.findViewById(R.id.tbCurso);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(cursos.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CursoWithDisciplinas curso);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
