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
    @Embedded
    private Curso curso;

    public List<DisciplinaHorarios> getDisciplinaHorarios() {
        return disciplinaHorarios;
    }

    public void setDisciplinaHorarios(List<DisciplinaHorarios> disciplinaHorarios) {
        this.disciplinaHorarios = disciplinaHorarios;
    }

    @Relation(
            entity = Disciplina.class,
            parentColumn = "cursoId",
            entityColumn = "disciplinaId"
    )
    List<DisciplinaHorarios> disciplinaHorarios;
}
