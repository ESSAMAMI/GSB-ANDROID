package fr.gsb.gsb_adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import fr.gsb.gsb_rv_visiteur.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater ;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //Liste des images

    public int[] listImages = {

            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4

    };

    public String[] listTitre = {

            "Plate-forme APPLICATIVE",
            "CONSULTER",
            "AJOUTER",
            "PROFIL"
    };

    public String[] listDesc = {

            "Il est désormais possible maintenant d'acceder à l'application GSB.",
            "Vous pouvez consulter vos rapports de visites pour une date donnée.",
            "Vous pouvez saisir de nouveau rapport concernant une visite.",
            "Vous pouvez modifier votre mot de passe en toute sécurité."

    };

    public int[] listBg = {

            Color.rgb(167,99,213),
            Color.rgb(133,52,188),
            Color.rgb(113,32,166),
            Color.rgb(88,6,143)
    };

    @Override
    public int getCount() {
        return listTitre.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {


        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide, container, false);
        LinearLayout layoutSlide = (LinearLayout) view.findViewById(R.id.slideLinearLayout);
        ImageView imgSlide = (ImageView) view.findViewById(R.id.slideimg);
        TextView textTitre = (TextView) view.findViewById(R.id.texteTitle);
        TextView textDesc = (TextView) view.findViewById(R.id.descriptionTexte);

        layoutSlide.setBackgroundColor(listBg[position]);
        imgSlide.setImageResource(listImages[position]);
        textTitre.setText(listTitre[position]);
        textDesc.setText(listDesc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
