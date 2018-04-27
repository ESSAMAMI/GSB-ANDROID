package fr.gsb.gsb_rv_visiteur;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.gsb.gsb_adapter.EchantillonAdapter;
import fr.gsb.gsb_entites.Medicament;
import fr.gsb.gsb_technique.Session;
import fr.gsb.gsb_utile.MedicamentIntent;
import fr.gsb.gsb_utile.Offrir;
import fr.gsb.gsb_utile.RapportIntent;

public class EchantillonActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout ;
    private ActionBarDrawerToggle toggle ;
    private EchantillonAdapter adapter;
    private List<Integer> quantite = new ArrayList<Integer>();
    private List<MedicamentIntent> lesMedocs = new ArrayList<MedicamentIntent>();

    private int qte = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echantillon);
        final ListView lvEchantillon = (ListView) findViewById(R.id.listeView_medocs);
        final Button valider = (Button) findViewById(R.id.validerEchantillon);
        Intent intent = getIntent();

        /* MES DONNES POUR CREER UN RAPPORT + OFFRIR*/
        final ArrayList<MedicamentIntent> medicamentIntents = intent.getParcelableArrayListExtra("echantillon");


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

        /*********** ICI JE CRÉE MA LISTE SPINNER ***********/


        class EchantillonItemAdapter extends ArrayAdapter<MedicamentIntent> {

            private  ArrayAdapter<Integer> arrayAdapter ;

            EchantillonItemAdapter(Activity contexte, ArrayAdapter<Integer> arrayAdapter){

                super(contexte, R.layout.item_echantillon, medicamentIntents);
                this.arrayAdapter = arrayAdapter;
            }


            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View ligneItem = convertView;
                if(ligneItem == null){

                    LayoutInflater convertisseur = getLayoutInflater();
                    ligneItem = convertisseur.inflate(R.layout.item_echantillon, parent, false);

                }

                TextView tvEchantillon = (TextView) ligneItem.findViewById(R.id.ech);
                tvEchantillon.setText(medicamentIntents.get(position).getNom());


                Spinner spinnerQte = (Spinner) ligneItem.findViewById(R.id.spinner_echa_qua);

                /*ArrayAdapter<Integer> aQ = new ArrayAdapter<Integer>(EchantillonActivity.this, R.layout.spinner_item, getQte() );
                aQ.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);*/
                spinnerQte.setAdapter(arrayAdapter);

                int getQte = medicamentIntents.get(position).getQte();
                spinnerQte.setSelection(getQte);
                spinnerQte.setTag(position);

                spinnerQte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int qteSelected, long l) {

                        Integer getPositionMedocs = (Integer)((Spinner) view.getParent()).getTag();

                        /*Toast.makeText(EchantillonActivity.this, "Medocs est => "+medicamentIntents.get(getPositionMedocs).getNom() +"Qte =>"+
                                String.valueOf(qteSelected + 1), Toast.LENGTH_LONG).show();*/

                        medicamentIntents.get(getPositionMedocs).setQte(qteSelected + 1);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                        Toast.makeText(EchantillonActivity.this, "BAH RIEN", Toast.LENGTH_SHORT).show();
                    }
                });

                return ligneItem ;
            }
        }

        for (int i =1; i < 6 ; i++){

            quantite.add(i);
        }

        ArrayAdapter<Integer> aQ = new ArrayAdapter<Integer>(EchantillonActivity.this, R.layout.spinner_item, quantite);
        aQ.setDropDownViewResource(R.layout.spinner_quantite);
        EchantillonItemAdapter echantillonItemAdapter = new EchantillonItemAdapter(EchantillonActivity.this, aQ);
        lvEchantillon.setAdapter(echantillonItemAdapter);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(EchantillonActivity.this, String.valueOf(medicamentIntents.toString()), Toast.LENGTH_LONG).show();

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();
                List<Medicament> medicaments = new ArrayList<Medicament>();

                final List<Offrir> offrirs = new ArrayList<Offrir>();
                for (int i = 0 ; i < medicamentIntents.size(); i++){

                    offrirs.add(new Offrir(Session.getSession().getLeVisiteur().getMatricule(),
                            0, medicamentIntents.get(i).getDepot(), medicamentIntents.get(i).getQte(), medicamentIntents.size()));
                }
                RequestQueue queue = Volley.newRequestQueue(EchantillonActivity.this);
                String url = getResources().getString(R.string.offrir);

                StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                               // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder dBuilder = new AlertDialog.Builder(EchantillonActivity.this);
                                dBuilder.setIcon(R.drawable.ic_info_outline_black_24dp);
                                dBuilder.setTitle(" SUCCÈS");
                                dBuilder.setMessage("Le rapport a été enregistré avec succès ☺");

                                dBuilder.show();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                AlertDialog.Builder dBuilder = new AlertDialog.Builder(EchantillonActivity.this);
                                dBuilder.setIcon(R.drawable.ic_error);
                                dBuilder.setTitle(" ERREUR");
                                dBuilder.setMessage("Une erreur est survenue. Veuillez réessayer ");

                                dBuilder.show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>(offrirs.size());
                        for (int i = 0 ; i < offrirs.size(); i++) {

                            params.put("medicament" + i, gson.toJson(offrirs.get(i)));

                        }

                        return params;
                    }
                };

                queue.add(strRequest);

            }
        });


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
