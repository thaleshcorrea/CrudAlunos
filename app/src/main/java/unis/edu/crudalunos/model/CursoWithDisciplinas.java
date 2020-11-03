package unis.edu.crudalunos.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class CursoWithDisciplinas implements Serializable {
    @Embedded
    public Curso curso;

    @Relation(
            parentColumn = "cursoId",
            entityColumn = "disciplinaId"
    )
    public List<Disciplina> disciplinaList;
}
