package unis.edu.crudalunos.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Aluno.class, Usuario.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();


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
            new PopulateDbAsyncTask(database).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private PopulateDbAsyncTask(AppDatabase db) {
            alunoDao = db.alunoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
