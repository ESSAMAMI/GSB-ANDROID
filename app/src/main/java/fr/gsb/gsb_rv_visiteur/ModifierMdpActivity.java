package fr.gsb.gsb_rv_visiteur;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import fr.gsb.gsb_technique.Session;

public class ModifierMdpActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout ;
    private ActionBarDrawerToggle toggle ;

    EditText ancienMdp, nouveauMdp, confirmerMdp;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_mdp);

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

    public void changerMDP (View v){

        nouveauMdp = (EditText) findViewById(R.id.new_mdp);
        ancienMdp = (EditText) findViewById(R.id.ancien_mdp);
        confirmerMdp = (EditText) findViewById(R.id.confirme_mdp);

        String nMdp, aMdp, cMdp;

        nMdp = String.valueOf(nouveauMdp.getText());
        aMdp = String.valueOf(ancienMdp.getText());
        cMdp = String.valueOf(confirmerMdp.getText());


        if(! aMdp.equals(Session.getSession().getLeVisiteur().getMdp())){

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ModifierMdpActivity.this);
            mBuilder.setTitle("Erreur").setMessage("L'ancien mot de passe est incorrect").setIcon(R.drawable.ic_info_outline_black_24dp);
            mBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });
            mBuilder.create().show();
        }else{

            if(nMdp.equals(cMdp)){

                //Toast.makeText(ModifierMdpActivity.this, "Le mot de passe est changé avec succées", Toast.LENGTH_LONG).show();

                final String stringNew = String.valueOf(nMdp);

                try {

                 String string = Session.getSession().getLeVisiteur().getMatricule()+'.'+String.valueOf(nMdp);
                 String mdp = URLEncoder.encode(String.valueOf(string), "UTF-8");
                 final String url = String.format(getResources().getString(R.string.mdp), mdp);

                 Response.Listener<String> ecouteurUpdate = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Session.getSession().getLeVisiteur().setMdp(stringNew);
                        //Toast.makeText(ModifierMdpActivity.this, response, Toast.LENGTH_LONG).show();

                    }

                };
                 Response.ErrorListener errorListenerUpdate = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModifierMdpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                     StringRequest stringRequest = new StringRequest(Request.Method.POST, url, ecouteurUpdate, errorListenerUpdate);
                     RequestQueue requestQueue = Volley.newRequestQueue(ModifierMdpActivity.this);
                     requestQueue.add(stringRequest);

                 } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
                 }


            }else{

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ModifierMdpActivity.this);
                mBuilder.setTitle("Erreur").setMessage("Les deux mot de passe ne sont pas identiques...").setIcon(R.drawable.ic_info_outline_black_24dp);
                mBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                mBuilder.create().show();
            }
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
