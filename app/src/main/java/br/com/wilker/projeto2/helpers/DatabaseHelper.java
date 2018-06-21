package br.com.wilker.projeto2.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static int VERSION = 1;
    public static String DB_NAME = "agenda_telefonica";
    public static String TB_CONTATO = "contato";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    // Cria o banco e a tabela de contatos (executado somente uma vez)
    public void onCreate(SQLiteDatabase db) {
        String sqlTableContato = "CREATE TABLE IF NOT EXISTS " + TB_CONTATO
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    " nome VARCHAR(255) NOT NULL, "+
                    " email VARCHAR(255), "+
                    " telefone VARCHAR(255) NOT NULL ); ";

        try {
            db.execSQL(sqlTableContato);
            Log.i("INFO DB", "Sucesso ao criar a table");
        } catch(Exception e) {
            Log.i("INFO DB", "Erro ao criar a table: "+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
