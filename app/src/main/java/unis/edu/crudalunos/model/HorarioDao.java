package unis.edu.crudalunos.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HorarioDao {
    @Query("SELECT * FROM horarios")
    LiveData<List<Horario>> getAll();

    @Query("SELECT * FROM horarios WHERE disciplinaId = :disciplinaId")
    List<Horario> getByDisciplina(long disciplinaId);

    @Insert
    void insert(Horario horario);

    @Insert
    void insertAll(List<Horario> horarios);

    @Delete
    void delete(Horario horario);

    @Update
    void update(Horario horario);
}
