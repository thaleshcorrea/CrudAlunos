package unis.edu.crudalunos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import unis.edu.crudalunos.model.Curso;
import unis.edu.crudalunos.model.Disciplina;
import unis.edu.crudalunos.model.DisciplinaViewModel;
import unis.edu.crudalunos.model.Horario;
import unis.edu.crudalunos.model.HorarioViewModel;
import unis.edu.crudalunos.model.UsuarioCurso;
import unis.edu.crudalunos.model.UsuarioCursoViewModel;
import unis.edu.crudalunos.model.UsuarioViewModel;
import unis.edu.crudalunos.model.UsuarioWithCurso;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String KEY_DISCIPLINA = "disciplines";
    private static final String KEY_EDITAR_USUARIO = "editar_usuario";

    static final String DISCIPLINA = "DISCIPLINA";
    private static final String USUARIO_CURSO = "USUARIO_CURSO";

    private static final int DISCIPLINA_ADD_REQUEST = 1;
    private static final int USUARIO_EDIT_REQUEST = 2;

    private DisciplinaViewModel disciplinaViewModel;
    private HorarioViewModel horarioViewModel;

    private UsuarioViewModel usuarioViewModel;
    private UsuarioCursoViewModel usuarioCursoViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        disciplinaViewModel = new ViewModelProvider(this).get(DisciplinaViewModel.class);
        horarioViewModel = new ViewModelProvider(this).get(HorarioViewModel.class);

        preferenceClick();
    }

    private void preferenceClick() {
        Preference preference = findPreference(KEY_DISCIPLINA);
        preference.setOnPreferenceClickListener(preference1 -> {
            startActivityForResult(preference1.getIntent(), DISCIPLINA_ADD_REQUEST);
            return true;
        });

        Preference preferenceEditarUsuario = findPreference(KEY_EDITAR_USUARIO);
        preferenceEditarUsuario.setOnPreferenceClickListener(preference1 -> {
            startActivityForResult(preferenceEditarUsuario.getIntent(), USUARIO_EDIT_REQUEST);
            return true;
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == DISCIPLINA_ADD_REQUEST) {
                Disciplina disciplina = (Disciplina) data.getSerializableExtra(DISCIPLINA);
                disciplinaViewModel.insert(disciplina, output -> {
                    if (output == null) {
                        return;
                    }
                    Long disciplinaId = (Long) output;
                    for (Horario horario : disciplina.getHorarios()) {
                        horario.setDisciplinaId(disciplinaId);
                    }
                    horarioViewModel.insertAll(disciplina.getHorarios());
                });
            } else if (requestCode == USUARIO_EDIT_REQUEST) {
                usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
                usuarioCursoViewModel = new ViewModelProvider(this).get(UsuarioCursoViewModel.class);

                UsuarioWithCurso usuarioWithCurso = (UsuarioWithCurso) data.getSerializableExtra(USUARIO_CURSO);
                usuarioViewModel.update(usuarioWithCurso.getUsuario());
                List<UsuarioCurso> usuarioCursoList = new ArrayList<>();
                for(Curso curso : usuarioWithCurso.getCursos()) {
                    UsuarioCurso usuarioCurso = new UsuarioCurso();
                    usuarioCurso.setId(usuarioWithCurso.getUsuario().getId());
                    usuarioCurso.setCursoId(curso.getCursoId());
                    usuarioCursoList.add(usuarioCurso);
                }
                usuarioCursoViewModel.insert(usuarioCursoList);
            }

            Snackbar.make(getView(), "Registro salvo", Snackbar.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}