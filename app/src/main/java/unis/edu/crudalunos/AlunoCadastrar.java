package unis.edu.crudalunos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import unis.edu.crudalunos.helpers.functions;
import unis.edu.crudalunos.model.Aluno;
import unis.edu.crudalunos.model.AlunoViewModel;
import unis.edu.crudalunos.model.UsuarioViewModel;

public class AlunoCadastrar extends AppCompatActivity {
    static final String ALUNO = "ALUNO";
    final int REQUEST_CODE_GALLERY = 999;

    private ImageView imageView;
    private FloatingActionButton fbSalvar;
    private FloatingActionButton fbImage;
    private TextInputLayout textInputLayoutNome;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutTelefone;

    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_cadastrar);
        getSupportActionBar().setTitle(getText(R.string.title_aluno_add));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        registrarEventos();

        Intent intent = getIntent();

        Object data = intent.getSerializableExtra(ALUNO);
        if(data != null) {
            aluno = (Aluno)data;
            preencherValores();
        } else {
            aluno = new Aluno();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initViews() {
        imageView = findViewById(R.id.imageView);
        fbSalvar = findViewById(R.id.fbSalvar);
        fbImage = findViewById(R.id.fbImage);
        textInputLayoutNome = findViewById(R.id.textInputNome);
        textInputLayoutEmail = findViewById(R.id.textInputEmail);
        textInputLayoutTelefone = findViewById(R.id.textInputTelefone);
    }

    private void registrarEventos() {
        fbImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AlunoCadastrar.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        fbSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });
    }

    private void preencherValores() {
        textInputLayoutNome.getEditText().setText(aluno.getNome());
        textInputLayoutEmail.getEditText().setText(aluno.getEmail());
        textInputLayoutTelefone.getEditText().setText(aluno.getTelefone());

        byte[] byteAluno = aluno.getFoto();
        if(byteAluno != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteAluno, 0, byteAluno.length);
            imageView.setImageBitmap(bitmap);
        }
    }

    private Boolean validate(Aluno aluno) {
        Boolean nome = validateNome(aluno.getNome());
        Boolean email = validateEmail(aluno.getEmail());
        Boolean telefone = validateTelefone(aluno.getTelefone());

        return (nome && email && telefone);
    }

    private Boolean validateNome(String nome) {
        if(nome.isEmpty()) {
            textInputLayoutNome.setError(getText(R.string.nome_obrigatorio));
            return false;
        } else {
            textInputLayoutNome.setError(null);
            return true;
        }
    }

    private Boolean validateEmail(String email) {
        if(email.isEmpty()) {
            textInputLayoutEmail.setError(getText(R.string.email_obrigatorio));
            return false;
        }
        else {
            textInputLayoutEmail.setError(null);
            return true;
        }
    }

    private Boolean validateTelefone(String telefone) {
        if(telefone.isEmpty()) {
            textInputLayoutTelefone.setError(getText(R.string.telefone_obrigatorio));
            return false;
        } else {
            textInputLayoutTelefone.setError(null);
            return true;
        }
    }

    private void AtribuirValores() {
        aluno.setNome(textInputLayoutNome.getEditText().getText().toString());
        aluno.setEmail(textInputLayoutEmail.getEditText().getText().toString());
        aluno.setTelefone(textInputLayoutTelefone.getEditText().getText().toString());
        aluno.setFoto(functions.imageViewToByte(imageView));
    }

    private void salvar() {
        AtribuirValores();

        if(validate(aluno)) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(ALUNO, aluno);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}