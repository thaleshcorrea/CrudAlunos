package unis.edu.crudalunos.model;

import android.media.Image;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "aluno")
public class Aluno implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Aluno() {
    }

    public Aluno(String nome, String email, String telefone, byte[] foto) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.foto = foto;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nome;

    private String email;

    private String telefone;

    private byte[] foto;
}
