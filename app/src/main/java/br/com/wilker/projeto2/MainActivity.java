package br.com.wilker.projeto2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import br.com.wilker.projeto2.activities.ContatosActivity;
import br.com.wilker.projeto2.activities.MapaActivity;
import br.com.wilker.projeto2.fragments.CalculadoraFragment;
import br.com.wilker.projeto2.R;
import br.com.wilker.projeto2.fragments.WebViewFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // placeholder da página atual
    FrameLayout pageContainer;

    // barra superior
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        // seta a toolbar na página
        setSupportActionBar(toolbar);
        pageContainer = findViewById(R.id.page_container);

        // prepara o menu lateral
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // abre o navegador como padrão
        openWebViewFragment();
    }

    // trata o botão de voltar para fechar o menu lateral caso ele estiver aberto
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // prepara os itens da barra superior
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    // Verifica o item clicado e abre a respectiva view
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_item_web_view) {
            openWebViewFragment();
        } else if (id == R.id.nav_item_contatos) {
            openContatosActivity();
        } else if (id == R.id.nav_item_calculadora) {
            openCalculadoraFragment();
        } else if (id == R.id.nav_item_me_mostre_no_mapa) {
            openMapaActivity();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Substitui o placeholder por um novo fragmento
    protected void replaceFragment(Fragment novoFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.page_container, novoFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Abre o fragmento do navegador
    protected void openWebViewFragment() {
        toolbar.setTitle(getResources().getString(R.string.webview));
        Fragment webViewFragment = new WebViewFragment();
        replaceFragment(webViewFragment);
    }

    // Abre a activity de mapa
    protected void openMapaActivity() {
        startActivity(new Intent(this, MapaActivity.class));
    }

    // Abre o fragmento de contatos
    protected void openContatosActivity() {
        startActivity(new Intent(this, ContatosActivity.class));
//        toolbar.setTitle(getResources().getString(R.string.lista_contatos));
    }

    // Abre o fragmento da calculadora
    protected void openCalculadoraFragment() {
        toolbar.setTitle(getResources().getString(R.string.calculadora));
        Fragment calculadoraFragment = new CalculadoraFragment();
        replaceFragment(calculadoraFragment);
    }

}
