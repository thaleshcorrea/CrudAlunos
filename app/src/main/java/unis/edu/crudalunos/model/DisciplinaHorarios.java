package unis.edu.crudalunos.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DisciplinaHorarios {
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    @Embedded
    private Disciplina disciplina;
    @Relation(
            parentColumn = "id",
            entityColumn = "disciplinaId"
    )
    private List<Horario> horarios;
}
