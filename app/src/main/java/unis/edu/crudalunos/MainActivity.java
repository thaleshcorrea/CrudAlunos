package unis.edu.crudalunos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import unis.edu.crudalunos.enums.UsuarioTipo;
import unis.edu.crudalunos.helpers.MyPreferences;
import unis.edu.crudalunos.helpers.UserPreferences;
import unis.edu.crudalunos.helpers.app_parameters;
import unis.edu.crudalunos.model.Usuario;

public class MainActivity extends AppCompatActivity {

    public static final String USER = "USER";
    public static final String STAY_LOGGED = "STAY_LOGGED";
    Fragment selectedFragment = null;

    private MyPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        userPreferences = new UserPreferences(getApplicationContext(), getText(R.string.sp_usuario).toString());
        savePreferences();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportActionBar().setTitle("Inicio");

        if(app_parameters.getLoggedUser().getUsuarioTipo() == UsuarioTipo.ALUNO.getValue()) {
            setFragmentAluno();
        }
        else {
            setFragmentProfessor();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sair:
                sair();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setFragmentAluno() {
        selectedFragment = new AlunoHomeFragment();
    }

    private void setFragmentProfessor() {
        selectedFragment = new ProfessorHomeFragment();
    }

    private void sair() {
        userPreferences.remove();
        finishAndRemoveTask();
    }

    private void savePreferences() {
        Intent intent = getIntent();
        Usuario usuario = (Usuario) intent.getSerializableExtra(USER);
        app_parameters.setLoggedUser(usuario);
        Boolean stayLogged = intent.getBooleanExtra(STAY_LOGGED, false);
        if(stayLogged) {
            userPreferences.set(usuario.getId());
        }
        else {
            userPreferences.remove();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            if(app_parameters.getLoggedUser().getUsuarioTipo() == UsuarioTipo.ALUNO.getValue()) {
                                setFragmentAluno();
                            }
                            else {
                                setFragmentProfessor();
                            }
                            getSupportActionBar().setTitle("Inicio");
                            break;
                        case R.id.nav_mais:
                            selectedFragment = new SettingsFragment();
                            getSupportActionBar().setTitle(getText(R.string.title_mais));
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}