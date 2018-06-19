package br.com.wilker.projeto2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.wilker.projeto2.R;
import br.com.wilker.projeto2.helpers.ContatoAdapter;
import br.com.wilker.projeto2.helpers.RecyclerItemClickListener;
import br.com.wilker.projeto2.models.Contato;

public class ContatosActivity extends AppCompatActivity {

    private RecyclerView listaContatosRecycler;
    private ContatoAdapter contatoAdapter;
    private List<Contato> listaContatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lista de Contatos");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarContatoActivity.class);
                startActivity(intent);
            }
        });

        // Configurando Recycler
        listaContatosRecycler = findViewById(R.id.listaContatos);

        // Adicionando evento de clique nos items
        listaContatosRecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        listaContatosRecycler,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                // Edição
                                Log.i("clique", "onItemClick");
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // Deleção (evento de quando segurar o click no item)
                                Log.i("clique", "onLongItemClick");
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );
    }

    @Override
    protected void onStart() {
        carregarListaContatos();
        super.onStart();
    }

    public void carregarListaContatos() {
        Contato c1 = new Contato();
        c1.setNomePessoa("Vinícius Regis");
        listaContatos.add(c1);

        Contato c2 = new Contato();
        c2.setNomePessoa("Wilker");
        listaContatos.add(c2);

        // Configurando o adapter
        contatoAdapter = new ContatoAdapter(listaContatos);

        // Configurando Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaContatosRecycler.setLayoutManager(layoutManager);
        listaContatosRecycler.setHasFixedSize(true);
        listaContatosRecycler.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL)
        );
        listaContatosRecycler.setAdapter(contatoAdapter);
    }
}
