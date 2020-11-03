package unis.edu.crudalunos.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class HorarioViewModel extends AndroidViewModel {
    private HorarioDao horarioDao;
    private AppDatabase database;
    private LiveData<List<Horario>> horarios;

    public HorarioViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getDatabase(application);
        horarioDao = database.horarioDao();
        horarios = horarioDao.getAll();
    }

    public LiveData<List<Horario>> getAll() {
        return horarios;
    }

    public List<Horario> getByDisciplina(long disciplinaId) {
        return horarioDao.getByDisciplina(disciplinaId);
    }

    public void insert(Horario horario) {
        new HorarioViewModel.InsertAsyncTask(horarioDao).execute(horario);
    }

    public void insertAll(List<Horario> horarios) {
        new HorarioViewModel.InsertAllAsyncTask(horarioDao).execute(horarios);
    }

    public void update(Horario horario) {
        new HorarioViewModel.UpdateAsyncTask(horarioDao).execute(horario);
    }

    public void delete(Horario horario) {
        new HorarioViewModel.DeleteAsyncTask(horarioDao).execute(horario);
    }

    private class InsertAsyncTask extends AsyncTask<Horario, Void, Void> {
        private HorarioDao horarioDao;

        public InsertAsyncTask(HorarioDao horarioDao) {
            this.horarioDao = horarioDao;
        }

        @Override
        protected Void doInBackground(Horario... horarios) {
            horarioDao.insert(horarios[0]);
            return null;
        }
    }

    private class InsertAllAsyncTask extends AsyncTask<List<Horario>, Void, Void> {
        private HorarioDao horarioDao;

        public InsertAllAsyncTask(HorarioDao horarioDao) {
            this.horarioDao = horarioDao;
        }

        @Override
        protected Void doInBackground(List<Horario>... lists) {
            horarioDao.insertAll(lists[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Horario, Void, Void> {
        private HorarioDao horarioDao;

        public UpdateAsyncTask(HorarioDao horarioDao) {
            this.horarioDao = horarioDao;
        }

        @Override
        protected Void doInBackground(Horario... horarios) {
            horarioDao.update(horarios[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Horario, Void, Void> {
        private HorarioDao horarioDao;

        public DeleteAsyncTask(HorarioDao horarioDao) {
            this.horarioDao = horarioDao;
        }

        @Override
        protected Void doInBackground(Horario... horarios) {
            horarioDao.delete(horarios[0]);
            return null;
        }
    }
}
