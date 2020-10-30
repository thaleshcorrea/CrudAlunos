package unis.edu.crudalunos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import unis.edu.crudalunos.enums.UsuarioTipo;

@Entity(tableName = "usuario")
public class Usuario implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    @NonNull
    public String getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public int getUsuarioTipo() {
        return usuarioTipo;
    }

    public void setUsuarioTipo(int usuarioTipo) {
        this.usuarioTipo = usuarioTipo;
    }

    public Usuario() {
    }

    @Ignore
    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String login;

    @NonNull
    private String senha;

    private String nome;

    @ColumnInfo(name = "usuario_tipo")
    private int usuarioTipo;

    @Embedded
    private Aluno aluno;
}
