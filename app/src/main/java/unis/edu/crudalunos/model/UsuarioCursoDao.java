package unis.edu.crudalunos.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface UsuarioCursoDao {
    @Transaction
    @Query("SELECT * FROM usuario WHERE id = :id")
    UsuarioWithCurso getUsuarioWithCurso(int id);

    @Insert
    void insert(List<UsuarioCurso> usuarioCursoList);

    @Query("DELETE FROM usuario_curso WHERE id = :usuarioId")
    void delete(int usuarioId);
}
