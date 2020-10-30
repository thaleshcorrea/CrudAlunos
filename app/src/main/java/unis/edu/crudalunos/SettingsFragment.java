package unis.edu.crudalunos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import unis.edu.crudalunos.model.Disciplina;
import unis.edu.crudalunos.model.DisciplinaViewModel;
import unis.edu.crudalunos.model.Usuario;
import unis.edu.crudalunos.model.UsuarioViewModel;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String KEY_DISCIPLINA = "disciplines";

    static final String DISCIPLINA = "DISCIPLINA";
    private static final int DISCIPLINA_ADD_REQUEST = 1;
    private DisciplinaViewModel disciplinaViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        disciplinaViewModel = new ViewModelProvider(this).get(DisciplinaViewModel.class);

        preferenceClickDiscipline();
    }

    private void preferenceClickDiscipline() {
        Preference preference = (Preference) findPreference(KEY_DISCIPLINA);
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(preference.getIntent(), DISCIPLINA_ADD_REQUEST);
                return true;
            }
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            Disciplina disciplina = (Disciplina) data.getSerializableExtra(DISCIPLINA);
            if(requestCode == DISCIPLINA_ADD_REQUEST) {
                disciplinaViewModel.insert(disciplina);
            }

            Toast.makeText(getView().getContext(), "Registro salvo", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}