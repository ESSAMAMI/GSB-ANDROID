package fr.gsb.gsb_rv_visiteur;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.gsb.gsb_entites.Coefeciant;
import fr.gsb.gsb_entites.Medicament;
import fr.gsb.gsb_entites.Motif;
import fr.gsb.gsb_entites.Praticien;
import fr.gsb.gsb_technique.DateFr;
import fr.gsb.gsb_technique.DatePickerFragment;
import fr.gsb.gsb_technique.Session;
import fr.gsb.gsb_test_fonctionnel.InsertRapp;
import fr.gsb.gsb_utile.MedicamentIntent;
import fr.gsb.gsb_utile.RapportIntent;

import static java.lang.String.*;

public class SaisirRapportActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener{


    private DrawerLayout drawerLayout ;
    private List<Medicament> arrayMedocs = new ArrayList<Medicament>();
    private ActionBarDrawerToggle toggle ;
    private String dateVisite ;
    private String bilan ;
    private int idCoef ;
    private  int idPra ;
    private int idMot;
    List<String> stringId = new ArrayList<String>() ;
    private List<Medicament> lesMedicaments = new ArrayList<Medicament>();

    Spinner listCoef, listPra, listMotif ;
    Button suivant, echantillon ;
    EditText bilanTv ;
    private String[] listItems ;
    private boolean[] checkedItem ;
    ArrayList<Integer> integers = new ArrayList<>();

