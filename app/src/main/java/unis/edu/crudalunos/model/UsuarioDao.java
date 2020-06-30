package unis.edu.crudalunos.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuario WHERE id = :id")
    Usuario getById(int id);

    @Query("SELECT * FROM usuario WHERE nome = :nome AND senha = :senha")
    Usuario Login(String nome, String senha);

    @Query("SELECT * FROM usuario WHERE nome = :nome")
    Usuario getByNome(String nome);

    @Insert
    void insert(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Update
    void update(Usuario usuario);
}
