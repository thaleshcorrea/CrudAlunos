package unis.edu.crudalunos.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cursos")
public class Curso implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String nome;
}
