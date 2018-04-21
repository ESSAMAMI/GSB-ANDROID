package fr.gsb.gsb_rv_visiteur;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import fr.gsb.gsb_adapter.SliderAdapter;
import fr.gsb.gsb_technique.Session;

import static fr.gsb.gsb_rv_visiteur.R.id.session_user;

public class AccueilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView nomText ;
    private DrawerLayout drawerLayout ;
    private ActionBarDrawerToggle toggle ;
    private ViewPager viewPager;
    private SliderAdapter sliderAdapter ;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderAdapter = new SliderAdapter(AccueilActivity.this);
        viewPager.setAdapter(sliderAdapter);


        /*Session*/

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
