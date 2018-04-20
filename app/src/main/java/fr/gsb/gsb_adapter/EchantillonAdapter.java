package fr.gsb.gsb_adapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.gsb.gsb_rv_visiteur.R;
import fr.gsb.gsb_utile.MedicamentIntent;

public class EchantillonAdapter extends BaseAdapter{

    private Context context;
    final List<Integer> quantite = new ArrayList<Integer>();
    private List<MedicamentIntent> medicamentIntents ;
    private ArrayAdapter<Integer> list ;

    public EchantillonAdapter (Context context, List<MedicamentIntent> medicamentIntents, ArrayAdapter<Integer> list){

        this.context = context;
        this.medicamentIntents = medicamentIntents;
        this.list = list ;
    }
    @Override
    public int getCount() {
        return medicamentIntents.size();
    }

    @Override
    public Object getItem(int i) {
        return medicamentIntents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v  = View.inflate(context, R.layout.item_echantillon, null);


        TextView tvEcha = (TextView) v.findViewById(R.id.ech);
        Spinner spinnerQt = (Spinner) v.findViewById(R.id.spinner_echa_qua);
        for(int cpt = 1 ; cpt < medicamentIntents.size() ; cpt++){

            quantite.add(cpt);
        }
        //ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, quantite);
        spinnerQt.setAdapter(list);

        spinnerQt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(context, String.valueOf(quantite.get(i)), Toast.LENGTH_SHORT).show();


                Toast.makeText(context, medicamentIntents.get(i).getNom(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        tvEcha.setText(medicamentIntents.get(i).getNom());

        v.setTag(medicamentIntents.get(i).getDepot());

        return v;
    }
}
