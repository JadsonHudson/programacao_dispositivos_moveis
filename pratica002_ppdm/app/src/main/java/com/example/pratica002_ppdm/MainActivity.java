package com.example.pratica002_ppdm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editNota;
    private RadioButton gradeCurrent;
    Button saveGradeBtn;

    private TableLayout tableLayout;
    ArrayList<Aluno> gradeBook = new ArrayList<>();
    private AlunoDAO alunos;
    Button editarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alunos = AlunoDAO.getInstance(this);
        final RadioGroup gradeGroup;
        editName = findViewById(R.id.editNome);
        editNota = findViewById(R.id.editNota);
        tableLayout = findViewById(R.id.tabela);

        gradeGroup = findViewById(R.id.bimestreGroup);

        saveGradeBtn = findViewById(R.id.adicionarBtn);

        saveGradeBtn.setOnClickListener(OnClickListener -> {
            gradeCurrent = findViewById(gradeGroup.getCheckedRadioButtonId());
            saveGrade(editName.getText().toString(), Float.parseFloat(editNota.getText().toString()), gradeCurrent.getText().toString());
        });
        editarBtn = findViewById(R.id.toEditScreenBtn);

        editarBtn.setOnClickListener(view -> {
                Intent editScreen = new Intent(MainActivity.this, EditScreen.class);
                startActivity(editScreen);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        SharedPreferences prefs = MainActivity.this.getApplicationContext().getSharedPreferences("GRADES", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("grades", null);
        Type type = new TypeToken<ArrayList<Aluno>>() {}.getType();
        gradeBook = gson.fromJson(json, type);

        if (gradeBook != null) {
            tableLayout.removeAllViews();
            for (int i = 0; i < gradeBook.size(); i++) {
                TableRow row = new TableRow(MainActivity.this);
                TextView nome = new TextView(MainActivity.this);
                TextView nota1 = new TextView(MainActivity.this);
                TextView nota2 = new TextView(MainActivity.this);
                TextView nota3 = new TextView(MainActivity.this);
                nome.setText(gradeBook.get(i).getName());
                nome.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                row.addView(nome);
                nota1.setText(String.valueOf(gradeBook.get(i).getNota1()));
                nota1.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                row.addView(nota1);
                nota2.setText(String.valueOf(gradeBook.get(i).getNota2()));
                nota2.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                row.addView(nota2);
                nota3.setText(String.valueOf(gradeBook.get(i).getNota3()));
                nota3.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                row.addView(nota3);
                TableRow.LayoutParams rowLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 30);
                row.setLayoutParams(rowLayoutParams);
                tableLayout.addView(row);
            }
        }
    }

    public void saveGrade(String nome, Float grade, String gradeCurrent) {
        alunos.salvar(nome, grade, gradeCurrent);
        loadData();
    }
}


