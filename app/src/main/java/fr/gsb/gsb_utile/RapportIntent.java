package fr.gsb.gsb_utile;

import android.os.Parcel;
import android.os.Parcelable;

public class RapportIntent implements Parcelable{

    private String date ;
    private int idCoef ;
    private int idPra ;
    private int idMot ;
    private String bilan ;

    public RapportIntent(String date, int idCoef, int idPra, int idMot, String bilan) {
        this.date = date;
        this.idCoef = idCoef;
        this.idPra = idPra;
        this.idMot = idMot;
        this.bilan = bilan;
    }

    protected RapportIntent(Parcel in) {
        date = in.readString();
        idCoef = in.readInt();
        idPra = in.readInt();
        idMot = in.readInt();
        bilan = in.readString();
    }

    public static final Creator<RapportIntent> CREATOR = new Creator<RapportIntent>() {
        @Override
        public RapportIntent createFromParcel(Parcel in) {
            return new RapportIntent(in);
        }

        @Override
        public RapportIntent[] newArray(int size) {
            return new RapportIntent[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdCoef() {
        return idCoef;
    }

    public void setIdCoef(int idCoef) {
        this.idCoef = idCoef;
    }

    public int getIdPra() {
        return idPra;
    }

    public void setIdPra(int idPra) {
        this.idPra = idPra;
    }

    public int getIdMot() {
        return idMot;
    }

    public void setIdMot(int idMot) {
        this.idMot = idMot;
    }

    public String getBilan() {
        return bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeInt(idCoef);
        parcel.writeInt(idPra);
        parcel.writeInt(idMot);
        parcel.writeString(bilan);
    }
}
