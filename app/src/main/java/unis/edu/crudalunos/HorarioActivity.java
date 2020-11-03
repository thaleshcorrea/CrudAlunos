package unis.edu.crudalunos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

import unis.edu.crudalunos.model.Horario;

public class HorarioActivity extends AppCompatActivity {

    private static final String HORARIO = "HORARIO";
    static final String HORARIO_INDEX = "HORARIO_INDEX";

    private TextView textViewInicio;
    private TextView textViewFim;
    private FloatingActionButton btSalvar;

    private ChipGroup chipGroupSemana;
    private Chip chipDomingo;
    private Chip chipSegunda;
    private Chip chipTerca;
    private Chip chipQuarta;
    private Chip chipQuinta;
    private Chip chipSexta;
    private Chip chipSabado;

    private Horario horario = null;
    private int horario_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_newhorario));

        // SetUp views
        textViewInicio = findViewById(R.id.textViewInicio);
        textViewFim = findViewById(R.id.textViewFim);
        btSalvar = findViewById(R.id.btSalvar);
        chipGroupSemana = findViewById(R.id.chipGroupSemana);
        chipDomingo = findViewById(R.id.chipDomingo);
        chipSegunda = findViewById(R.id.chipSegunda);
        chipTerca = findViewById(R.id.chipTerca);
        chipQuarta = findViewById(R.id.chipQuarta);
        chipQuinta = findViewById(R.id.chipQuinta);
        chipSexta = findViewById(R.id.chipSexta);
        chipSabado = findViewById(R.id.chipSabado);

        // Set initial values
        chipGroupSemana.setSelectionRequired(true);

        // get intent
        Intent intent = getIntent();
        Object data = intent.getSerializableExtra(HORARIO);
        if (data != null) {
            horario_index = intent.getIntExtra(HORARIO_INDEX, 0);
            horario = (Horario) data;
            setValues();
        } else {
            setChipSelect();
            horario = new Horario();
        }

        // Set events
        btSalvar.setOnClickListener(v -> save());
        textViewInicio.setOnClickListener(v -> showTimePicker(true));
        textViewFim.setOnClickListener(v -> showTimePicker(false));
    }

    private void setChipSelect(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                chipDomingo.setChecked(true);
                break;
            case Calendar.MONDAY:
                chipSegunda.setChecked(true);
                break;
            case Calendar.TUESDAY:
                chipTerca.setChecked(true);
                break;
            case Calendar.WEDNESDAY:
                chipQuarta.setChecked(true);
                break;
            case Calendar.THURSDAY:
                chipQuinta.setChecked(true);
                break;
            case Calendar.FRIDAY:
                chipSexta.setChecked(true);
                break;
            case Calendar.SATURDAY:
                chipSabado.setChecked(true);
                break;
        }
    }

    private void setValues() {
        textViewInicio.setText(String.format("%02d:%02d", horario.getHoraInicio(), horario.getMinutoInicio()));
        textViewFim.setText(String.format("%02d:%02d", horario.getHoraTermino(), horario.getMinutoTermino()));

        chipDomingo.setChecked(horario.isDomingo());
        chipSegunda.setChecked(horario.isSegunda());
        chipTerca.setChecked(horario.isTerca());
        chipQuarta.setChecked(horario.isQuarta());
        chipQuinta.setChecked(horario.isQuinta());
        chipSexta.setChecked(horario.isSexta());
        chipSabado.setChecked(horario.isSabado());
    }

    private void showTimePicker(Boolean startHour) {
        int hour = startHour ? horario.getHoraInicio() : horario.getHoraTermino();
        int minute = startHour ? horario.getMinutoInicio() : horario.getMinutoTermino();

        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .build();

        materialTimePicker.show(getSupportFragmentManager(), "fragment_tag");
        materialTimePicker.addOnPositiveButtonClickListener(dialog -> {
            int newHour = materialTimePicker.getHour();
            int newMinute = materialTimePicker.getMinute();
            onTimeSet(startHour, newHour, newMinute);
        });
    }

    private void onTimeSet(boolean startHour, int newHour, int newMinute) {

        String format = String.format("%02d:%02d", newHour, newMinute);
        if (startHour) {
            textViewInicio.setText(format);
            horario.setHoraInicio(newHour);
            horario.setMinutoInicio(newMinute);
        } else {
            textViewFim.setText(format);
            horario.setHoraTermino(newHour);
            horario.setMinutoTermino(newMinute);
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.coordinator), message, Snackbar.LENGTH_SHORT).show();
    }

    private boolean validateTime() {
        if (horario.getHoraInicio() == horario.getHoraTermino() && horario.getMinutoInicio() == horario.getMinutoTermino()) {
            showSnackbar(getString(R.string.invalid_interval));
            return false;
        }
        return true;
    }

    private boolean validateWeek() {
        if (chipGroupSemana.getCheckedChipIds().isEmpty()) {
            showSnackbar(getString(R.string.dia_semana_required));
            return false;
        }
        return true;
    }

    private void save() {
        if (!validateTime() || !validateWeek()) {
            return;
        }

        setDaysChecked();

        Intent returnIntent = new Intent();
        returnIntent.putExtra(HORARIO, horario);
        returnIntent.putExtra(HORARIO_INDEX, horario_index);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void setDaysChecked() {
        horario.setDomingo(chipDomingo.isChecked());
        horario.setSegunda(chipSegunda.isChecked());
        horario.setTerca(chipTerca.isChecked());
        horario.setQuarta(chipQuarta.isChecked());
        horario.setQuinta(chipQuinta.isChecked());
        horario.setSexta(chipSexta.isChecked());
        horario.setSabado(chipSabado.isChecked());
    }
}