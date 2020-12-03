package unis.edu.crudalunos.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "aluno_disciplina",
        primaryKeys = {"disciplinaId", "usuarioId"},
        foreignKeys = {
                @ForeignKey(entity = Disciplina.class, parentColumns = "disciplinaId", childColumns = "disciplinaId"),
                @ForeignKey(entity = Usuario.class, parentColumns = "id", childColumns = "usuarioId")
        })
public class UsuarioDisciplina {
    public long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    private long disciplinaId;
    private int usuarioId;
}
