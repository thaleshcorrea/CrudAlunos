package unis.edu.crudalunos.model;

import android.app.Application;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import unis.edu.crudalunos.helpers.OnTaskCompleted;

public class DisciplinaViewModel extends AndroidViewModel {
    private DisciplinaDao disciplinaDao;
    private AppDatabase database;
    private LiveData<List<Disciplina>> disciplinas;

    public DisciplinaViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getDatabase(application);
        disciplinaDao = database.disciplinaDao();
        disciplinas = disciplinaDao.getAll();
    }

    public LiveData<List<Disciplina>> getAll() {
        return disciplinas;
    }

    public void GetCursoComDisciplinas(OnTaskCompleted listener) {
        new DisciplinaViewModel.GetCursoComDisciplinasAsync(disciplinaDao, listener).execute();
    }

    public void insert(Disciplina disciplina, OnTaskCompleted listener) {
        new DisciplinaViewModel.InsertAsyncTask(disciplinaDao, listener).execute(disciplina);
    }

    public void update(Disciplina disciplina) {
        new DisciplinaViewModel.UpdateAsyncTask(disciplinaDao).execute(disciplina);
    }

    public void delete(Disciplina disciplina) {
        new DisciplinaViewModel.DeleteAsyncTask(disciplinaDao).execute(disciplina);
    }

    private class GetCursoComDisciplinasAsync extends AsyncTask<Void, Void, List<DisciplinaHorarios>> {
        private DisciplinaDao disciplinaDao;
        private OnTaskCompleted listener;

        public GetCursoComDisciplinasAsync(DisciplinaDao disciplinaDao, OnTaskCompleted listener) {
            this.disciplinaDao = this.disciplinaDao;
            this.listener = listener;
        }

        @Override
        protected List<DisciplinaHorarios> doInBackground(Void... voids) {
            return disciplinaDao.getDisciplinaComHorarios();
        }

        @Override
        protected void onPostExecute(List<DisciplinaHorarios> disciplinaHorarios) {
            listener.processFinish(disciplinaHorarios);
        }
    }

    private class InsertAsyncTask extends AsyncTask<Disciplina, Void, Long> {
        private DisciplinaDao disciplinaDao;
        private OnTaskCompleted listener;

        public InsertAsyncTask(DisciplinaDao disciplinaDao, OnTaskCompleted listener) {
            this.disciplinaDao = disciplinaDao;
            this.listener = listener;
        }

        @Override
        protected Long doInBackground(Disciplina... disciplinas) {
            return disciplinaDao.insert(disciplinas[0]);
        }

        @Override
        protected void onPostExecute(Long id) {
            listener.processFinish(id);
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Disciplina, Void, Void> {
        private DisciplinaDao disciplinaDao;

        public UpdateAsyncTask(DisciplinaDao disciplinaDao) {
            this.disciplinaDao = disciplinaDao;
        }

        @Override
        protected Void doInBackground(Disciplina... disciplinas) {
            disciplinaDao.update(disciplinas[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Disciplina, Void, Void> {
        private DisciplinaDao disciplinaDao;

        public DeleteAsyncTask(DisciplinaDao disciplinaDao) {
            this.disciplinaDao = disciplinaDao;
        }

        @Override
        protected Void doInBackground(Disciplina... disciplinas) {
            disciplinaDao.delete(disciplinas[0]);
            return null;
        }
    }
}
