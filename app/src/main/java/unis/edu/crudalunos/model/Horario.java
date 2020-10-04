package unis.edu.crudalunos.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "horarios")
public class Horario {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    @NonNull
    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(@NonNull String horaInicio) {
        this.horaInicio = horaInicio;
    }

    @NonNull
    public String getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(@NonNull String horaTermino) {
        this.horaTermino = horaTermino;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private int diaSemana;

    @NonNull
    private String horaInicio;

    @NonNull
    private String horaTermino;

    @NonNull
    private int disciplinaId;
}
