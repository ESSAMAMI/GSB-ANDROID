package fr.gsb.gsb_modele ;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List ;

import fr.gsb.gsb_entites.Medicament;
import fr.gsb.gsb_entites.Motif;
import fr.gsb.gsb_entites.Praticien;
import fr.gsb.gsb_entites.RapportVisite;
import fr.gsb.gsb_entites.Visiteur;



public class ModeleGsb {

	private static ModeleGsb modele = null ;
	
	private List<Visiteur> lesVisiteurs = new ArrayList<Visiteur>() ;
	private List<Medicament> lesMedicaments = new ArrayList<Medicament>() ;
	private List<Praticien> lesPraticiens = new ArrayList<Praticien>() ;
	private List<Motif> lesMotifs = new ArrayList<Motif>() ;
	private List<RapportVisite> lesRapportsVisites = new ArrayList<RapportVisite>() ;
	
	private ModeleGsb(){
		super() ;
		this.peupler() ;
	}
	
	public static ModeleGsb getInstance(){
		if( ModeleGsb.modele == null ){
			modele = new ModeleGsb() ;
		}
		return ModeleGsb.modele ;
	}
	
	private void peupler(){
		this.lesVisiteurs.add( new Visiteur("a131","azerty","Villechalane","Louis") ) ;
		this.lesVisiteurs.add( new Visiteur("b13","azerty","Bentot","Pascal") ) ;
		this.lesVisiteurs.add( new Visiteur("b16","azerty","Bioret","Luc") ) ;
		this.lesVisiteurs.add( new Visiteur("a17","azerty","Andre","David") ) ;
		
		this.lesMedicaments.add( new Medicament("3MYC7","TRIMYCINE") ) ;
		this.lesMedicaments.add( new Medicament("ADIMOL9","ADIMOL") ) ;
		this.lesMedicaments.add( new Medicament("AMOX45","AMOXAR") ) ;
		this.lesMedicaments.add( new Medicament("BACTIG10","BACTIGEL") ) ;
		this.lesMedicaments.add( new Medicament("DIMIRTAM6","DIMIRTAM") ) ;
		this.lesMedicaments.add( new Medicament("EVILR7","EVEILLOR") ) ;
		this.lesMedicaments.add( new Medicament("PHYSOI8","PHYSICOR") ) ;
		this.lesMedicaments.add( new Medicament("TXISOL22","TOUXISOL") ) ;
		
		this.lesPraticiens.add( new Praticien(1,"Notini","Alain") ) ;
		this.lesPraticiens.add( new Praticien(2,"Gosselin","Albert" ) ) ;
		this.lesPraticiens.add( new Praticien(3,"Delahaye","André" ) ) ;
		this.lesPraticiens.add( new Praticien(4,"Leroux","André" ) ) ;
		this.lesPraticiens.add( new Praticien(5,"Desmoulins","Anne" ) ) ;
		this.lesPraticiens.add( new Praticien(6,"Mouel","Anne" ) ) ;
		this.lesPraticiens.add( new Praticien(7,"Desgranges-Lentz","Antoine" ) ) ;
		
		this.lesMotifs.add( new Motif(1,"P\u00e9riodicit\u00e9") ) ;
		this.lesMotifs.add( new Motif(2,"Actualisation") ) ;
		this.lesMotifs.add( new Motif(3,"Sollicitation") ) ;
		this.lesMotifs.add( new Motif(4,"Autre") ) ;
		
		/*this.lesRapportsVisites.add( new RapportVisite(1,"RAS",2,new GregorianCalendar(2016,1,3),new GregorianCalendar(2016,1,5),true)) ;
		this.lesRapportsVisites.get(0).setLeVisiteur(this.lesVisiteurs.get(0)) ;
		this.lesRapportsVisites.get(0).setLeMotif(this.lesMotifs.get(1)) ;
		this.lesRapportsVisites.get(0).setLePraticien(this.lesPraticiens.get(0)) ;
		this.lesRapportsVisites.get(0).getLesEchantillons().put(this.lesMedicaments.get(0), new Integer(2)) ;
		this.lesRapportsVisites.get(0).getLesEchantillons().put(this.lesMedicaments.get(2), new Integer(1)) ;
		this.lesRapportsVisites.get(0).getLesEchantillons().put(this.lesMedicaments.get(3), new Integer(2)) ;
		
		this.lesRapportsVisites.add( new RapportVisite(2,"RAS",4,new GregorianCalendar(2015,4,5),new GregorianCalendar(2016,1,8),false)) ;
		this.lesRapportsVisites.get(1).setLeVisiteur(this.lesVisiteurs.get(0)) ;
		this.lesRapportsVisites.get(1).setLeMotif(this.lesMotifs.get(2)) ;
		this.lesRapportsVisites.get(1).setLePraticien(this.lesPraticiens.get(1)) ;
		this.lesRapportsVisites.get(1).getLesEchantillons().put(this.lesMedicaments.get(1), new Integer(2)) ;
		this.lesRapportsVisites.get(1).getLesEchantillons().put(this.lesMedicaments.get(4), new Integer(1)) ;
		this.lesRapportsVisites.get(1).getLesEchantillons().put(this.lesMedicaments.get(5), new Integer(2)) ;

		/*this.lesRapportsVisites.add( new RapportVisite(3,"RAS",2,new GregorianCalendar(2016,1,3),new GregorianCalendar(2016,1,5),true)) ;
		this.lesRapportsVisites.get(0).setLeVisiteur(this.lesVisiteurs.get(2)) ;
		this.lesRapportsVisites.get(0).setLeMotif(this.lesMotifs.get(1)) ;
		this.lesRapportsVisites.get(0).setLePraticien(this.lesPraticiens.get(0)) ;
		this.lesRapportsVisites.get(0).getLesEchantillons().put(this.lesMedicaments.get(0), new Integer(2)) ;
		this.lesRapportsVisites.get(0).getLesEchantillons().put(this.lesMedicaments.get(2), new Integer(1)) ;
		this.lesRapportsVisites.get(0).getLesEchantillons().put(this.lesMedicaments.get(3), new Integer(2)) ;*/

	}
	
