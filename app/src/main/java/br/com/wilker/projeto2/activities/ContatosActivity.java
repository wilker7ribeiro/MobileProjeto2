package br.com.wilker.projeto2.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.wilker.projeto2.R;
import br.com.wilker.projeto2.dao.ContatoDAO;
import br.com.wilker.projeto2.helpers.ContatoAdapter;
import br.com.wilker.projeto2.helpers.RecyclerItemClickListener;
import br.com.wilker.projeto2.models.Contato;

public class ContatosActivity extends AppCompatActivity {

    private RecyclerView listaContatosRecycler;
    private ContatoAdapter contatoAdapter;
    private List<Contato> listaContatos = new ArrayList<>();
    private Contato contatoSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        // Inicializa e seta o titulo da toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lista de Contatos");
        setSupportActionBar(toolbar);

        // Inicializa o botao + para adicionar contato
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarContatoActivity.class);
                startActivity(intent);
            }
        });

        // Recuperando RecyclerView
        listaContatosRecycler = findViewById(R.id.listaContatos);

        // Adicionando evento de clique nos items da Recycler
        listaContatosRecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        listaContatosRecycler,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            // Configurando evento de click no item da recycler
                            public void onItemClick(View view, int position) {
                                // Recuperando contato para edição
                                contatoSelected = listaContatos.get(position);

                                // Enviando tarefa para tela de edição (adicionar contato)
                                Intent intent = new Intent(
                                        ContatosActivity.this,
                                        AdicionarContatoActivity.class);
                                intent.putExtra("contatoSelected", contatoSelected);

                                startActivity(intent);
                            }

                            @Override
                            // Deleção (evento de quando segurar o click no item)
                            public void onLongItemClick(View view, int position) {
                                //Recuperando contato selecionado
                                contatoSelected = listaContatos.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(ContatosActivity.this);

                                // Configura titulo e mensagem
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir o contato: "+contatoSelected.getNomePessoa() + " ?");

                                // Setando ação do botão "Sim"
                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());

                                        if(contatoDAO.deletar(contatoSelected)) {
                                            carregarListaContatos();
                                            Toast.makeText(
                                                getApplicationContext(),
                                                "Contato excluído com sucesso",
                                                Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(
                                                getApplicationContext(),
                                                "Falha ao excluir Contato",
                                                Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                // Setando ação do botão "Não"
                                dialog.setNegativeButton("Não", null);

                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );
    }

    @Override
    // Recarrega lista de contatos ao reiniciar activity
    protected void onStart() {
        carregarListaContatos();
        super.onStart();
    }

    public void carregarListaContatos() {
        // Listar contatos
        ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());
        listaContatos = contatoDAO.listar();

        // Configurando o adapter
        contatoAdapter = new ContatoAdapter(listaContatos);

        // Configurando o LayoutManeger necessário para o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaContatosRecycler.setLayoutManager(layoutManager);
        listaContatosRecycler.setHasFixedSize(true);
        listaContatosRecycler.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL)
        );

        //Setando o adapter no recycler
        listaContatosRecycler.setAdapter(contatoAdapter);
    }
}
