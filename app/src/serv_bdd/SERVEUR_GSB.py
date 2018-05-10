#!/usr/bin/python
# -*- coding: utf-8 -*-

from flask import *
import json
import mysql.connector
import requests


app = Flask( __name__ )	

connexionBD = mysql.connector.connect(
			host = 'localhost' ,
			user = 'root' ,
			password = 'azerty' ,
			database = 'Gsb'
		)
    
@app.route( '/' )
def accueil() :
	
	return "<body style='font-family: sans-serif;background-color:#524876; color:#fff'><center><div style='height:32%'></div><h1 style='font-size:100px;'>CLIENTS <span style='color:#179482'>GSB</span></h1></center></body>"


@app.route( '/Visiteurs' , methods=['GET'] )
def getVisiteur() :
	
	curseur = connexionBD.cursor()
	
	curseur.execute( 'SELECT VIS_MATRICULE, VIS_NOM, VIS_PRENOM, VIS_MDP FROM VISITEUR' )
	
	tuples = curseur.fetchall()
	
	curseur.close()
	
	clients = []
	
	for unTuple in tuples :
		
		unClient = { 'VIS_MATRICULE': unTuple[0] , 'VIS_NOM': unTuple[1] , 'VIS_PRENOM': unTuple[2] , 'VIS_MDP': unTuple[3] }
		
		clients.append( unClient )
	
	
	reponse = json.dumps( clients)
	print "VISITEURS : " + reponse
	
	return Response( reponse , status=200 , mimetype='text/html' )


@app.route( '/Visiteur/<idVisiteur>', methods=['GET'])
def getVisi( idVisiteur ) :
	pass
	
	curseur = connexionBD.cursor() 
	
	curseur.execute('SELECT VIS_MATRICULE, VIS_NOM, VIS_PRENOM FROM VISITEUR WHERE VIS_MATRICULE = %s', (idVisiteur,))
	
	tuples = curseur.fetchall()

	curseur.close()
	
	clients = []
	
	for unTuple in tuples :
		
		unClient = { 'VIS_MATRICULE': unTuple[0] , 'VIS_NOM': unTuple[1] , 'VIS_PRENOM': unTuple[2]}
		
		clients.append( unClient )
	
	
	reponse = reponse = json.dumps( clients)
	print "VISITEUR : " + reponse
	
	return Response( reponse , status=200 , mimetype='text/html' )
	


@app.route( '/connexion/<usr>.<pwd>' , methods=['GET'] )
def connexionUser( usr, pwd ) :
    pass

    curseur = connexionBD.cursor()
    curseur.execute( 'SELECT VIS_MATRICULE, VIS_NOM, VIS_PRENOM, VIS_ADRESSE, VIS_CP, VIS_VILLE, CONVERT(VIS_DATEEMBAUCHE, CHAR), VIS_MDP FROM VISITEUR WHERE VIS_MATRICULE = %s AND VIS_MDP = %s', ( usr, pwd ) )
    tuples = curseur.fetchall()
    curseur.close()
    #connexions = []
    for unTuple in tuples :
        uneConnexion = { 'mdp': unTuple[7] , 'dateembauche': unTuple[6] , 'ville': unTuple[5] , 'cp': unTuple[4] , 'adresse': unTuple[3] , 'prenom': unTuple[2] , 'nom': unTuple[1] , 'matricule': unTuple[0] }
                      
        #connexions.append( uneConnexion )
    reponse = json.dumps( uneConnexion )
    print "Visiteur : " + reponse
    return Response( reponse , status=200 , mimetype='application/json' )


