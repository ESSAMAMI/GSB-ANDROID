package fr.gsb.gsb_rv_visiteur;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import fr.gsb.gsb_entites.Visiteur;
import fr.gsb.gsb_modele.ModeleGsb;
import fr.gsb.gsb_technique.Session;
import fr.gsb.gsb_technique.Singleton;

public class MainActivity extends Activity {
    Button valider ;
    TextView output ;
    EditText matricule, mdp ;
    protected Visiteur visi ;
    protected ModeleGsb modele = ModeleGsb.getInstance() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        valider = findViewById(R.id.connexion);

        output = (TextView) findViewById(R.id.tele);
        matricule = (EditText)findViewById(R.id.matricule);
        mdp = (EditText)findViewById(R.id.mdp);

    }

    public void Connexion(View v) throws UnsupportedEncodingException {

        String auth = matricule.getText().toString().trim() +'.'+String.valueOf(mdp.getText().toString().trim());
        final String visi = URLEncoder.encode(auth, "UTF-8");
        String url = String.format(getResources().getString(R.string.ipConnexion), visi);

        Response.Listener<JSONObject> ecouteur = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    Visiteur visiteur = new Visiteur();
                    visiteur.setNom(response.getString("nom"));
                    visiteur.setPrenom(response.getString("prenom"));
                    visiteur.setMdp(response.getString("mdp"));
                    visiteur.setMatricule(response.getString("matricule"));

                    Session.ouvrir(response.getString("matricule"), response.getString("mdp"), visiteur);
                    Intent i = new Intent(MainActivity.this, AccueilActivity.class);
                    startActivity(i);

                    //Toast.makeText(MainActivity.this, response.getString("matricule")+' '+response.getString("mdp"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Connexion refusée")
                        .setMessage("Le login et ou le mot de passe est faux." +
                                " Veuillez réessayer...")
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Toast.makeText(MainActivity.this, "Button cliqué", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setIcon(R.drawable.ic_error)
                .show();
            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, ecouteur,errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);


    }

    public void effacer(View v){

        matricule.setText("");
        mdp.setText("");
        output.setText("");

    }

}
