package fr.gsb.gsb_adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

import fr.gsb.gsb_entites.RapportVisite;
import fr.gsb.gsb_rv_visiteur.R;

public class RapportVisiteAdapter extends BaseAdapter {

    private Context context;
    private List<RapportVisite> lesRapportsVisite ;

    //Constructeur


    public RapportVisiteAdapter(Context context, List<RapportVisite> lesRapportsVisite) {
        this.context = context;
        this.lesRapportsVisite = lesRapportsVisite;
    }

    @Override
    public int getCount() {
        return lesRapportsVisite.size();
    }

    @Override
    public Object getItem(int i) {
        return lesRapportsVisite.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.item_rapport_list, null);
        TextView tvRap = (TextView) v.findViewById(R.id.tv_nomRap);
        TextView tvDate = (TextView) v.findViewById(R.id.tv_date);
        TextView tvPar = (TextView) v.findViewById(R.id.tv_pra);
        TextView tvVu = (TextView)v.findViewById(R.id.vu);

        //Set Text for TextView
        String date = lesRapportsVisite.get(i).getDateRedaction().get(Calendar.DAY_OF_MONTH)+"/"+
                lesRapportsVisite.get(i).getDateRedaction().get(Calendar.MONTH) + 1 +"/"+
                lesRapportsVisite.get(i).getDateRedaction().get(Calendar.YEAR)
                ;

        tvRap.setText(String.valueOf(lesRapportsVisite.get(i).getNumero()));
        tvDate.setText(date);
        tvPar.setText(lesRapportsVisite.get(i).getLePraticien().getNom());

        if (lesRapportsVisite.get(i).isLu()){

            tvVu.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check, 0,0, 0);
        }
        v.setTag(lesRapportsVisite.get(i).getNumero());
        //v.setTag(lesRapportsVisite.get(i));

        return v;
    }
}
