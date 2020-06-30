package unis.edu.crudalunos.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AlunoViewModel extends AndroidViewModel {

    private AlunoDao alunoDao;
    private AppDatabase database;
    private LiveData<List<Aluno>> alunos;

    public AlunoViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getDatabase(application);
        alunoDao = database.alunoDao();
        alunos = alunoDao.getAll();
    }

    public void insert(Aluno aluno) {
        new InsertAsyncTask(alunoDao).execute(aluno);
    }

    public void update(Aluno aluno) {
        new UpdateAsyncTask(alunoDao).execute(aluno);
    }

    public void delete(Aluno aluno) {
        new DeleteAsyncTask(alunoDao).execute(aluno);
    }

    public LiveData<List<Aluno>> getAll() {
        return alunos;
    }

    private class InsertAsyncTask extends AsyncTask<Aluno, Void, Void> {
        private AlunoDao alunoDao;

        public InsertAsyncTask(AlunoDao alunoDao) {
            this.alunoDao = alunoDao;
        }

        @Override
        protected Void doInBackground(Aluno... alunos) {
            alunoDao.insert(alunos[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Aluno, Void, Void> {
        private AlunoDao alunoDao;

        public UpdateAsyncTask(AlunoDao alunoDao) {
            this.alunoDao = alunoDao;
        }

        @Override
        protected Void doInBackground(Aluno... alunos) {
            alunoDao.update(alunos[0]);
            return null;
        }
    }


    private class DeleteAsyncTask extends AsyncTask<Aluno, Void, Void> {
        private AlunoDao alunoDao;

        public DeleteAsyncTask(AlunoDao alunoDao) {
            this.alunoDao = alunoDao;
        }

        @Override
        protected Void doInBackground(Aluno... alunos) {
            alunoDao.delete(alunos[0]);
            return null;
        }
    }
}
