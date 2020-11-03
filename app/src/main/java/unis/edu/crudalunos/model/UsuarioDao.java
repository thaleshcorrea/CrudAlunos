package unis.edu.crudalunos.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import unis.edu.crudalunos.enums.UsuarioTipo;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuario WHERE id = :id")
    Usuario getById(int id);

    @Query("SELECT * FROM usuario WHERE login = :login AND senha = :senha")
    Usuario Login(String login, String senha);

    @Query("SELECT * FROM usuario WHERE nome = :login")
    Usuario getByLogin(String login);

    @Query("SELECT * FROM usuario where usuario_tipo = 0")
    LiveData<List<Usuario>> getAllAlunos();

    @Query("SELECT * FROM usuario WHERE nome LIKE :nome")
    LiveData<List<Usuario>> getByNome(String nome);

    @Insert
    void insert(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Update
    void update(Usuario usuario);
}
