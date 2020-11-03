package unis.edu.crudalunos.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "disciplinas")
public class Disciplina implements Serializable {
    public Disciplina() {}

    public Disciplina(String nome) {
        this.descricao = nome;
    }

    public long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    @NonNull
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NonNull String descricao) {
        this.descricao = descricao;
    }

    public long getCursoId() {
        return cursoId;
    }

    public void setCursoId(long cursoId) {
        this.cursoId = cursoId;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    @PrimaryKey(autoGenerate = true)
    private long disciplinaId;

    @NonNull
    private String descricao;

    @NonNull
    @ForeignKey(entity = Curso.class, parentColumns = "cursoId", childColumns = "cursoId")
    private long cursoId;

    @Ignore
    private List<Horario> horarios;
}
