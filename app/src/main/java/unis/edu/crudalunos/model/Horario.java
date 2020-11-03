package unis.edu.crudalunos.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "horarios")
public class Horario implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public boolean isSegunda() {
        return segunda;
    }

    public void setSegunda(boolean segunda) {
        this.segunda = segunda;
    }

    public boolean isTerca() {
        return terca;
    }

    public void setTerca(boolean terca) {
        this.terca = terca;
    }

    public boolean isQuarta() {
        return quarta;
    }

    public void setQuarta(boolean quarta) {
        this.quarta = quarta;
    }

    public boolean isQuinta() {
        return quinta;
    }

    public void setQuinta(boolean quinta) {
        this.quinta = quinta;
    }

    public boolean isSexta() {
        return sexta;
    }

    public void setSexta(boolean sexta) {
        this.sexta = sexta;
    }

    public boolean isSabado() {
        return sabado;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public int getHoraTermino() {
        return horaTermino;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraTermino(int horaTermino) {
        this.horaTermino = horaTermino;
    }

    public int getMinutoInicio() {
        return minutoInicio;
    }

    public void setMinutoInicio(int minutoInicio) {
        this.minutoInicio = minutoInicio;
    }

    public int getMinutoTermino() {
        return minutoTermino;
    }

    public void setMinutoTermino(int minutoTermino) {
        this.minutoTermino = minutoTermino;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private boolean segunda;

    @NonNull
    private boolean terca;

    @NonNull
    private boolean quarta;

    @NonNull
    private boolean quinta;

    @NonNull
    private boolean sexta;

    @NonNull
    private boolean sabado;

    @NonNull
    private boolean domingo;

    @NonNull
    private int horaInicio;

    @NonNull
    private int minutoInicio;

    @NonNull
    private int horaTermino;

    @NonNull
    private int minutoTermino;

    @NonNull
    private long disciplinaId;
}
