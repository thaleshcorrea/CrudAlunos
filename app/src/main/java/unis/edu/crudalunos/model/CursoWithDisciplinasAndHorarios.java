package unis.edu.crudalunos.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CursoWithDisciplinasAndHorarios {
    @Embedded
    private Curso curso;
    @Relation(
            entity = Disciplina.class,
            parentColumn = "cursoId",
            entityColumn = "disciplinaId"
    )
    List<CursoWithDisciplinas> cursoWithDisciplinas;
}
