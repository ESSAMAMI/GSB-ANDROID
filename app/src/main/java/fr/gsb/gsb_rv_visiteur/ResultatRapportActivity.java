package fr.gsb.gsb_rv_visiteur;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import fr.gsb.gsb_adapter.DialogueRapport;
import fr.gsb.gsb_adapter.RapportVisiteAdapter;
import fr.gsb.gsb_entites.Motif;
import fr.gsb.gsb_entites.Praticien;
import fr.gsb.gsb_entites.RapportVisite;
import fr.gsb.gsb_entites.Visiteur;
import fr.gsb.gsb_modele.ModeleGsb;
import fr.gsb.gsb_technique.DateFr;
import fr.gsb.gsb_technique.Session;


public class ResultatRapportActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView nomText ;
    private DrawerLayout drawerLayout ;
    private ActionBarDrawerToggle toggle ;

    private List<RapportVisite> lesRapportsVisites = new ArrayList<RapportVisite>() ;
    private List<Visiteur> lesVisiteurs = new ArrayList<Visiteur>();
    private  List<Praticien> lesPraticiens = new ArrayList<Praticien>();
    private List<Motif> lesMotif = new ArrayList<Motif>();
    private RapportVisiteAdapter adapter ;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_resultat);
        final ListView lvRapport = (ListView)findViewById(R.id.listeView_rapport);
        Bundle paquet = this.getIntent().getExtras();

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

        ArrayList<Integer> valeur = paquet.getIntegerArrayList("value");

        Integer mois = valeur.get(0);
        Integer annee = valeur.get(1);
        try {

            final String rap = String.valueOf(mois) +'.'+ String.valueOf(annee)+'.'+Session.getSession().getLeVisiteur().getMatricule();
            String visivisi = URLEncoder.encode(String.valueOf(rap), "UTF-8");
            final String url = String.format(getResources().getString(R.string.ipRap), rap);

            Response.Listener<JSONArray> ecouteur = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(final JSONArray response) {

                    for (int i = 0; i < response.length(); i++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            /* Le(s) Praticien(s) */
                            lesPraticiens.add(new Praticien(jsonObject.getInt("num_pra"),jsonObject.getString("nom_pra"), jsonObject.getString("prenom_pra")));
                            /* Le(s) Visiteur(s) */
                            lesVisiteurs.add(new Visiteur(jsonObject.getString("num_visi"), jsonObject.getString("nom_visi"), jsonObject.getString("prenom_visi")));
                            /* Le motif */
                            lesMotif.add(new Motif(jsonObject.getInt("motId"), jsonObject.getString("rapMotif")));

                            /*RapportVisite(int numero, String bilan, int coefConfiance, DateFr dateVisite, DateFr dateRedaction,boolean lu)*/

                            String[] dateVisite = jsonObject.getString("dateVisite").split("-");
                            String[] dateRedac = jsonObject.getString("rapDate").split("-");

                            int anneeVisite, moisVisite, jourVisite, anneeRedac, moisRedac, jourRedac ;

                            anneeVisite = Integer.parseInt(dateVisite[0]);
                            moisVisite = Integer.parseInt(dateVisite[1]);
                            jourVisite = Integer.parseInt(dateVisite[2]);

                            anneeRedac = Integer.parseInt(dateRedac[0]);
                            moisRedac = Integer.parseInt(dateRedac[1]);
                            jourRedac = Integer.parseInt(dateRedac[2]);

                            boolean vu ;
                            if(jsonObject.getInt("estVu") == 0){

                                vu = false ;
                                //Toast.makeText(ResultatRapportActivity.this, "false = "+jsonObject.getInt("estVu"), Toast.LENGTH_SHORT).show();
                            }else{

                                vu = true ;
                                //Toast.makeText(ResultatRapportActivity.this, "true = "+jsonObject.getInt("estVu"), Toast.LENGTH_SHORT).show();
                            }
                            lesRapportsVisites.add(new RapportVisite(jsonObject.getInt("num_rap"), jsonObject.getString("rapBilan"), jsonObject.getString("coefLibelle"),
                                    new DateFr(jourVisite, moisVisite, anneeVisite), new DateFr(jourRedac, moisRedac, anneeRedac), vu));

                            lesRapportsVisites.get(i).setLeVisiteur(lesVisiteurs.get(i));
                            lesRapportsVisites.get(i).setLePraticien(lesPraticiens.get(i));
                            lesRapportsVisites.get(i).setLeMotif(lesMotif.get(i));
                            //t1.setText(lesRapportsVisites.get(i).toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter = new RapportVisiteAdapter(getApplicationContext(), lesRapportsVisites);
                    lvRapport.setAdapter(adapter);

                    lvRapport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            //Toast.makeText(getApplicationContext(), "Rap num = " + view.getTag(), Toast.LENGTH_SHORT).show();

                            //Dialogue + Info Rapp
                            Integer intt = (Integer) view.getTag();
                            if(intt == 0){

                                intt = (Integer) view.getTag() + 1;
                            }

                            //Toast.makeText(getApplicationContext(), "Rap num = " + String.valueOf(i), Toast.LENGTH_SHORT).show();
                            if(!lesRapportsVisites.get(i).isLu()){

                               //Toast.makeText(ResultatRapportActivity.this, "On m'a vu ", Toast.LENGTH_LONG).show();

                                try {

                                    String id = URLEncoder.encode(String.valueOf(intt), "UTF-8");
                                    final String urlUpdate = String.format(getResources().getString(R.string.ipResuRap), id);

                                    Response.Listener<String> ecouteurUpdate = new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(ResultatRapportActivity.this, response, Toast.LENGTH_LONG).show();
                                        }

                                    };
                                    Response.ErrorListener errorListenerUpdate = new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(ResultatRapportActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, urlUpdate, ecouteurUpdate, errorListenerUpdate);
                                    RequestQueue requestQueue = Volley.newRequestQueue(ResultatRapportActivity.this);
                                    requestQueue.add(stringRequest);

                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                            }else{

                                //Toast.makeText(ResultatRapportActivity.this, "Je suis la faut voir qui ne va pas ", Toast.LENGTH_LONG).show();
                            }


                            //Toast.makeText(getApplicationContext(), lesRapportsVisites.get(i).toString(), Toast.LENGTH_LONG).show();

                            /*new AlertDialog.Builder(ResultatRapportActivity.this)
                                    .setTitle("RAPPORT " + String.valueOf(intt))
                                    .setMessage("1-")
                                    .setMessage("2-")

                                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            //Toast.makeText(MainActivity.this, "Button cliqué", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .setIcon(R.drawable.ic_file)
                                    .show();*/

                            /**int numRap, String bilan, String coef, String dateV, String dateR, String praticien, String motif**/
                            DialogueRapport dialogueRapport = new DialogueRapport(lesRapportsVisites.get(i).getNumero(),
                                    lesRapportsVisites.get(i).getBilan(),
                                    lesRapportsVisites.get(i).getCoefConfiance(),
                                    lesRapportsVisites.get(i).getDateVisite(),
                                    lesRapportsVisites.get(i).getDateRedaction(),
                                    lesRapportsVisites.get(i).getLePraticien().getNom(),
                                    lesRapportsVisites.get(i).getLeMotif().getLibelle());
                            dialogueRapport.show(getSupportFragmentManager(), "Dialogue");

                        }
                    });
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(ResultatRapportActivity.this, "JE suis vide", Toast.LENGTH_LONG).show();
                }
            };
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,ecouteur, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(ResultatRapportActivity.this);
            requestQueue.add(jsonArrayRequest);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
