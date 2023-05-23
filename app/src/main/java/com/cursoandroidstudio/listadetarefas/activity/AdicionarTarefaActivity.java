package com.cursoandroidstudio.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cursoandroidstudio.listadetarefas.R;
import com.cursoandroidstudio.listadetarefas.helper.TarefaDAO;
import com.cursoandroidstudio.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        ActionBar actionBar = getSupportActionBar();
        //mudar o titulo na parte de add tarefa pq inexplicavelmente tava ficando preto????
        if (actionBar != null) {
            // Define a cor do texto do título como branco
            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Lista de Tarefas</font>"));
        }

        editTarefa = findViewById(R.id.textTarefa2);

        //recuperar tarefa, caso seja edicao
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //configurar a tarefa na caixa de text
        if (tarefaAtual != null) {
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemSalvar:
                //Executa açao para o item salvar
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefaAtual != null) {//edicao
                    String nomeTarefa = editTarefa.getText().toString();
                    if (!nomeTarefa.isEmpty() ) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //atualizar no banco de dados
                        if (tarefaDAO.atualizar(tarefa) ) {
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Tarefa atualizada com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                } else { //salvar

                    String nomeTarefa = editTarefa.getText().toString();
                    if (!nomeTarefa.isEmpty() ) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);

                        if (tarefaDAO.salvar(tarefa)) {
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Tarefa salva com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }

                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}