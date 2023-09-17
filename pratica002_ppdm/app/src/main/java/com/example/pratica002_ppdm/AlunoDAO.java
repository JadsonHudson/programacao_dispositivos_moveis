package com.example.pratica002_ppdm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class AlunoDAO {
    private static ArrayList<Aluno> alunos = new ArrayList<>();
    private static SharedPreferences prefs;
    private static AlunoDAO instance;
    private static SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    private AlunoDAO(Context context) {
        try {
            if (context != null){

                prefs = context.getSharedPreferences( "GRADES", Context.MODE_PRIVATE);
                editor = prefs.edit();
            }
        }catch (Exception e){
            Log.e("AlunoDAO", Objects.requireNonNull(e.getMessage()));
        }

    }
    public static AlunoDAO getInstance(Context context){
        if (instance == null){
            instance = new AlunoDAO(context);
        }
        return instance;
    }
    public static AlunoDAO getInstance(){
        return instance;
    }
    public void salvar(String nome, Float grade, String gradeCurrent) {
        try {
            String json = prefs.getString("grades", null);
            Type type = new TypeToken<ArrayList<Aluno>>() {}.getType();
            alunos=gson.fromJson(json, type);

            Aluno resAluno = this.recuperar(nome);
            if(resAluno == null){
                switch (gradeCurrent) {
                    case "nota1":
                        alunos.add(new Aluno(nome, grade, (float) 0, (float) 0));
                        break;
                    case "nota2":
                        alunos.add(new Aluno(nome, (float) 0, grade, (float) 0));
                        break;
                    case "nota3":
                        alunos.add(new Aluno(nome, (float) 0, (float) 0, grade));
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.\n");
                        break;
                }
            }else {
                int index = alunos.indexOf(resAluno);
                switch (gradeCurrent) {
                    case "nota1":
                        resAluno.setNota1(grade);
                        alunos.set(index, resAluno);
                        break;
                    case "nota2":
                        resAluno.setNota2(grade);
                        alunos.set(index, resAluno);
                        break;
                    case "nota3":
                        resAluno.setNota3(grade);
                        alunos.set(index, resAluno);
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.\n");
                        break;
                }
            }

            json = gson.toJson(alunos);
            if (editor != null){
                editor.putString("grades", json);
                editor.apply();
            }else {
                Log.e("AlunoDAO", "Editor é null");
            }
        }catch (Exception e){
            Log.e("AlunoDAO.salvar", Objects.requireNonNull(e.getMessage()));
        }
    }

    public Aluno recuperar(String nome) {
        try {
            String json = prefs.getString("grades", null);
            Type type = new TypeToken<ArrayList<Aluno>>() {}.getType();

            alunos=gson.fromJson(json, type);
            if (alunos ==null){
                alunos = new ArrayList<>();
                return null;
            }
            for(Aluno a : alunos){
                if (a.getName().equals(nome)){
                    return a;
                }
            }
        }catch(Exception e){
            Log.e("AlunoDAO.recuperar", Objects.requireNonNull(e.getMessage()));
        }
        return null;
    }

    public void atualizar(Aluno aluno) {
        try {
            String json = prefs.getString("grades", null);
            Type type = new TypeToken<ArrayList<Aluno>>() {}.getType();

            alunos=gson.fromJson(json, type);
            Aluno resAluno = this.recuperar(aluno.getName());
            if(resAluno == null){
                throw new Exception("Aluno não existe na base de dados!");
            }else{
                alunos.set(alunos.indexOf(resAluno), aluno);
            }
            json = gson.toJson(alunos);
            if (editor != null){
                editor.putString("grades", json);
                editor.apply();
            }else {
                Log.e("AlunoDAO", "Editor é null");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void excluir(String nome) {
        try {
            String json = prefs.getString("grades", null);
            Type type = new TypeToken<ArrayList<Aluno>>() {}.getType();

            alunos=gson.fromJson(json, type);
            for(Aluno a : alunos){
                if (a.getName().equals(nome)){
                    alunos.remove(a);
                    break;
                }
            }
            json = gson.toJson(alunos);
            if (editor != null){
                editor.putString("grades", json);
                editor.apply();
            }else {
                Log.e("AlunoDAO", "Editor é null");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