@app.route( '/rapport/<mois>.<annee>.<visi>' , methods=['GET'] )
def getRapportsParVisMoisAnnee( mois, annee, visi ) :
    pass

    curseur = connexionBD.cursor()
    curseur.execute( 'SELECT r.RAP_NUM, v.VIS_MATRICULE, v.VIS_NOM, v.VIS_PRENOM, p.PRA_NUM, p.PRA_NOM, p.PRA_PRENOM, CONVERT(r.RAP_DATEREDACTION, CHAR), r.RAP_BILAN, m.MOT_LIBELLE, v.VIS_VILLE, r.ESTVU, c.COEF_LIBELLE, CONVERT(r.RAP_DATEVISITE, CHAR), m.MOT_ID, c.COEF_LIBELLE From VISITEUR v INNER JOIN RAPPORT_VISITE r ON v.VIS_MATRICULE = r.VIS_MATRICULE INNER JOIN PRATICIEN p ON r.PRA_NUM = p.PRA_NUM INNER JOIN MOTIF m ON r.MOT_NUM = m.MOT_ID inner join COEFECIANT c ON r.RAP_COEFCONFIANCE = c.COEF_ID WHERE v.VIS_MATRICULE = %s AND MONTH(r.RAP_DATEVISITE) = %s AND YEAR(r.RAP_DATEVISITE) = %s ORDER BY r.RAP_NUM' , ( visi, mois, annee ) )
    tuples = curseur.fetchall()
    curseur.close()
    rapports = []
    for unTuple in tuples :
        unRapport = { 'num_rap': unTuple[0] ,  'num_visi': unTuple[1], 'nom_visi': unTuple[2] ,  'prenom_visi': unTuple[3],  'num_pra': unTuple[4], 'nom_pra': unTuple[5] ,  'prenom_pra': unTuple[6], 'rapDate': unTuple[7] , 'rapBilan': unTuple[8] , 'rapMotif': unTuple[9] , 'rapVille': unTuple[10], 'estVu': unTuple[11], 'coef': unTuple[12], 'dateVisite': unTuple[13], 'motId': unTuple[14], 'coefLibelle': unTuple[15] }
        rapports.append( unRapport )
    reponse = json.dumps( rapports )
    print "RAPPORT: " + reponse
    return Response( reponse , status=200 , mimetype='application/json' )
    

@app.route( '/rapport/estLu/<numRapport>' , methods=['GET','PUT'] )
def estVu( numRapport ) :
	
	curseur = connexionBD.cursor()
	curseur.execute( 'UPDATE RAPPORT_VISITE SET ESTVU = 1 WHERE RAP_NUM = %s' , ( numRapport , ) )
	idNouveauLivreur = curseur.lastrowid
	nbTuplesTraites = curseur.rowcount
	connexionBD.commit()
    
	curseur.close()

	reponse = make_response( '' )
	if nbTuplesTraites == 1 :
		reponse.mimetype = 'text/plain'
		reponse.status_code = 200

	else :
		pass
		reponse.mimetype = 'text/plain'
		reponse.status_code = 404

	return reponse
	
	

@app.route( '/getCoefeciant' , methods=['GET'] )
def getCoefeciant() :
	
	curseur = connexionBD.cursor()
	
	curseur.execute( 'SELECT * FROM COEFECIANT' )
	
	tuples = curseur.fetchall()
	
	curseur.close()
	
	coefes = []
	
	for unTuple in tuples :
		
		coef = { 'id': unTuple[0] , 'libelle': unTuple[1] }
		
		coefes.append( coef )
	
	reponse = json.dumps( coefes)
	print "COEFECIANT: " + reponse
	return Response( reponse , status=200 , mimetype='application/json' )


@app.route( '/getPraticien' , methods=['GET'] )
def getPraticien() :
	
	curseur = connexionBD.cursor()
	
	curseur.execute( 'SELECT PRA_NUM, PRA_NOM, PRA_PRENOM FROM PRATICIEN ORDER BY PRA_NOM' )
	
	tuples = curseur.fetchall()
	
	curseur.close()
	
	praticiens = []
	
	for unTuple in tuples :
		
		unPra = { 'id': unTuple[0] , 'nom': unTuple[1], 'prenom': unTuple[2] }
		
		praticiens.append( unPra )
	
	reponse = json.dumps( praticiens)
	print "praticiens: " + reponse
	return Response( reponse , status=200 , mimetype='application/json' )


@app.route( '/getMotif' , methods=['GET'] )
def getMotif() :
	
	curseur = connexionBD.cursor()
	
	curseur.execute( 'SELECT * FROM MOTIF' )
	
	tuples = curseur.fetchall()
	
	curseur.close()
	
	motifs = []
	
	for unTuple in tuples :
		
		motif = { 'id': unTuple[0] , 'libelle': unTuple[1] }
		
		motifs.append( motif )
	
	reponse = json.dumps(motifs)
	print "motifs: " + reponse
	return Response( reponse , status=200 , mimetype='application/json' )
	
	
	
