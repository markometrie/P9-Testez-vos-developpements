
package com.dummy.myerp.consumer.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"/com/dummy/myerp/consumer/applicationContext.xml"})
 public class TestComptabilite {
    
    
                                            /*
                                            Implementation de la classe ComptaDao que nous allons tester
                                            */
                                            ComptabiliteDaoImpl dao = ComptabiliteDaoImpl.getInstance();
                                            
                                            /*
                                            Vérification que la classe à tester est bien récupérée donc que le contexte à bien était pris en compte
                                            */
                                            @Test
                                            public void testDaoNull() {
                                                assertNotNull(dao);
                                            }
                                            
    
                                            //************************************************************************************************************/
                                            
                                           
                                            /*
                                            Création de la méthode pour récupérer une écriture comptable par ID
                                            @throws NotFoundException
                                           */
                                            @Test
                                            public void getEcritureComptableWithIdTest() throws NotFoundException {
                         
                         
                                            EcritureComptable testEcriture;
                                            
                                            // Id correspondant à la première donnée de la base [Libelle = cartouche d'imprimante]
                                            Integer Id = -1; 
                                            
                                            //Nous allons récupérer l'écriture comptable
                                            testEcriture = dao.getEcritureComptable(Id);
                                            
                                            assertNotNull("Verification que l'ecriture n'est pas nulle", testEcriture);
                                            assertTrue("Vérification de la référence", testEcriture.getReference().equals("AC-2016/00001"));
                                            
                                            //Retourn False si la donnée contient un Libelle
                                            assertFalse("".equals(testEcriture.getLibelle()));
                                            
                                            assertTrue("Nous allons vérifier que la liste à été chargée", testEcriture.getListLigneEcriture().size() == 3);
                                            
                                            
                                            
                                            
                                            //Nous allons tester avec une écriture comptable qui n'existe pas
                                            //Exception attendue
                                           
                                            Id = 35;
                                            
                                            try {
                                                
                                            testEcriture = dao.getEcritureComptable(Id);
                                            
                                            fail("ATTENTION: Exception qui sera attendue, Car Id n°35 n'existe pas !");
                                            
                                            } catch (NotFoundException exception) {
                                                
                                            assertFalse(exception.getMessage(), testEcriture.getId().equals(35));
                                                    
                                                    
                                            
                                            }

                                            }
                                            
                                          //************************************************************************************************************/

                                            
                                            /**
	                    * Vérification de l'obtention de la liste des écritures comptables
	                    */
	                    @Test
	                     public void getListEcritureComptableTest() {
		
                                             List<EcritureComptable>liste;

		
		liste=dao.getListEcritureComptable();

		assertTrue("Test taille de la liste attendu Ecritures Comptables", liste.size()==5);
		assertTrue("Test si une ecriture test est bien présente",liste.stream().filter(o -> o.getReference().equals("BQ-2016/00005")).findFirst().isPresent());
		
		
}
                                            
                                            
                                            //************************************************************************************************************/
                                            
                                            
                                            /*
                                            Méthode de test pour permettre la vérification de la liste des comptes comptales
                                            */
                                            @Test
                                            public void getListCompteComptableTest() {
                                                
                                            List<CompteComptable> listeCompte;
                                            
                                            listeCompte = dao.getListCompteComptable();
                                            
                                            assertTrue("test la taille de la liste ", listeCompte.size() == 7);
                                                
                                            assertTrue("Test si le compte comptable fournisseur est présent en base ", listeCompte.stream().filter(o -> o.getNumero().equals(401)).findFirst().isPresent());
        
                                                
                                            }
                                           
                                            
                                            //************************************************************************************************************/
                                            
                                            /*
                                            Création de la méthode Test pour vérifier les valeurs des journaux comptables
                                           */
                                            @Test
                                            public void getListJournalComptaTest() {
                                                
                                            //Variable qui va contenir la liste des journaux
                                            List<JournalComptable> listeJournaux;
                                            
                                            listeJournaux = dao.getListJournalComptable();
                                            
                                            
                                            //Vérification de la taille de la liste des journaux |  Test si le journal existe [AC]
                                            
                                        
                                            // La taille de la liste est bien égale à 4
                                            assertTrue("Nous allons tester la liste attendu, si elle correspond bien à la taille donnée", listeJournaux.size() ==  4 );
                                            
                                            // Nom du code du journal = AC | Libelle = Achat
                                            assertTrue("Test si le journal est bien existant", listeJournaux.stream().filter(o -> o.getCode().equals("AC")).findFirst().isPresent());
        
        
                                             }
                                            
                                            
                                            //************************************************************************************************************/

                                            
                                             /*
                                            Création de la méthode test qui va servir à vérifier l'écriture comptable en fonction de la référence
                                            */
                                            @Test
                                            public void getEcritureComptableByReferenceTest() {

                                            // Varibale existante
                                            String refEffective = "BQ-2016/00003";

                                            // Variable non existante pour générée une exception
                                            String refNonEffective = "AC-2020/00001";


                                            EcritureComptable ecritureComptable;
                                            
                                            

                                            try{

                                            assertTrue("Vérification de la récupération d'une éccriture comptable", dao.getEcritureComptableByRef(refEffective).getId() == -3);
        
                                            } catch (NotFoundException  exception) {

                                            exception.printStackTrace();
 
                                            }
                                            
                                            // Bloc qui va générée l'exception           
                                            
                                            try {

                                            dao.getEcritureComptableByRef(refNonEffective);

                                            fail("ATTENTION: Exception attendue! ");

                                            } catch (NotFoundException exception) {
                                                

                                           assertThat(exception.getMessage(), is("ATTENTION: EcritureComptable non trouvée ! La référence : " + refNonEffective + "n'existe pas !!!"));
                                       
                                            }                     

                                            }
                                            
                                            
                                            /*
                                            Méthode Test qui va tester l'écriture comptable en fonction de la date enregistrée en base
                                            @throws NotFoundException
                                            */
                                            @Test
                                            public void getLastSequenceTest() throws NotFoundException {
                                                
                                                
                                            EcritureComptable ecritureComptable;
                                            
                                            //Création de la variable pour l'écriture comptable
                                            ecritureComptable = new EcritureComptable();
                                            
                                            // Nous settons l'écriture comptable au journal comptable
                                            ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
                                            
                                            // Nous définissions la date à récupérer via un calendar
                                            Calendar calendar = new GregorianCalendar(2016, 1, 1);
                                            
                                            //Nous settons l'écriture comptable à la date
                                            ecritureComptable.setDate(calendar.getTime());
                                            
                                            //Nous definissons la derniere valeur de la sequence
                                            SequenceEcritureComptable derniereSequence = dao.getLastSequence(ecritureComptable);
                                            
                                            
                                            // Test de la dernière valeur sur une séquence existante
                                            assertTrue("La dernière Séquence existe bien : ", derniereSequence.getDerniereValeur() == 40);
                                            
                                            
                                            calendar.set(2016, 1, 1);
                                            
                                            ecritureComptable.setDate(calendar.getTime());
                                            
                                            
                                            
                                            //Bloc try / catch 
                                            //pour tester sur une séquence neuve Exception générée si le Calendar ne correspond pas à la base
                                            
                                            try {
                                                
                                            derniereSequence = dao.getLastSequence(ecritureComptable);
                                            
                                            fail("ATTENTION: Exception qui sera générée");
                                            
                                            } catch (NotFoundException exception) {
                                                
                                            assertThat(exception.getMessage(), is(" ATTENTION: La séquence n'existe pas !"));
                                                    

                                            }
                                            
                                            
                                            }
                                            
                                                                                        
                                            
                                            //************************************************************************************************************/
                                            
                                            /*
                                            Création de la methode qui va tester la requête Sql InsertEcriture 
                                           */
                                            @Test
                                            public void insertSqlEcritureTest() throws FunctionalException, NotFoundException{
                                                
                                                
                                            EcritureComptable ecritureComptable;
                                            
                                            
                                            //Création d'une variable écriture comptable
                                            ecritureComptable = new EcritureComptable();
                                            
                                            //Nous allons Set écriture comptable au nom de code du journal comptable ainsi qu'a son libelle
                                            ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
                                            
                                            //Création d'une variable Calendar qui sera défini via un GregorianCalendar
                                            Calendar calendar = new GregorianCalendar(2019,1, 1);
                                            
                                            //Nous allons Set la date sur l'écriture comptable
                                            ecritureComptable.setDate(calendar.getTime());
                                            
                                            //Nous définissons la référence de l'écriture comptable
                                            ecritureComptable.setReference("AC-2019/00001");
                                            
                                            //On set le libelle
                                            ecritureComptable.setLibelle("Libelle");
                                            
                                            ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), null, new BigDecimal("35"), null));
                                            
                                            ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512), null, null, new BigDecimal("35")));

                                            //On initialise la requête dans la variable écriture comptable
                                            dao.insertEcritureComptable(ecritureComptable);
                                            
                                            //Initialisation d'une variable test
                                            EcritureComptable testEcritureComptable;
                                            
                                            //Définition de la variable test en fonction de sa référence
                                            testEcritureComptable = dao.getEcritureComptableByRef("AC-2019/00001");
                                            
                                            //Nous vérifions que la variable TestEcriture n'est pas null
                                                assertNotNull(testEcritureComptable);
                                            
                                            }
                                            
                                            
                                            //************************************************************************************************************/
                                            
                                            
                                             /*
                                            Test de la méthode update - Mise à jour de l'écriture comptable directement en base
                                            Mise à jour de la référence
                                            @throws NotFoundException
                                            */
                                            @Test
                                            public void updateSqlEcritureComptableTest() throws NotFoundException {
                                                
                                            EcritureComptable testEcritureUpdate;
                                            
                                            //Définition de la ligne que nous voulons mettre à jour
                                            testEcritureUpdate = dao.getEcritureComptable(-1);
                                            
                                            // Nous définissions la nouvelle référence
                                            testEcritureUpdate.setReference("AB-2019/00001");
                                            
                                            //Invocation de la méthode update
                                            dao.updateEcritureComptable(testEcritureUpdate);
                                            
                                            //Méthode insérer dans la méthode testEcritureToMaj
                                            EcritureComptable testEcritureToMAJ;
                                            
                                            //Définition de la nouvelle ref
                                            testEcritureToMAJ = dao.getEcritureComptableByRef("AB-2019/00001");
                                            
                                            
                                            // On vérifie si l'update à bien été fonctionnel sur la ligne que nous avons défini
                                            assertNotNull("La mise à jour à t'elle bien était effective et bien trouvée avec la nouvelle référence attribuée qui est : ", testEcritureToMAJ);
                                            
                                            
                                            }
                                            
                                            

                                            
                                            //************************************************************************************************************/
                                            
                                            
                                            /*
                                            Création de la méthode test pour supprimer une écriture comptable en base de donnée
                                            @throws NotFoundException
                                            */
                                            @Test
                                            public void deleteSqlEcritureComptableTest() throws NotFoundException {
                                            
                                            // Référence de l'écriture
                                            Integer Id = -1;
                                            
                                            EcritureComptable testEcriture;
                                            
                                            testEcriture = dao.getEcritureComptable(Id);
                                            
                                            //Invocation de la méthode delete
                                            dao.deleteEcritureComptable(Id);
                                            
                                            
                                            
                                            
                                            //Vérification que l'écriture à bien été supprimée [Exception attendu]
                                            
                                            try {
                                                
                                            testEcriture = dao.getEcritureComptable(Id);
                                            
                                            fail("ATTENTION: Exception attendue via NotFoundException");
                                            
                                            } catch (NotFoundException exception) {
                                                
                                            assertThat(exception.getMessage(), is("ATTENTION: L'écriture comptale n'a pas été trouvée ! [Normal elle vient d'être supp] Id = " + Id));
                                            
                                            }
                                            
                                           // On nettoie la liste des lignes d'écritures
                                           testEcriture.getListLigneEcriture().clear();
                                           
                                           //On charge la liste des lignes d'écritures via la méthode Load du Dao
                                           dao.loadListLigneEcriture(testEcriture);
                                           
                                           
                                           
                                            //Vérification que les lignes ont bien étaient supprimées
                                            assertTrue(testEcriture.getListLigneEcriture().isEmpty());

                                                
                                            }
                                            
                                            
                                           //************************************************************************************************************/
                                            
                                            
                                           
                                           
                                            /*
                                            Méthode qui servira à ajouter une Sequence en base
                                            @throws NotFoundException
                                            */
                                            @Test
                                            public void insertSequenceTest() throws NotFoundException {
                                                
                                            SequenceEcritureComptable newSequence = new SequenceEcritureComptable(2019, 42);
                                            
                                            dao.insertSequence(newSequence, "AC");
                                            
                                            EcritureComptable ecritureComptable;
                                            
                                            ecritureComptable = new EcritureComptable();
                                            
                                            ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
                                            
                                            Calendar calendar = new GregorianCalendar(2019,1,1);
                                            
                                            ecritureComptable.setDate(calendar.getTime());
                                            
                                            
                                            SequenceEcritureComptable derniereSequence = dao.getLastSequence(ecritureComptable);
                                            
                                            assertTrue("Nous allons vérifier que la dernière sequence insérée est bien : ", derniereSequence.getDerniereValeur()== 42);
                                            
                                            assertFalse("ATTENTION EXCEPTION ATTENDU: Nous retournons une exception si la dernière Sequence = ", derniereSequence.getAnnee() == 2016);
                                                
                                            
                                            }
                                            
                                             //************************************************************************************************************/
                                            
                                            
                                         @Test
	                  public void updateSequenceTest() throws NotFoundException {
                              
	                  EcritureComptable vEcritureComptable;
        
                                          vEcritureComptable = new EcritureComptable();
       
                                          vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        
                                          Calendar calendar = new GregorianCalendar(2016,1,1);
       
                                          vEcritureComptable.setDate(calendar.getTime());
        
                                         SequenceEcritureComptable last = dao.getLastSequence(vEcritureComptable);
        
        
                                         last.setDerniereValeur(45);
        
                                         dao.updateSequence(last, "AC");
        
                                         last= dao.getLastSequence(vEcritureComptable);
        
                                         assertTrue("verif que la séquence a été mise à jour",last.getDerniereValeur()==45);
       
                                         assertFalse(last.getAnnee()==2018);
	
                                         }
                          
                                     //************************************************************************************************************/
	
	
                                        @Test
	                 public void getListEcritureComptableByCompteTest() {
		
	                 List<LigneEcritureComptable>liste;
		
	                 liste=dao.getListLigneEcritureComptableByCompte(512);

	                  assertTrue("Test taille de la liste attendu Ligne Ecriture Comptable", liste.size()==2);
		
                                          liste.clear();
		
	                  liste=dao.getListLigneEcritureComptableByCompte(411);
		
                                         assertTrue("Test taille de la liste attendu Ligne Ecriture Comptable", liste.size()==3);
		
		
	}
	

}
        
        

    
    

