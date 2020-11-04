package unis.edu.crudalunos.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuario_curso",
        primaryKeys = {"id", "cursoId"})
public class UsuarioCurso {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCursoId() {
        return cursoId;
    }

    public void setCursoId(long cursoId) {
        this.cursoId = cursoId;
    }

    private int id;
    private long cursoId;

}
