package unis.edu.crudalunos.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {
        Usuario.class,
        Curso.class,
        Horario.class,
        Disciplina.class,
        UsuarioCurso.class,
        Aula.class,
        AulaAlunos.class,
}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();
    public abstract CursoDao cursoDao();
    public abstract DisciplinaDao disciplinaDao();
    public abstract HorarioDao horarioDao();
    public abstract UsuarioCursoDao usuarioCursoDao();

    private static volatile AppDatabase database;
    public static AppDatabase getDatabase(Context context) {
        if (database == null) {
            synchronized (AppDatabase.class) {
                if (database == null) {
                    database = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "alunoDb")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return database;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
