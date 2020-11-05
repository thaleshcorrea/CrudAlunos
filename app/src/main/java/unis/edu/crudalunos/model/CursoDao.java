package unis.edu.crudalunos.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CursoDao {
    @Transaction
    @Query("SELECT * FROM cursos")
    LiveData<List<CursoWithDisciplinas>> getAll();

    @Transaction
    @Query("SELECT * FROM cursos WHERE cursoId = :cursoId")
    LiveData<List<CursoWithDisciplinas>> getById(long cursoId);

    @Transaction
    @Query("SELECT * FROM cursos WHERE nome LIKE :nome")
    LiveData<List<CursoWithDisciplinas>> getByNome(String nome);

    @Transaction
    @Query("SELECT * FROM cursos WHERE cursoId IN (:cursoIds)")
    List<CursoWithDisciplinasAndHorarios> getCursoWithDisciplinasAndHorarios(List<Long> cursoIds);

    @Insert
    long insert(Curso curso);

    @Delete
    void delete(Curso curso);

    @Update
    void update(Curso curso);
}