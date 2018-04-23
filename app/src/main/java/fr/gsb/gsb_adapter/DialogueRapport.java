package fr.gsb.gsb_adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fr.gsb.gsb_rv_visiteur.R;

@SuppressLint("ValidFragment")
public class DialogueRapport extends DialogFragment {


    LayoutInflater layoutInflater ;
    View view ;
    private String bilan ;
    private int numRap ;
    private String coef;
    private GregorianCalendar dateV;
    private GregorianCalendar dateR;
    private String praticien;
    private String motif ;

    TextView tvBilan, tvCoef, tvDateV, tvDateR, tvPraticien, tvMotif;
    @SuppressLint("ValidFragment")
    public DialogueRapport(int numRap, String bilan, String coef, GregorianCalendar dateV, GregorianCalendar dateR, String praticien, String motif){

        this.numRap = numRap;
        this.bilan = bilan;
        this.coef = coef;
        this.dateV = dateV;
        this.dateR = dateR;
        this.praticien = praticien;
        this.motif = motif ;

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.layout_dialogue, null);
        tvBilan = (TextView) view.findViewById(R.id.bilanRap) ;
        tvCoef = (TextView) view.findViewById(R.id.coefRap);
        tvDateR = (TextView) view.findViewById(R.id.dateRedacRap);
        tvDateV = (TextView) view.findViewById(R.id.dateVisiteRap);
        tvPraticien = (TextView) view.findViewById(R.id.praticienRap);
        tvMotif = (TextView) view.findViewById(R.id.motifRap);

        tvBilan.setText(bilan);
        tvCoef.setText(coef);
        tvPraticien.setText(praticien);
        tvMotif.setText(motif);
        int anneeR, moisR, jourR, anneeV, moisV, jourV;
        anneeR = dateR.get(Calendar.YEAR);
        moisR = dateR.get(Calendar.MONTH) + 1;
        jourR = dateR.get(Calendar.DAY_OF_MONTH);

        anneeV = dateV.get(Calendar.YEAR);
        moisV = dateV.get(Calendar.MONTH) + 1;
        jourV = dateV.get(Calendar.DAY_OF_MONTH);
        String newDateR = String.valueOf(jourR)+"/"+String.valueOf(moisR)+"/"+String.valueOf(anneeR);
        String newDateV = String.valueOf(jourV)+"/"+String.valueOf(moisV)+"/"+String.valueOf(anneeV);
        tvDateV.setText(newDateV);
        tvDateR.setText(newDateR);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setIcon(R.drawable.ic_file);
        builder.setTitle("RAPPORT N° "+ String.valueOf(numRap));

        //ICI POUR COSTUMER LE DIALOGUE
        builder.setView(view).setNegativeButton("FERMER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNeutralButton("Echantillon", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                DialogueEchantillon dialogueEchantillon = new DialogueEchantillon(numRap);
                dialogueEchantillon.show(getFragmentManager(),"show");
            }
        });
        return builder.create();
    }
}
