package com.cursoandroidstudio.listadetarefas.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cursoandroidstudio.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase ler;

    public TarefaDAO(Context context) {

        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try {
            escreve.insert(DbHelper.TABELA_TAREFAS, null, cv);
            Log.e("INFO", "Sucesso ao salvar tarefa");

        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try {
            String[] args = {Long.toString(tarefa.getId())};
            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.e("INFO", "Tarefa atualizada com sucesso!");

        } catch (Exception e) {
            Log.e("INFO", "Erro ao atualizar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = {Long.toString(tarefa.getId())};
            escreve.delete(DbHelper.TABELA_TAREFAS, "id=?", args);
            Log.e("INFO", "Tarefa removida com sucesso!");

        } catch (Exception e) {
            Log.e("INFO", "Erro ao remover tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";
        Cursor c = ler.rawQuery(sql, null);

        while (c.moveToNext() ) {

            Tarefa tarefa = new Tarefa();

            @SuppressLint("Range") Long id = c.getLong(c.getColumnIndex("id") );
            @SuppressLint("Range") String nomeTarefa = c.getString(c.getColumnIndex("nome") );

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);


        }

        return tarefas;

    }
}
