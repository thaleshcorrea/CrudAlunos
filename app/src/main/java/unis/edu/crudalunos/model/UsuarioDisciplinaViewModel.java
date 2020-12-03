package unis.edu.crudalunos.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class UsuarioDisciplinaViewModel extends AndroidViewModel {
    private UsuarioDisciplinaDao usuarioDisciplinaDao;
    private AppDatabase database;

    public UsuarioDisciplinaViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getDatabase(application);
        usuarioDisciplinaDao = database.usuarioDisciplinaDao();

    }

    public void insert(Usuario usuario) {
        new UsuarioDisciplinaViewModel.InsertAsyncTask(usuarioDao).execute(usuario);
    }

    public void update(Usuario usuario) {
        new UsuarioDisciplinaViewModel.UpdateAsyncTask(usuarioDisciplinaDao).execute(usuario);
    }

    public void delete(Usuario usuario) {
        new UsuarioDisciplinaViewModel.DeleteAsyncTask(usuarioDisciplinaDao).execute(usuario);
    }

    private class InsertAsyncTask extends AsyncTask<Usuario, Void, Void> {
        private UsuarioDao usuarioDao;

        public InsertAsyncTask(UsuarioDao usuarioDao) {
            this.usuarioDao = usuarioDao;
        }

        @Override
        protected Void doInBackground(Usuario... usuarios) {
            usuarioDao.insert(usuarios[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Usuario, Void, Void> {
        private UsuarioDao usuarioDao;

        public UpdateAsyncTask(UsuarioDao usuarioDao) {
            this.usuarioDao = usuarioDao;
        }

        @Override
        protected Void doInBackground(Usuario... usuarios) {
            usuarioDao.update(usuarios[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Usuario, Void, Void> {
        private UsuarioDao usuarioDao;

        public DeleteAsyncTask(UsuarioDao usuarioDao) {
            this.usuarioDao = usuarioDao;
        }

        @Override
        protected Void doInBackground(Usuario... usuarios) {
            usuarioDao.delete(usuarios[0]);
            return null;
        }
    }
}
