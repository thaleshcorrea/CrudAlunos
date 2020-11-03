package unis.edu.crudalunos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import unis.edu.crudalunos.R;
import unis.edu.crudalunos.enums.Semana;
import unis.edu.crudalunos.model.CursoWithDisciplinas;
import unis.edu.crudalunos.model.Horario;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.HorarioHolder> {

    private List<Horario> horarioList = new ArrayList<>();
    private OnItemClickListener listener;

    private ViewGroup parent;

    @NonNull
    @Override
    public HorarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horario_item, parent, false);

        this.parent = parent;
        return new HorarioAdapter.HorarioHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioAdapter.HorarioHolder holder, int position) {
        Horario horario = horarioList.get(position);
        String horaInicio = String.format("%02d:%02d", horario.getHoraInicio(), horario.getMinutoInicio());
        String horaFinal = String.format("%02d:%02d", horario.getHoraTermino(), horario.getMinutoTermino());
        holder.textViewHorario.setText(horaInicio + "-" + horaFinal);
        holder.chipDomingo.setChecked(horario.isDomingo());
        holder.chipSegunda.setChecked(horario.isSegunda());
        holder.chipTerca.setChecked(horario.isTerca());
        holder.chipQuarta.setChecked(horario.isQuarta());
        holder.chipQuinta.setChecked(horario.isQuinta());
        holder.chipSexta.setChecked(horario.isSexta());
        holder.chipSabado.setChecked(horario.isSabado());
    }

    @LayoutRes
    protected int getChipGroupItem() {
        return R.layout.chip;
    }

    private void createCheap(ChipGroup chipGroup, String text) {
        Chip chip =
                (Chip) LayoutInflater.from(parent.getContext()).inflate(getChipGroupItem(), chipGroup, false);
        chip.setText(text);
        chip.setCloseIconVisible(false);
        chip.setCheckable(false);
    }

    public void setHorarioList(List<Horario> horarios) {
        this.horarioList = horarios;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return horarioList.size();
    }

    class HorarioHolder extends RecyclerView.ViewHolder {

        private TextView textViewHorario;
        private Chip chipDomingo;
        private Chip chipSegunda;
        private Chip chipTerca;
        private Chip chipQuarta;
        private Chip chipQuinta;
        private Chip chipSexta;
        private Chip chipSabado;

        public HorarioHolder(@NonNull View itemView) {
            super(itemView);

            textViewHorario = itemView.findViewById(R.id.textViewHorario);
            chipDomingo = itemView.findViewById(R.id.chipDomingo);
            chipSegunda = itemView.findViewById(R.id.chipSegunda);
            chipTerca = itemView.findViewById(R.id.chipTerca);
            chipQuarta = itemView.findViewById(R.id.chipQuarta);
            chipQuinta = itemView.findViewById(R.id.chipQuinta);
            chipSexta = itemView.findViewById(R.id.chipSexta);
            chipSabado = itemView.findViewById(R.id.chipSabado);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(horarioList.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Horario horario);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
