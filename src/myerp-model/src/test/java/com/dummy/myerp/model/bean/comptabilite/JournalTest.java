
package com.dummy.myerp.model.bean.comptabilite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;


public class JournalTest {
    
    @Test
    public void getCodeTest() {
        
        //Nous allons créer des journaux comptables pour le test
        JournalComptable banque = new JournalComptable("bq", "banque");
        JournalComptable client = new JournalComptable("ach", "client");
        JournalComptable fournisseur = new JournalComptable("vte", "fournisseur");
        
        
        //Journal que nnous allons tester
        JournalComptable journalTest = null;
        
        //Nous les ajoutons au sein d'une liste
        List<JournalComptable> testList = new ArrayList<JournalComptable>();
        
        testList.addAll(Arrays.asList(banque, client, fournisseur));
        
        /*
        Méthode de test
        */
        journalTest = JournalComptable.getByCode(testList, "bq");
        Assert.assertTrue(journalTest.toString(), "bq".equals(journalTest.getCode()) && journalTest.getLibelle().equals("banque"));

        
         journalTest = JournalComptable.getByCode(testList, "ach");
        Assert.assertTrue(journalTest.toString(), "ach".equals(journalTest.getCode()) && journalTest.getLibelle().equals("client"));

        
         journalTest = JournalComptable.getByCode(testList, "vte");
        Assert.assertTrue(journalTest.toString(), "vte".equals(journalTest.getCode()) && journalTest.getLibelle().equals("fournisseur"));

        //*******************************************************************************************************
        
        JournalComptable journal = new JournalComptable();
        journal.setCode("RTY");
        journal.setLibelle("libelle");

        Assert.assertEquals(journal.toString(), "JournalComptable" + "{code='RTY', libelle='libelle'}");
        
    }
    
    
    
    
}
