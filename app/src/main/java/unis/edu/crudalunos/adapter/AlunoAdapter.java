package unis.edu.crudalunos.adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unis.edu.crudalunos.MainActivity;
import unis.edu.crudalunos.R;
import unis.edu.crudalunos.model.Aluno;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoHolder> {
    private List<Aluno> alunos = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AlunoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aluno_item, parent, false);
        return new AlunoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoHolder holder, int position) {
        Aluno alunoAtual = alunos.get(position);
        holder.textViewTitulo.setText(alunoAtual.getNome());
        holder.textViewEmail.setText(alunoAtual.getEmail());
        holder.textViewTelefone.setText(alunoAtual.getTelefone());

        byte[] byteAluno = alunoAtual.getFoto();
        if (byteAluno != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteAluno, 0, byteAluno.length);
            holder.imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
        notifyDataSetChanged();
    }

    public Aluno getAlunoAt(int position) {
        return alunos.get(position);
    }

    class AlunoHolder extends RecyclerView.ViewHolder {

        public static final int REQUEST_CALL = 1;
        private TextView textViewTitulo;
        private TextView textViewEmail;
        private TextView textViewTelefone;
        private ImageView imageView;

        private Button btLigar;
        private Button btEmail;

        public AlunoHolder(View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.text_view_titulo);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewTelefone = itemView.findViewById(R.id.textViewTelefone);
            imageView = itemView.findViewById(R.id.imageView);
            btLigar = itemView.findViewById(R.id.btLigar);
            btEmail = itemView.findViewById(R.id.btEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(alunos.get(position));
                }
            });

            btEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Aluno aluno = alunos.get(position);

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{aluno.getEmail()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, aluno.getNome());
                    intent.putExtra(Intent.EXTRA_TEXT, "Email para " + aluno.getNome());
                    v.getContext().startActivity(Intent.createChooser(intent, ""));
                }
            });

            btLigar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Aluno aluno = alunos.get(position);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Aluno aluno);
    }

    public void setOnImteClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
