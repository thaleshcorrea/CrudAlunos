package unis.edu.crudalunos.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class AulaWithDisciplina {
    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Embedded
    private Aula aula;
    @Relation(
            parentColumn = "aulaId",
            entityColumn = "disciplinaId"
    )
    private Disciplina disciplina;
}
