package unis.edu.crudalunos.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CursoWithDisciplinasAndHorarios {

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<DisciplinaHorarios> getCursoWithDisciplinas() {
        return cursoWithDisciplinas;
    }

    public void setCursoWithDisciplinas(List<DisciplinaHorarios> cursoWithDisciplinas) {
        this.cursoWithDisciplinas = cursoWithDisciplinas;
    }

    @Embedded
    private Curso curso;

    @Relation(
            entity = Disciplina.class,
            parentColumn = "cursoId",
            entityColumn = "cursoId"
    )
    List<DisciplinaHorarios> cursoWithDisciplinas;
}
