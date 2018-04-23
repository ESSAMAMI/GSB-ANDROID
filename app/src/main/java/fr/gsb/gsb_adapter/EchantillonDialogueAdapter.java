package fr.gsb.gsb_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.gsb.gsb_entites.Medicament;
import fr.gsb.gsb_rv_visiteur.R;

public class EchantillonDialogueAdapter extends BaseAdapter {

    private Context context ;
    private List<Medicament> lesMedicaments ;

    EchantillonDialogueAdapter(Context context, List<Medicament> lesMedicaments){

        this.context = context;
        this.lesMedicaments = lesMedicaments ;
    }


    @Override
    public int getCount() {
        return lesMedicaments.size();
    }

    @Override
    public Object getItem(int i) {
        return lesMedicaments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView tvNom, tvId, tvQte;
        View v = View.inflate(context, R.layout.item_echantillon_dialogue_list, null);

        tvNom = (TextView) v.findViewById(R.id.tvEchantillonDialogue);
        tvId = (TextView) v.findViewById(R.id.echantillon_id);
        tvQte= (TextView) v.findViewById(R.id.echantillon_qte);

        String string = "Échantillon n°"+String.valueOf(i+1);
        tvId.setText(string);
        tvNom.setText(lesMedicaments.get(i).getNomCommercial());

        tvQte.setText(String.valueOf(lesMedicaments.get(i).getQte()));

        return v;
    }
}
