package unis.edu.crudalunos.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CursoDisciplinas {
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Embedded
    private Curso curso;

    @Relation(
            parentColumn = "id",
            entityColumn = "cursoId"
    )
    private List<Disciplina> disciplinas;
}
