package unis.edu.crudalunos.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UsuarioWithDisciplina {
    @Embedded
    public Usuario usuario;
    @Relation(
            parentColumn = "id",
            entityColumn = "disciplinaId",
            associateBy = @Junction(UsuarioDisciplina.class)
    )
    public List<Disciplina> disciplinaList;
}
