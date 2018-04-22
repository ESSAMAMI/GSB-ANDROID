package fr.gsb.gsb_entites;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import fr.gsb.gsb_technique.DateFr;

public class RapportVisite {

	private int numero ;
	private String bilan ;
	private String coefConfiance ;
	private DateFr dateVisite ;
	private DateFr dateRedaction ;
	private boolean lu ;
	
	private Praticien lePraticien ;
	private Visiteur leVisiteur ;
	private Motif leMotif ;
	private Map<Medicament,Integer> lesEchantillons = new HashMap<Medicament,Integer>() ;
	
	public RapportVisite() {
		super();
	}

	public RapportVisite(int numero, String bilan, String coefConfiance,
						 DateFr dateVisite, DateFr dateRedaction,
			boolean lu) {
		super();
		this.numero = numero;
		this.bilan = bilan;
		this.coefConfiance = coefConfiance;
		this.dateVisite = dateVisite;
		this.dateRedaction = dateRedaction;
		this.lu = lu;
	}



	public RapportVisite(int numero, String bilan, String coefConfiance, boolean lu) {
		super();
		this.numero = numero;
		this.bilan = bilan;
		this.coefConfiance = coefConfiance;
		this.lu = lu;
	}

	public RapportVisite(int numero, String bilan, String coefConfiance,
						 DateFr dateVisite, DateFr dateRedaction,
			boolean lu, Praticien lePraticien, Visiteur leVisiteur,
			Motif leMotif) {
		super();
		this.numero = numero;
		this.bilan = bilan;
		this.coefConfiance = coefConfiance;
		this.dateVisite = dateVisite;
		this.dateRedaction = dateRedaction;
		this.lu = lu;
		this.lePraticien = lePraticien;
		this.leVisiteur = leVisiteur;
		this.leMotif = leMotif;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getBilan() {
		return bilan;
	}

	public void setBilan(String bilan) {
		this.bilan = bilan;
	}

	public String getCoefConfiance() {
		return coefConfiance;
	}

	public void setCoefConfiance(String coefConfiance) {
		this.coefConfiance = coefConfiance;
	}

	public GregorianCalendar getDateVisite() {
		return dateVisite;
	}

	public void setDateVisite(DateFr dateVisite) {
		this.dateVisite = dateVisite;
	}

	public GregorianCalendar getDateRedaction() {
		return dateRedaction;
	}

	public void setDateRedaction(DateFr dateRedaction) {
		this.dateRedaction = dateRedaction;
	}

	public boolean isLu() {
		return lu ;
	}

	public void setLu(boolean lu) {
		this.lu = lu;
	}

	public Praticien getLePraticien() {
		return lePraticien;
	}

	public void setLePraticien(Praticien lePraticien) {
		this.lePraticien = lePraticien;
	}

	public Visiteur getLeVisiteur() {
		return leVisiteur;
	}

	public void setLeVisiteur(Visiteur leVisiteur) {
		this.leVisiteur = leVisiteur;
	}

	public Motif getLeMotif() {
		return leMotif;
	}

	public void setLeMotif(Motif leMotif) {
		this.leMotif = leMotif;
	}

	public Map<Medicament, Integer> getLesEchantillons() {
		return lesEchantillons;
	}

	public void setLesEchantillons(Map<Medicament, Integer> lesEchantillons) {
		this.lesEchantillons = lesEchantillons;
	}

	@Override
	public String toString() {
		return "RapportVisite [numero=" + numero + ", bilan=" + bilan
				+ ", coefConfiance=" + coefConfiance + ", dateVisite="
				+ dateVisite + ", dateRedaction=" + dateRedaction + ", lu="
				+ lu + ", lePraticien=" + lePraticien + ", leVisiteur="
				+ leVisiteur + ", leMotif=" + leMotif + ", lesEchantillons="
				+ lesEchantillons + "]";
	}
	
}
