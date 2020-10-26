package unis.edu.crudalunos.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DisciplinaViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int cursoId;

    public DisciplinaViewModelFactory(Application application, int cursoId) {
        this.application = application;
        this.cursoId = cursoId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DisciplinaViewModel(application, cursoId);
    }
}
