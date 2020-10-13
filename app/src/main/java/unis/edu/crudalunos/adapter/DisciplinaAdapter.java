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
import unis.edu.crudalunos.model.CursoDisciplinas;
import unis.edu.crudalunos.model.Disciplina;
import unis.edu.crudalunos.model.Usuario;

public class DisciplinaAdapter extends RecyclerView.Adapter<DisciplinaAdapter.DisciplinaHolder>{
    private List<CursoDisciplinas> disciplinas = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public DisciplinaAdapter.DisciplinaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.disciplina_item,parent,false);
        return new DisciplinaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DisciplinaAdapter.DisciplinaHolder holder, int position) {
        CursoDisciplinas cursoDisciplinas = disciplinas.get(position);
        Disciplina disciplina = cursoDisciplinas.getDisciplinas().get(position);
        holder.tbNome.setText(disciplina.getDescricao());
    }

    @Override
    public int getItemCount() {
        return disciplinas.size();
    }

    public void setDisciplinas(List<CursoDisciplinas> cursoDisciplinas) {
        this.disciplinas = cursoDisciplinas;
        notifyDataSetChanged();
    }

    class DisciplinaHolder extends RecyclerView.ViewHolder{
        public static final int REQUEST_CALL = 1;
        private TextView tbNome;

        public DisciplinaHolder(@NonNull View itemView) {
            super(itemView);

            tbNome = itemView.findViewById(R.id.tbNomeDisciplina);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(disciplinas.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CursoDisciplinas cursoDisciplinas);
    }

    public void setOnClickListener(DisciplinaAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
