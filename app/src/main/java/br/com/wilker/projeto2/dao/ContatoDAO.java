package br.com.wilker.projeto2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.wilker.projeto2.helpers.DatabaseHelper;
import br.com.wilker.projeto2.models.Contato;

public class ContatoDAO implements IContatoDAO{

    private SQLiteDatabase escritor;
    private SQLiteDatabase leitor;

    public ContatoDAO(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);

        // objeto responsável pela manipulação de dados do banco
        escritor = db.getWritableDatabase();

        // objeto responsável pela leitura de dados do banco
        leitor = db.getReadableDatabase();
    }

    @Override
    // Salva um novo contato
    public boolean salvar(Contato contato) {
        ContentValues cv = new ContentValues();
        cv.put("nome", contato.getNomePessoa());
        cv.put("telefone", contato.getNumero());
        cv.put("email", contato.getEmail());

        try{
            escritor.insert(DatabaseHelper.TB_CONTATO, null, cv);
            Log.e("INFO", "Contato salvo com sucesso");
        } catch(Exception e) {
            Log.e("INFO", "Falha ao salvar contato: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    // Atualiza um contato existente
    public boolean atualizar(Contato contato) {
        ContentValues cv = new ContentValues();
        cv.put("nome", contato.getNomePessoa());
        cv.put("telefone", contato.getNumero());
        cv.put("email", contato.getEmail());

        // Array com os valores para o where no sql
        String[] where = {contato.getId().toString()};
        try{
            escritor.update(DatabaseHelper.TB_CONTATO, cv, "id=?", where);
            Log.e("INFO", "Contato salvo com sucesso");
        } catch(Exception e) {
            Log.e("INFO", "Falha ao salvar contato: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    // Deleta um contato
    public boolean deletar(Contato contato) {
        // Array com os valores para o where no sql
        String[] where = {contato.getId().toString()};

        try{
            escritor.delete(DatabaseHelper.TB_CONTATO, "id=?", where);
            Log.e("INFO", "Contato excluído com sucesso");
        } catch(Exception e) {
            Log.e("INFO", "Falha ao excluir contato: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    // Retorna a lista de contatos cadastrados
    public List<Contato> listar() {

        List<Contato> contatos = new ArrayList<>();

        String sql = "SELECT * FROM " + DatabaseHelper.TB_CONTATO + " ;";
        Cursor c = leitor.rawQuery(sql, null);

        // Adicionando cada item no cursor na lista de contatos
        while(c.moveToNext()) {
            Contato contato = new Contato();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomePessoa = c.getString(c.getColumnIndex("nome"));
            String numero = c.getString(c.getColumnIndex("telefone"));
            String email = c.getString(c.getColumnIndex("email"));

            contato.setId(id);
            contato.setNomePessoa(nomePessoa);
            contato.setNumero(numero);
            contato.setEmail(email);

            contatos.add(contato);
        }

        return contatos;
    }
}
