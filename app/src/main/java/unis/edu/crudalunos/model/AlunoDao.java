package unis.edu.crudalunos.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlunoDao {
    @Query("SELECT * FROM aluno")
    LiveData<List<Aluno>> getAll();

    @Query("SELECT * FROM aluno WHERE id = :id")
    Aluno getById(int id);

    @Insert
    void insert(Aluno aluno);

    @Delete
    void delete(Aluno aluno);

    @Update
    void update(Aluno aluno);
}
