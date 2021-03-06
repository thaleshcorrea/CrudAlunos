package unis.edu.crudalunos.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.sql.Time;
import java.sql.Date;

@Entity
public class Aula {
    public long getAulaId() {
        return aulaId;
    }

    public void setAulaId(long aulaId) {
        this.aulaId = aulaId;
    }

    @NonNull
    public Date getData() {
        return data;
    }

    public void setData(@NonNull Date data) {
        this.data = data;
    }

    @NonNull
    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(@NonNull Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    @NonNull
    public Time getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(@NonNull Time horaTermino) {
        this.horaTermino = horaTermino;
    }

    public long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @PrimaryKey
    private long aulaId;

    @NonNull
    private Date data;

    @NonNull
    private Time horaInicio;

    @NonNull
    private Time horaTermino;

    @NonNull
    private long disciplinaId;

    private int status;
}