    private List<Coefeciant> coefeciants = new ArrayList<Coefeciant>();
    private List<Praticien> praticiens = new ArrayList<Praticien>();
    private List<Motif> motifs = new ArrayList<Motif>();
    private TextView nomText ;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisir_rapport);

        listCoef = (Spinner) findViewById(R.id.spinner_coefConf);
        listPra = (Spinner) findViewById(R.id.spinner_praticien);
        listMotif = (Spinner) findViewById(R.id.spinner_motif);
        suivant = (Button) findViewById(R.id.suivant);
        echantillon = (Button) findViewById(R.id.ehantillon);

        try {

            String getMedocs = "getMedocs" ;
            final String donneeMed = URLEncoder.encode(getMedocs, "UTF-8");
            String urlMed = format(getResources().getString(R.string.ip), donneeMed);

            Response.Listener<JSONArray> ecouteurMed = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            //Toast.makeText(SaisirRapportActivity.this, jsonObject.getString("depot"), Toast.LENGTH_LONG).show();
                            lesMedicaments.add(new Medicament(jsonObject.getString("depot"), jsonObject.getString("nom")));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    /* J'INDEX MON TABLEAU */
                    listItems = new String[lesMedicaments.size()] ;
                    for (int i = 0 ; i < lesMedicaments.size(); i++){

                        listItems[i] = lesMedicaments.get(i).getNomCommercial();

                    // POUR CHAQUE INDICE JE LUI DONNE UNE VALEUR
                    }


                    checkedItem = new boolean[listItems.length];
                    echantillon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(SaisirRapportActivity.this);
                            mBuilder.setTitle("ECHANTILLONS");

                            mBuilder.setMultiChoiceItems(listItems, checkedItem, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                                    if(isChecked){

                                        if(!integers.contains(position)){

                                            integers.add(position);

                                        }else{

                                            integers.remove(position);
                                        }
                                    }
                                }
                            });
                            mBuilder.setCancelable(false);
                            mBuilder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int wich) {
                                    String item = "";
                                    for (int i = 0; i <integers.size(); i++){
                                        item = item +"-"+lesMedicaments.get(integers.get(i)).getNomCommercial();

                                        arrayMedocs.add(new Medicament(lesMedicaments.get(integers.get(i)).getDepotLegal(),
                                                lesMedicaments.get(integers.get(i)).getNomCommercial()));

                                        //Toast.makeText(SaisirRapportActivity.this, item, Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });

                            mBuilder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();

                                }
                            });

                            mBuilder.setNeutralButton("EFFACER", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {

                                    for(int i = 0; i < checkedItem.length; i++){

                                        checkedItem[i] = false;
                                        integers.clear();

                                    }
                                }
                            });

                            AlertDialog dialog = mBuilder.create();
                            dialog.show();
                        }
                    });

                }
            };

            Response.ErrorListener errorListenerMed = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(SaisirRapportActivity.this ,"rie..........n", Toast.LENGTH_SHORT).show();
                }
            };

            JsonArrayRequest jsonArrayRequest3 = new JsonArrayRequest(Request.Method.GET, urlMed, null,ecouteurMed,  errorListenerMed);
            RequestQueue requestQueue3 = Volley.newRequestQueue(SaisirRapportActivity.this);
            requestQueue3.add(jsonArrayRequest3);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        bilanTv = (EditText) findViewById(R.id.bilan);
        Button button = (Button) findViewById(R.id.datePicker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        Menu menu = navigationView.getMenu();
        View view = navigationView.getHeaderView(0);
        nomText = (TextView) view.findViewById(R.id.session_user);
        String string = Session.getSession().getLeVisiteur().getNom()+" "+Session.getSession().getLeVisiteur().getPrenom();
        nomText.setText(string);

        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        try {

            String getCoef = "getCoefeciant" ;
            final String donnee = URLEncoder.encode(getCoef, "UTF-8");
            String url = format(getResources().getString(R.string.ip), donnee);

            Response.Listener<JSONArray> ecouteur = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++){

                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            //Toast.makeText(SaisirRapportActivity.this, jsonObject.getString("libelle"), Toast.LENGTH_LONG).show();
                            coefeciants.add(new Coefeciant(jsonObject.getInt("id"), jsonObject.getString("libelle")));

                            ArrayAdapter<Coefeciant> arrayAdapterCoef = new ArrayAdapter<>(SaisirRapportActivity.this, R.layout.spinner_item_coef, coefeciants);
                            listCoef.setAdapter(arrayAdapterCoef);

                            listCoef.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    idCoef = coefeciants.get(i).getIdCoef();
                                    //Toast.makeText(SaisirRapportActivity.this, String.valueOf(idCoef), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    Toast.makeText(SaisirRapportActivity.this, "RIEN", Toast.LENGTH_LONG).show();
                                }
                            });

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(SaisirRapportActivity.this, "Je suis VIDE", Toast.LENGTH_LONG).show();
                }
            };

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,ecouteur, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(SaisirRapportActivity.this);
            requestQueue.add(jsonArrayRequest);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {

            String getPra = "getPraticien" ;
            final String donneePra = URLEncoder.encode(getPra, "UTF-8");
            String urlPra = format(getResources().getString(R.string.ip), donneePra);

            Response.Listener<JSONArray> ecouteurPra = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            praticiens.add(new Praticien(jsonObject.getInt("id"), jsonObject.getString("nom"), jsonObject.getString("prenom")));

                            ArrayAdapter<Praticien> arrayAdapter = new ArrayAdapter<>(SaisirRapportActivity.this, R.layout.spinner_praticien, praticiens);
                            listPra.setAdapter(arrayAdapter);

                            listPra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    idPra = praticiens.get(i).getNumero();
                                    //Toast.makeText(SaisirRapportActivity.this, String.valueOf(idPra), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                    Toast.makeText(SaisirRapportActivity.this, "Rien du tout", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Response.ErrorListener errorListenerPra = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(SaisirRapportActivity.this, "JE suis la", Toast.LENGTH_LONG).show();
                }
            };

            JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Request.Method.GET, urlPra, null,ecouteurPra, errorListenerPra);
            RequestQueue requestQueue1 = Volley.newRequestQueue(SaisirRapportActivity.this);
            requestQueue1.add(jsonArrayRequest1);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        try {

            String getMot = "getMotif" ;
            final String donneeMot = URLEncoder.encode(getMot, "UTF-8");
            String urlMot = format(getResources().getString(R.string.ip), donneeMot);

            Response.Listener<JSONArray> ecouteurMot = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(i);

                            motifs.add(new Motif(jsonObject.getInt("id"), jsonObject.getString("libelle")));

                            ArrayAdapter<Motif> arrayAdapter = new ArrayAdapter<>(SaisirRapportActivity.this, R.layout.spinner_motif, motifs);

                            listMotif.setAdapter(arrayAdapter);

                            listMotif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    idMot = motifs.get(i).getCode();
                                   // Toast.makeText(SaisirRapportActivity.this, String.valueOf(idMot), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    Toast.makeText(SaisirRapportActivity.this, "Pas de motif", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            Response.ErrorListener errorListenerMot = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(SaisirRapportActivity.this ,"Motif null", Toast.LENGTH_SHORT).show();
                }
            };

            JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, urlMot, null,ecouteurMot,  errorListenerMot);
            RequestQueue requestQueue2 = Volley.newRequestQueue(SaisirRapportActivity.this);
            requestQueue2.add(jsonArrayRequest2);

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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);

        String currentDate = DateFormat.getDateInstance().format(date.getTime());
        TextView tvDate = (TextView) findViewById(R.id.tv_datePicker);
        int mois = date.get(Calendar.MONTH ) + 1;
        dateVisite = valueOf(date.get(Calendar.YEAR)+"-"+mois+"-"+date.get(Calendar.DAY_OF_MONTH));
        //Toast.makeText(SaisirRapportActivity.this, dateVisite, Toast.LENGTH_LONG).show();
        tvDate.setText(currentDate);

    }

    public void getData(View v){


        /************ POST *************/

        bilan = bilanTv.getText().toString();

        DateFr dateFr = new DateFr();
        String dateRedac = String.valueOf(dateFr.getAnnee()+"-"+dateFr.getMois()+"-"+dateFr.getJour());

        RapportIntent rapportIntent = new RapportIntent(dateVisite, idCoef, idPra, idMot, bilan);

        ArrayList<MedicamentIntent> medicamentIntents = new ArrayList<MedicamentIntent>();
        for (int i =0; i < arrayMedocs.size(); i++) {

            medicamentIntents.add(new MedicamentIntent(arrayMedocs.get(i).getDepotLegal(), arrayMedocs.get(i).getNomCommercial()));
            //Toast.makeText(SaisirRapportActivity.this, medicamentIntents.get(i).getNom(), Toast.LENGTH_LONG).show();
        }

        final InsertRapp insertRapp = new InsertRapp(Session.getSession().getLeVisiteur().getMatricule(),
                0, rapportIntent.getIdPra(), rapportIntent.getBilan(), dateRedac,
                rapportIntent.getIdCoef(), dateVisite, 0, rapportIntent.getIdMot()
                );

        GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getResources().getString(R.string.ipPost);

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rap", gson.toJson(insertRapp));
                return params;
            }
        };

        queue.add(strRequest);

        /************ END POST *************/
        Intent intention = new Intent(this, EchantillonActivity.class);
        intention.putExtra("echantillon", medicamentIntents);
        startActivity(intention);

    }


}
