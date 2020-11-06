package unis.edu.crudalunos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import unis.edu.crudalunos.R;
import unis.edu.crudalunos.model.Aula;
import unis.edu.crudalunos.model.AulaWithDisciplina;

public class AulaHorarioAdapter extends RecyclerView.Adapter<AulaHorarioAdapter.AulaHolder> {
    List<Aula> aulas = new ArrayList<>();

    @NonNull
    @Override
    public AulaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aula_horario_item, parent, false);
        return new AulaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AulaHolder holder, int position) {
        Aula aula = aulas.get(position);
//        holder.textViewDisciplina.setText(aula.getDisciplina().getDescricao());
//        holder.textViewHorario.setText(aula.getHoraInicio() + "-" + aula.getHoraTermino());

        Calendar calendar = Calendar.getInstance();
        holder.textViewSemana.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
    }

    @Override
    public int getItemCount() {
        return aulas.size();
    }

    class AulaHolder extends RecyclerView.ViewHolder {

        private TextView textViewHorario;
        private TextView textViewDisciplina;
        private TextView textViewSemana;

        public AulaHolder(@NonNull View itemView) {
            super(itemView);

            textViewHorario = itemView.findViewById(R.id.textViewHorario);
            textViewDisciplina = itemView.findViewById(R.id.textViewDisciplina);
            textViewSemana = itemView.findViewById(R.id.textViewSemana);
        }
    }
}
