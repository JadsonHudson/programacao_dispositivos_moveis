package com.example.pratica002_ppdm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditScreen extends AppCompatActivity {
    private Button voltarBtn, buscarBtn, editarBtn, excluirBtn;
    private TextView newNome, newNota1, newNota2, newNota3;
    private AlunoDAO alunos = AlunoDAO.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);
        newNome = findViewById(R.id.newNome);
        newNota1 = findViewById(R.id.newNota1);
        newNota2 = findViewById(R.id.newNota2);
        newNota3 = findViewById(R.id.newNota3);
        voltarBtn = findViewById(R.id.voltarBtn);
        buscarBtn = findViewById(R.id.buscarBtn);
        editarBtn = findViewById(R.id.editarBtn);
        excluirBtn=findViewById(R.id.excluirBtn);

        voltarBtn.setOnClickListener(v->{
            onBackPressed();
        });

        buscarBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String searchNome = newNome.getText().toString();
                Aluno resAluno = alunos.recuperar(searchNome);
                if(resAluno!=null){
                    newNota1.setText(resAluno.getNota1().toString());
                    newNota2.setText(resAluno.getNota2().toString());
                    newNota3.setText(resAluno.getNota3().toString());
                    newNota1.setFocusable(true);
                    newNota1.setFocusableInTouchMode(true);
                    newNota2.setFocusable(true);
                    newNota2.setFocusableInTouchMode(true);
                    newNota3.setFocusable(true);
                    newNota3.setFocusableInTouchMode(true);
                }

            }
        });
        editarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Float nota1=Float.parseFloat(newNota1.getText().toString()),nota2=Float.parseFloat(newNota2.getText().toString()),nota3=Float.parseFloat(newNota3.getText().toString());
                    String nome = newNome.getText().toString();
                    alunos.atualizar(new Aluno(nome, nota1, nota2, nota3));
            }
        });
        excluirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteNome = newNome.getText().toString();
                alunos.excluir(deleteNome);
            }
        });
    }
}