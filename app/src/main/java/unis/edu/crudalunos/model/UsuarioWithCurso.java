package unis.edu.crudalunos.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class UsuarioWithCurso implements Serializable {
    @Embedded
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    @Relation(
            parentColumn = "id",
            entityColumn = "cursoId",
            associateBy = @Junction(UsuarioCurso.class)
    )
    private List<Curso> cursos;
}
