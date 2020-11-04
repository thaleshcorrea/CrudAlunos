package unis.edu.crudalunos.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import unis.edu.crudalunos.helpers.OnTaskCompleted;

public class UsuarioCursoViewModel extends AndroidViewModel {
    private AppDatabase database;
    private UsuarioCursoDao usuarioCursoDao;

    public UsuarioCursoViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getDatabase(application);
        usuarioCursoDao = database.usuarioCursoDao();
    }

    public void getUsuarioWithCurso(int id, OnTaskCompleted listener) {
        new UsuarioCursoViewModel.GetUsuarioWithCursoAsync(usuarioCursoDao, listener).execute(id);
    }

    public void insert(List<UsuarioCurso> usuarioCursoList) {
        new UsuarioCursoViewModel.InsertAsyncTask(usuarioCursoDao).execute(usuarioCursoList);
    }

    public void delete(int usuarioId) {
        new UsuarioCursoViewModel.DeleteAsyncTask(usuarioCursoDao).execute(usuarioId);
    }

    private class GetUsuarioWithCursoAsync extends AsyncTask<Integer, Void, UsuarioWithCurso> {
        private UsuarioCursoDao usuarioCursoDao;
        private OnTaskCompleted listener;

        public GetUsuarioWithCursoAsync(UsuarioCursoDao usuarioCursoDao, OnTaskCompleted listener) {
            this.usuarioCursoDao = usuarioCursoDao;
            this.listener = listener;
        }

        @Override
        protected UsuarioWithCurso doInBackground(Integer... integers) {
            return usuarioCursoDao.getUsuarioWithCurso(integers[0]);
        }

        @Override
        protected void onPostExecute(UsuarioWithCurso usuarioWithCurso) {
            listener.processFinish(usuarioWithCurso);
        }
    }

    private class InsertAsyncTask extends AsyncTask<List<UsuarioCurso>, Void, Void> {
        private UsuarioCursoDao usuarioCursoDao;

        public InsertAsyncTask(UsuarioCursoDao usuarioCursoDao) {
            this.usuarioCursoDao = usuarioCursoDao;
        }

        @Override
        protected Void doInBackground(List<UsuarioCurso>... lists) {
            usuarioCursoDao.insert(lists[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private UsuarioCursoDao usuarioCursoDao;

        public DeleteAsyncTask(UsuarioCursoDao usuarioCursoDao) {
            this.usuarioCursoDao = usuarioCursoDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            usuarioCursoDao.delete(integers[0]);
            return null;
        }
    }
}
