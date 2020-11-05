package unis.edu.crudalunos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unis.edu.crudalunos.R;
import unis.edu.crudalunos.model.Aula;

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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class AulaHolder extends RecyclerView.ViewHolder {

        public AulaHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
