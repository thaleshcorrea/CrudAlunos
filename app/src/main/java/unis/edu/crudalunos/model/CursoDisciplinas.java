package unis.edu.crudalunos.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CursoDisciplinas {
    @Embedded
    private Curso curso;

    @Relation(
            parentColumn = "id",
            entityColumn = "cursoId"
    )
    private List<Disciplina> disciplinas;
}