	public Visiteur seConnecter(String matricule, String mdp){
		
		for( Visiteur unVisiteur : this.lesVisiteurs ){
			if( unVisiteur.getMatricule().equals(matricule) && unVisiteur.getMdp().equals(mdp) ){
				return unVisiteur ;
			}
		}
		
		return null ;
		
	}
	
	public Motif getMotif(int code){
		for(Motif unMotif : this.lesMotifs){
			if(unMotif.getCode() == code){
				return unMotif ;
			}
		}
		return null ;
	}
	
	public Praticien getPraticien(int numero){
		for(Praticien unPraticien : this.lesPraticiens){
			if(unPraticien.getNumero() == numero ){
				return unPraticien ;
			}
		}
		return null ;
	}
	
	public Medicament getMedicament(String depotLegal){
		for(Medicament unMedicament : this.lesMedicaments){
			if(unMedicament.getDepotLegal().equals(depotLegal)){
				return unMedicament ;
			}
		}
		return null ;
	}
	
	public List<RapportVisite> getRapportsVisites(Visiteur visiteur, int mois, int annee){
		List<RapportVisite> rapports = new ArrayList<RapportVisite>() ;
		
		for(RapportVisite unRapport : this.lesRapportsVisites){
			if(unRapport.getLeVisiteur().getMatricule().equals(visiteur.getMatricule())){
				if(unRapport.getDateVisite().get(Calendar.MONTH) == mois){
					if(unRapport.getDateVisite().get(Calendar.YEAR) == annee){
						rapports.add(unRapport) ;
					}			
				}
			}
		}
		return rapports ;
	}
}
