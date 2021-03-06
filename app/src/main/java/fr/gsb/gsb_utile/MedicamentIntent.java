package fr.gsb.gsb_utile;

import android.os.Parcel;
import android.os.Parcelable;

public class MedicamentIntent implements Parcelable {

    private String depot ;
    private String nom;
    private int qte ;

    public MedicamentIntent(String depot, String nom, int qte) {
        this.depot = depot;
        this.nom = nom;
        this.qte = qte;
    }

    public MedicamentIntent(String depot, String nom) {
        this.depot = depot;
        this.nom = nom;
    }

    public MedicamentIntent(){

    }

    protected MedicamentIntent(Parcel in) {
        depot = in.readString();
        nom = in.readString();
    }

    public static final Creator<MedicamentIntent> CREATOR = new Creator<MedicamentIntent>() {
        @Override
        public MedicamentIntent createFromParcel(Parcel in) {
            return new MedicamentIntent(in);
        }

        @Override
        public MedicamentIntent[] newArray(int size) {
            return new MedicamentIntent[size];
        }
    };

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(depot);
        parcel.writeString(nom);
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public static Creator<MedicamentIntent> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "MedicamentIntent{" +
                "depot='" + depot + '\'' +
                ", nom='" + nom + '\'' +
                ", qte=" + qte +
                '}';
    }
}
