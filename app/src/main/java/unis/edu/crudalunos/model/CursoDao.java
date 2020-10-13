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
public interface CursoDao {
    @Query("SELECT * FROM cursos")
    LiveData<List<Curso>> getAll();

    @Transaction
    @Query("SELECT * FROM cursos WHERE id = :id")
    LiveData<CursoDisciplinas> getCursoComDisciplinas(int id);

    @Insert
    void insert(Curso curso);

    @Delete
    void delete(Curso curso);

    @Update
    void update(Curso curso);
}
