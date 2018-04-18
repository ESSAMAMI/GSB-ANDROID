package fr.gsb.gsb_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.gsb.gsb_rv_visiteur.ConsulterActivity;
import fr.gsb.gsb_rv_visiteur.EchantillonActivity;
import fr.gsb.gsb_rv_visiteur.R;
import fr.gsb.gsb_utile.MedicamentIntent;

public class EchantillonAdapter extends BaseAdapter{

    private Context context;
    final List<Integer> quantite = new ArrayList<Integer>();
    private List<MedicamentIntent> medicamentIntents ;

    public EchantillonAdapter (Context context, List<MedicamentIntent> medicamentIntents){

        this.context = context;
        this.medicamentIntents = medicamentIntents;
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


        TextView tvEcha = (TextView) v.findViewById(R.id.ehantillon);
        Spinner spinnerQt = (Spinner) v.findViewById(R.id.spinner_echa_qua);
        for(int cpt = 1 ; cpt > 6 ; cpt++){

            quantite.add(cpt);
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, quantite);
        spinnerQt.setAdapter(arrayAdapter);
        /*tvEcha.setText(medicamentIntents.get(i).getNom());*/
        v.setTag(medicamentIntents.get(i).getDepot());

        return v;
    }
}
