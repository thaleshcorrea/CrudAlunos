package unis.edu.crudalunos.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import unis.edu.crudalunos.helpers.OnTaskCompleted;

public class DisciplinaViewModel extends AndroidViewModel {
    private DisciplinaDao disciplinaDao;
    private AppDatabase database;
    private LiveData<List<Disciplina>> disciplinas;
    private LiveData<List<Disciplina>> disciplinasByCurso;


    public DisciplinaViewModel(@NonNull Application application, int cursoId) {
        super(application);

        database = AppDatabase.getDatabase(application);
        disciplinaDao = database.disciplinaDao();
        disciplinas = disciplinaDao.getAll();
        disciplinasByCurso = disciplinaDao.getByCurso(cursoId);
    }

    public LiveData<List<Disciplina>> getAll() {
        return disciplinas;
    }

    public LiveData<List<Disciplina>> getByCurso() {
        return disciplinasByCurso;
    }

    public void GetCursoComDisciplinas(OnTaskCompleted listener) {
        new DisciplinaViewModel.GetCursoComDisciplinasAsync(disciplinaDao, listener).execute();
    }

    public void insert(Disciplina disciplina) {
        new DisciplinaViewModel.InsertAsyncTask(disciplinaDao).execute(disciplina);
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

    private class InsertAsyncTask extends AsyncTask<Disciplina, Void, Void> {
        private DisciplinaDao disciplinaDao;

        public InsertAsyncTask(DisciplinaDao disciplinaDao) {
            this.disciplinaDao = disciplinaDao;
        }

        @Override
        protected Void doInBackground(Disciplina... disciplinas) {
            disciplinaDao.insert(disciplinas[0]);
            return null;
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
