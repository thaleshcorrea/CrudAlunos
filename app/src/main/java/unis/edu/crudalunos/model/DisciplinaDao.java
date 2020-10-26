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
public interface DisciplinaDao {
    @Query("SELECT * FROM disciplinas")
    LiveData<List<Disciplina>> getAll();

    @Query("SELECT * FROM disciplinas WHERE cursoId = :cursoId")
    LiveData<List<Disciplina>> getByCurso(int cursoId);

    @Transaction
    @Query("SELECT * FROM disciplinas")
    List<DisciplinaHorarios> getDisciplinaComHorarios();

    @Insert
    void insert(Disciplina disciplina);

    @Delete
    void delete(Disciplina disciplina);

    @Update
    void update(Disciplina disciplina);
}
