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
import unis.edu.crudalunos.model.Curso;
import unis.edu.crudalunos.model.CursoWithDisciplinas;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoHolder> {
    private List<CursoWithDisciplinas> cursos = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CursoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.curso_item,parent,false);
        return new CursoAdapter.CursoHolder(itemView);
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
        public static final int REQUEST_CALL = 1;
        private TextView tbCurso;

        public CursoHolder(@NonNull View itemView) {
            super(itemView);

            tbCurso = itemView.findViewById(R.id.tbCurso);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(cursos.get(position));
                    }
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
