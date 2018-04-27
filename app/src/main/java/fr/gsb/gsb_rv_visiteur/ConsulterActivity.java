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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.gsb.gsb_technique.DateFr;
import fr.gsb.gsb_technique.Session;

public class ConsulterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView nomText ;

    Spinner listeMois, listeAnnee ;
    final List<Integer> dateAnnee = new ArrayList<Integer>();
    final List<String> dateMois = new ArrayList<String>();

    private String mois ;
    private String annee ;


    private DrawerLayout drawerLayout ;
    private ActionBarDrawerToggle toggle ;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter);

        listeMois = (Spinner) findViewById(R.id.spinner_mois);
        listeAnnee = (Spinner) findViewById(R.id.spinner_annee);

        DateFr dateFr = new DateFr();
        for (int i = 0 ; i < 10 ; i++){

            dateAnnee.add(new Integer(dateFr.getAnnee() - i));
        }

        dateMois.add("Janvier");
        dateMois.add("Février");
        dateMois.add("Mars");
        dateMois.add("Avril");
        dateMois.add("Mai");
        dateMois.add("Juin");
        dateMois.add("Juillet");
        dateMois.add("Août");
        dateMois.add("Séptembre");
        dateMois.add("Octobre");
        dateMois.add("Novembre");
        dateMois.add("Décembre");
        
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(ConsulterActivity.this, R.layout.spinner_item, dateAnnee);
        ArrayAdapter<String> arrayAdapterMois = new ArrayAdapter<>(ConsulterActivity.this, R.layout.spinner_item, dateMois);

        listeAnnee.setAdapter(arrayAdapter);
        listeMois.setAdapter(arrayAdapterMois);

        listeAnnee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                annee = dateAnnee.get(i).toString();
                //Toast.makeText(ConsulterActivity.this, annee, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(ConsulterActivity.this, "Rien", Toast.LENGTH_SHORT).show();

            }
        });

        listeMois.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mois = String.valueOf(i+1) ;
                //Toast.makeText(ConsulterActivity.this, mois, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(ConsulterActivity.this, "Rien", Toast.LENGTH_SHORT).show();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        Menu menu = navigationView.getMenu();
        View view = navigationView.getHeaderView(0);
        nomText = (TextView) view.findViewById(R.id.session_user);
        String string =Session.getSession().getLeVisiteur().getNom()+" "+Session.getSession().getLeVisiteur().getPrenom();
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

    public void search(View v){


        /*Toast.makeText(ConsulterActivity.this, mois, Toast.LENGTH_SHORT).show();
        Toast.makeText(ConsulterActivity.this, annee, Toast.LENGTH_SHORT).show();*/
        Bundle donnees = new Bundle();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(Integer.parseInt(mois));
        arrayList.add(Integer.parseInt(annee));

        donnees.putIntegerArrayList("value", arrayList);

        Intent intention = new Intent(this, ResultatRapportActivity.class);
        intention.putExtras(donnees);
        startActivity(intention);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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

            case R.id.nav_mdp:

                Intent mdp = new Intent(this, ModifierMdpActivity.class);
                startActivity(mdp);

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
