package br.com.wilker.projeto2.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.wilker.projeto2.R;
import br.com.wilker.projeto2.dao.ContatoDAO;
import br.com.wilker.projeto2.models.Contato;

public class AdicionarContatoActivity extends AppCompatActivity {

    private TextInputEditText editNome;
    private TextInputEditText editNumero;
    private TextInputEditText editEmail;
    private Contato contatoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_contato);

        // Recuperando ActionBar
        ActionBar toolbar = getSupportActionBar();

        // Recuperando valores da tela
        editNome = findViewById(R.id.txtNome);
        editNumero = findViewById(R.id.txtNumero);
        editEmail = findViewById(R.id.txtEmail);

        // Recuperando o contato, caso edição
        contatoAtual = (Contato) getIntent().getSerializableExtra("contatoSelected");

        // Popula os valores do contato em edição
        if(contatoAtual != null) {
            editNome.setText(contatoAtual.getNomePessoa());
            editNumero.setText(contatoAtual.getNumero());
            editEmail.setText(contatoAtual.getEmail());

            // Mudando titulo toolbar
            toolbar.setTitle("Editar Contato");
        } else {
            toolbar.setTitle("Adicionar Contato");
        }

    }

    @Override
    // Inclui o botao (ícone) de salvar o contato
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_contato, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    // Açoes de click do botão de salvar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSalvar:
                // Ação de salvar e editar o contato
                ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());

                // Recuperando os valores do usuário
                String nome = editNome.getText().toString();
                String numero = editNumero.getText().toString();
                String email = editEmail.getText().toString();

                // Validando se está vazio os campos obrigatórios
                if(!nome.isEmpty() && ! numero.isEmpty()){
                    Contato contato = new Contato();
                    contato.setNomePessoa(nome);
                    contato.setNumero(numero);
                    contato.setEmail(email);


                    if(contatoAtual != null) {
                        // Bloco editar contato existente
                        contato.setId(contatoAtual.getId());

                        if(contatoDAO.atualizar(contato)){
                            Toast.makeText(
                                getApplicationContext(),
                                "Contato atualizado com sucesso",
                                Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(
                                getApplicationContext(),
                                "Falha ao atualizar Contato",
                                Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if(contatoDAO.salvar(contato)) {
                            Toast.makeText(
                                getApplicationContext(),
                                "Contato adicionado com sucesso",
                                Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(
                                getApplicationContext(),
                                "Falha ao adicionar Contato",
                                Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Nome e número obrigatórios!",
                            Toast.LENGTH_SHORT).show();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