@app.route( '/getMedocs' , methods=['GET'] )
def getMedocs() :
	
	curseur = connexionBD.cursor()
	
	curseur.execute( 'SELECT MED_DEPOTLEGAL, MED_NOMCOMMERCIAL FROM MEDICAMENT' )
	
	tuples = curseur.fetchall()
	
	curseur.close()
	
	motifs = []
	
	for unTuple in tuples :
		
		motif = { 'depot': unTuple[0] , 'nom': unTuple[1] }
		
		motifs.append( motif )
	
	reponse = json.dumps(motifs)
	print "medocs: " + reponse
	return Response( reponse , status=200 , mimetype='application/json' )


def count() :
	
	curseur = connexionBD.cursor()
	
	curseur.execute( 'SELECT COUNT(*) FROM RAPPORT_VISITE' )
	
	tuples = curseur.fetchall()
	
	curseur.close()
	
	for unTuple in tuples :
		
		idC = unTuple[0] + 1 
		
	return idC	

@app.route( '/InsertRv' , methods=['GET', 'POST'] )
def addRv() :
	
	curseur = connexionBD.cursor()
	unRapJSON = request.form['rap']
	data = json.loads(unRapJSON)
	
	idVisi = data['idVisi']
	#rapId = data['rapId']
	parId = data['praId']
	bilan = data['bilan']
	dateRedac = data['dateRedac']
	dateVisi = data['dateVisi']
	estVu = data['estVu']
	motif = data['motif']
	rapCoef = data['rapCoef']
	
	rapId = count()
	
	curseur.execute( 'INSERT INTO RAPPORT_VISITE VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s )', (idVisi, rapId, parId, bilan, dateRedac, rapCoef, dateVisi, estVu, motif) )
	connexionBD.commit()

	curseur.close()
	
	return Response( data, status=200, mimetype='application/json' )

@app.route( '/InsertOffrir' , methods=['GET', 'POST'] )
def offrir() :
	
	uneOffreJSON = request.form['medicament0']

	data = json.loads(uneOffreJSON)
	
	taille = data['tailleTab']

	for i in range(0, taille):
		
		print request.form['medicament'+str(i)]
		uneOffreJSON = request.form['medicament'+str(i)]
		
		data = json.loads(uneOffreJSON)
		
		idVisi = data['idVisi']
		nomDepMedocs = data['nomDepotMedocs']
		
		if count() == 0 :
			numRap = 1
		else:
			numRap = count() - 1
			
		qte = data['qte']
		curseur = connexionBD.cursor()
		
		curseur.execute( 'INSERT INTO OFFRIR VALUES (%s, %s, %s, %s)', (idVisi, numRap, nomDepMedocs, qte))
		
		connexionBD.commit()

		curseur.close()
		
		
	return Response( data, status=200, mimetype='application/json' )

@app.route( '/Echantillons/<numRap>' , methods=['GET'] )

def getMedocOfert(numRap) :
	
	pass
	
	curseur = connexionBD.cursor()
	
	curseur.execute( 'SELECT m.MED_NOMCOMMERCIAL, o.QUANTITE FROM OFFRIR o INNER JOIN MEDICAMENT m ON o.MED_DEPOTLEGAL = m.MED_DEPOTLEGAL WHERE o.RAP_NUM = %s', (numRap,) )
	
	tuples = curseur.fetchall()
	
	curseur.close()
	
	medocs = []
	
	for unTuple in tuples :
		
		medoc = { 'nom': unTuple[0] , 'quantite': unTuple[1] }
		
		medocs.append( medoc )
	
	reponse = json.dumps(medocs)
	print "medocs: " + reponse
	
	return Response( reponse , status=200 , mimetype='application/json' )
	

@app.route( '/Mdp/<matVisi>.<newMdp>' , methods=['GET', 'POST'] )

def modifierMdp(matVisi, newMdp) :
	
	pass
	
	curseur = connexionBD.cursor()
	
	curseur.execute( 'UPDATE VISITEUR SET VIS_MDP = %s WHERE VIS_MATRICULE = %s', (newMdp, matVisi) )
	
	idNouveauMDP = curseur.lastrowid
	nbTuplesTraites = curseur.rowcount
	connexionBD.commit()
    
	curseur.close()

	reponse = make_response( '' )
	if nbTuplesTraites == 1 :
		reponse.mimetype = 'text/plain'
		reponse.status_code = 200

	else :
		pass
		reponse.mimetype = 'text/plain'
		reponse.status_code = 404

	return reponse
	  	
if __name__ == "__main__" :
	
	app.run( debug = True , host = '192.168.1.36' , port = 5000 )
