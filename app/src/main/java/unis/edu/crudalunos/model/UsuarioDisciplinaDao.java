package unis.edu.crudalunos.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsuarioDisciplinaDao {
    @Transaction
    @Query("SELECT * FROM usuario WHERE id = :id")
    LiveData<List<UsuarioWithDisciplina>> getUsuarioWithDisciplina(int id);

    @Insert
    void insert(UsuarioDisciplina usuarioDisciplina);

    @Delete
    void delete(UsuarioDisciplina usuarioDisciplina);

    @Update
    void update(UsuarioDisciplina usuarioDisciplina);
}
