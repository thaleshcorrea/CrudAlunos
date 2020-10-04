package unis.edu.crudalunos.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import unis.edu.crudalunos.helpers.OnTaskCompleted;

public class CursoViewModel extends AndroidViewModel {
    private CursoDao cursoDao;
    private AppDatabase database;
    private LiveData<List<Curso>> cursos;

    public CursoViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getDatabase(application);
        cursoDao = database.cursoDao();
        cursos = cursoDao.getAll();
    }

    public LiveData<List<Curso>> getAll() {
        return cursos;
    }

    public void GetCursoComDisciplinas(OnTaskCompleted listener) {
        new CursoViewModel.GetCursoComDisciplinasAsync(cursoDao, listener).execute();
    }

    public void insert(Curso curso) {
        new CursoViewModel.InsertAsyncTask(cursoDao).execute(curso);
    }

    public void update(Curso curso) {
        new CursoViewModel.UpdateAsyncTask(cursoDao).execute(curso);
    }

    public void delete(Curso curso) {
        new CursoViewModel.DeleteAsyncTask(cursoDao).execute(curso);
    }

    private class GetCursoComDisciplinasAsync extends AsyncTask<Void, Void, List<CursoDisciplinas>> {
        private CursoDao cursoDao;
        private OnTaskCompleted listener;

        public GetCursoComDisciplinasAsync(CursoDao cursoDao, OnTaskCompleted listener) {
            this.cursoDao = this.cursoDao;
            this.listener = listener;
        }

        @Override
        protected List<CursoDisciplinas> doInBackground(Void... voids) {
            return cursoDao.getCursoComDisciplinas();
        }

        @Override
        protected void onPostExecute(List<CursoDisciplinas> cursoDisciplinas) {
            listener.processFinish(cursoDisciplinas);
        }
    }

    private class InsertAsyncTask extends AsyncTask<Curso, Void, Void> {
        private CursoDao cursoDao;

        public InsertAsyncTask(CursoDao cursoDao) {
            this.cursoDao = cursoDao;
        }

        @Override
        protected Void doInBackground(Curso... cursos) {
            cursoDao.insert(cursos[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Curso, Void, Void> {
        private CursoDao cursoDao;

        public UpdateAsyncTask(CursoDao cursoDao) {
            this.cursoDao = cursoDao;
        }

        @Override
        protected Void doInBackground(Curso... cursos) {
            cursoDao.update(cursos[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Curso, Void, Void> {
        private CursoDao cursoDao;

        public DeleteAsyncTask(CursoDao cursoDao) {
            this.cursoDao = cursoDao;
        }

        @Override
        protected Void doInBackground(Curso... cursos) {
            cursoDao.delete(cursos[0]);
            return null;
        }
    }
}
