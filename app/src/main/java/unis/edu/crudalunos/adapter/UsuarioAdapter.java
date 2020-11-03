package unis.edu.crudalunos.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unis.edu.crudalunos.R;
import unis.edu.crudalunos.model.Aluno;
import unis.edu.crudalunos.model.Usuario;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder> {
    private List<Usuario> usuarios = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public UsuarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aluno_item, parent, false);
        return new UsuarioHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioHolder holder, int position) {
        Usuario usuarioAtual = usuarios.get(position);
        holder.textViewTitulo.setText(usuarioAtual.getNome());
        if(usuarioAtual.getAluno() != null) {
            holder.textViewEmail.setText(usuarioAtual.getAluno().getEmail());
            holder.textViewTelefone.setText(usuarioAtual.getAluno().getTelefone());
            byte[] byteAluno = usuarioAtual.getAluno().getFoto();
            if (byteAluno != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteAluno, 0, byteAluno.length);
                holder.imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        notifyDataSetChanged();
    }

    public Usuario getAlunoAt(int position) {
        return usuarios.get(position);
    }

    class UsuarioHolder extends RecyclerView.ViewHolder {

        public static final int REQUEST_CALL = 1;
        private TextView textViewTitulo;
        private TextView textViewEmail;
        private TextView textViewTelefone;
        private ImageView imageView;

        public UsuarioHolder(View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.text_view_titulo);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewTelefone = itemView.findViewById(R.id.textViewTelefone);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION)
                    listener.onItemClick(usuarios.get(position));
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Usuario usuario);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
