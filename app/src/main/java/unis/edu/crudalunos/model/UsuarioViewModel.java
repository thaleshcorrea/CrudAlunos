package unis.edu.crudalunos.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import unis.edu.crudalunos.helpers.OnTaskCompleted;

public class UsuarioViewModel extends AndroidViewModel {
    private UsuarioDao usuarioDao;
    private AppDatabase database;
    private LiveData<List<Usuario>> usuarios;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getDatabase(application);
        usuarioDao = database.usuarioDao();
        usuarios = usuarioDao.getAllAlunos();
    }

    public void getById(int id, OnTaskCompleted listener) {
        new UsuarioViewModel.GetByIdAsyncTask(usuarioDao, listener).execute(id);
    }

    public void getByLogin(String login, OnTaskCompleted listener) {
        new GetByLoginAsyncTask(usuarioDao, listener).execute(login);
    }

    public void Login(String usuario, String senha, OnTaskCompleted listener) {
        new UsuarioViewModel.LoginAsyncTask(usuarioDao, listener).execute(new LoginParams(usuario, senha));
    }

    public void insert(Usuario usuario) {
        new UsuarioViewModel.InsertAsyncTask(usuarioDao).execute(usuario);
    }

    public void update(Usuario usuario) {
        new UsuarioViewModel.UpdateAsyncTask(usuarioDao).execute(usuario);
    }

    public void delete(Usuario usuario) {
        new UsuarioViewModel.DeleteAsyncTask(usuarioDao).execute(usuario);
    }

    public LiveData<List<Usuario>> getAllAlunos() {
        return usuarios;
    }

    public LiveData<List<Usuario>> getByNome (String nome){
        return usuarioDao.getByNome(nome);
    }

    private class GetByIdAsyncTask extends AsyncTask<Integer, Void, Usuario> {
        private UsuarioDao usuarioDao;
        private OnTaskCompleted listener;

        public GetByIdAsyncTask(UsuarioDao usuarioDao, OnTaskCompleted listener) {
            this.usuarioDao = usuarioDao;
            this.listener = listener;
        }

        @Override
        protected Usuario doInBackground(Integer... integers) {
            return usuarioDao.getById(integers[0]);
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            listener.processFinish(usuario);
        }
    }

    private class GetByLoginAsyncTask extends AsyncTask<String, Void, Usuario> {
        private UsuarioDao usuarioDao;
        private OnTaskCompleted listener;

        public GetByLoginAsyncTask(UsuarioDao usuarioDao, OnTaskCompleted listener) {
            this.usuarioDao = usuarioDao;
            this.listener = listener;
        }

        @Override
        protected Usuario doInBackground(String... strings) {
            return usuarioDao.getByLogin(strings[0]);
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            listener.processFinish(usuario);
        }
    }

    private class LoginParams {
        public String usuario;
        public String senha;

        public LoginParams(String usuario, String senha) {
            this.usuario = usuario;
            this.senha = senha;
        }
    }

    private class LoginAsyncTask extends AsyncTask<LoginParams, Void, Usuario> {
        private UsuarioDao usuarioDao;
        private OnTaskCompleted listener;

        public LoginAsyncTask(UsuarioDao usuarioDao, OnTaskCompleted listener) {
            this.usuarioDao = usuarioDao;
            this.listener = listener;
        }

        @Override
        protected Usuario doInBackground(LoginParams... loginParams) {
            return usuarioDao.Login(loginParams[0].usuario, loginParams[0].senha);
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            listener.processFinish(usuario);
        }
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
