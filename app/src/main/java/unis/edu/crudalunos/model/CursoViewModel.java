package unis.edu.crudalunos.model;

import android.app.Application;
import android.net.sip.SipSession;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import unis.edu.crudalunos.helpers.OnTaskCompleted;

public class CursoViewModel extends AndroidViewModel {
    private CursoDao cursoDao;
    private AppDatabase database;
    private LiveData<List<CursoWithDisciplinas>> cursos;

    public CursoViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getDatabase(application);
        cursoDao = database.cursoDao();
        cursos = cursoDao.getAll();
    }

    public LiveData<List<CursoWithDisciplinas>> getAll() {
        return cursos;
    }

    public LiveData<List<CursoWithDisciplinas>> getByNome(String nome) {
        return cursoDao.getByNome(nome);
    }

    public LiveData<List<CursoWithDisciplinas>> getById(long cursoId) {
        return cursoDao.getById(cursoId);
    }

    public void getCursoWithDisciplinasAndHorarios(List<Long> cursoIds, OnTaskCompleted listener) {
        new GetCursoWithDisciplinasAndHorarios(cursoDao, listener).execute(cursoIds);
    }

    public void insert(Curso curso, OnTaskCompleted listener) {
        new CursoViewModel.InsertAsyncTask(cursoDao, listener).execute(curso);
    }

    public void update(Curso curso) {
        new CursoViewModel.UpdateAsyncTask(cursoDao).execute(curso);
    }

    public void delete(Curso curso) {
        new CursoViewModel.DeleteAsyncTask(cursoDao).execute(curso);
    }

    private class GetCursoWithDisciplinasAndHorarios extends AsyncTask<List<Long>, Void, List<CursoWithDisciplinasAndHorarios>> {
        private OnTaskCompleted listener;
        private CursoDao cursoDao;

        public GetCursoWithDisciplinasAndHorarios(CursoDao cursoDao, OnTaskCompleted listener) {
            this.cursoDao = cursoDao;
            this.listener = listener;
        }

        @Override
        protected List<CursoWithDisciplinasAndHorarios> doInBackground(List<Long>... longs) {
            return cursoDao.getCursoWithDisciplinasAndHorarios(longs[0]);
        }

        @Override
        protected void onPostExecute(List<CursoWithDisciplinasAndHorarios> cursoWithDisciplinasAndHorarios) {
            listener.processFinish(cursoWithDisciplinasAndHorarios);
        }
    }

    private class InsertAsyncTask extends AsyncTask<Curso, Void, Long> {
        private CursoDao cursoDao;
        private OnTaskCompleted listener;

        public InsertAsyncTask(CursoDao cursoDao, OnTaskCompleted listener) {
            this.cursoDao = cursoDao;
            this.listener = listener;
        }

        @Override
        protected Long doInBackground(Curso... cursos) {
            return cursoDao.insert(cursos[0]);
        }

        @Override
        protected void onPostExecute(Long cursoId) {
            listener.processFinish(cursoId);
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
