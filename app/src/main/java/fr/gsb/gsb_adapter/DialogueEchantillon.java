package fr.gsb.gsb_adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import fr.gsb.gsb_entites.Medicament;
import fr.gsb.gsb_rv_visiteur.R;
import fr.gsb.gsb_rv_visiteur.ResultatRapportActivity;

@SuppressLint("ValidFragment")
public class DialogueEchantillon extends DialogFragment {

    private List<Medicament> medicaments = new ArrayList<Medicament>();
    private LayoutInflater layoutInflater ;
    private View view ;
    private TextView tvMedocs, tvQte;
    private int numRap ;
    public DialogueEchantillon(int numRap){

        this.numRap = numRap;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.layout_dialogue_echantillon, null);
        final ListView listView = (ListView) view.findViewById(R.id.list_view_medocs_ehcantillon);


        /*tvMedocs = (TextView) view.findViewById(R.id.tvEchantillonDialogue);*/

        try {

            int num = numRap;
            String echa = URLEncoder.encode(String.valueOf(num), "UTF-8");
            final String url = String.format(getResources().getString(R.string.echantillon), echa);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            Response.Listener<JSONArray> ecouter = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            medicaments.add(new Medicament(jsonObject.getString("nom"), jsonObject.getInt("quantite")));
                            EchantillonDialogueAdapter echantillonDialogueAdapter = new EchantillonDialogueAdapter(getContext(), medicaments);
                            listView.setAdapter(echantillonDialogueAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            };
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,ecouter, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonArrayRequest);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setIcon(R.drawable.pill_color);
        builder.setTitle("ÉCHANTILLON(S)");

        builder.setView(view);

        builder.setPositiveButton("FERMER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Toast.makeText(getContext(), "Je suis la", Toast.LENGTH_LONG).show();

            }
        });

        return builder.create();
    }
}
