package fr.gsb.gsb_rv_visiteur;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.gsb.gsb_technique.Session;
import fr.gsb.gsb_utile.MedicamentIntent;
import fr.gsb.gsb_utile.RapportIntent;

public class EchantillonActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout ;
    private ActionBarDrawerToggle toggle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echantillon);

        Intent intent = getIntent();

        /* MES DONNES POUR CREER UN RAPPORT + OFFRIR*/
        RapportIntent rapportIntent = intent.getParcelableExtra("donnee");
        ArrayList<MedicamentIntent> medicamentIntents = intent.getParcelableArrayListExtra("echantillon");


        //Toast.makeText(EchantillonActivity.this, medicamentIntents.toString(), Toast.LENGTH_LONG ).show();

        /* ICI A FINIR */
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        Menu menu = navigationView.getMenu();
        View view = navigationView.getHeaderView(0);
        TextView nomText = (TextView) view.findViewById(R.id.session_user);
        String string = Session.getSession().getLeVisiteur().getNom()+" "+Session.getSession().getLeVisiteur().getPrenom();
        nomText.setText(string);

        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){

            return true ;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Toast.makeText(AccueilActivity.this, "Hey", Toast.LENGTH_LONG).show();
        switch (item.getItemId()){

            case R.id.nav_consulter:
                Intent consulter = new Intent(this, ConsulterActivity.class);
                startActivity(consulter);

                item.setChecked(true);
                break;
            case R.id.nav_saisir:
                Intent saisir = new Intent(this, SaisirRapportActivity.class);
                startActivity(saisir);

                item.setChecked(true);
                break;

            case R.id.nav_logout:
                Intent retour = new Intent(this, MainActivity.class);
                startActivity(retour);
                Session.fermer();
                item.setChecked(true);
                break;

        }
        return false;
    }
}
