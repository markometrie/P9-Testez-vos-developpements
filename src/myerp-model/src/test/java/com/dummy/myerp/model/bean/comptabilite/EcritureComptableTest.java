package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;


public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero), vLibelle, vDebit, vCredit);
        
        return vRetour;
        
        }
    
    
    

    @Test
    public  void isEquilibree() {
        
        
        EcritureComptable vEcriture = new EcritureComptable();
        
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        
        /*
        Nous vérifions via une assertTrue si l'écriture est bien équilibrée
        */
        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());
        
        
        
        vEcriture.getListLigneEcriture().clear();
        
        vEcriture.setLibelle("L'écriture n'est pas équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        
        /*
        Vérification que l'écriture n'est pas équilibrée
        */
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
        
        }
    
    
       @Test
        public void getTotalDebit() {

        EcritureComptable vEcriture = new EcritureComptable();

        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));

        BigDecimal vRetour = BigDecimal.ZERO;

        for (LigneEcritureComptable vLigneEcritureComptable : vEcriture.getListLigneEcriture()) {
        
        if (vLigneEcritureComptable.getDebit() != null) {
                
       vRetour = vRetour.add(vLigneEcritureComptable.getDebit());
       
       
        }
        
        }
        
        Assert.assertEquals(vEcriture.getTotalDebit(), vRetour);
    
        }

        
        
         @Test
         public void getTotalCredit() {

        EcritureComptable vEcriture = new EcritureComptable();

        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", "400"));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "31"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "70"));

        BigDecimal vRetour = BigDecimal.ZERO;

        
        for (LigneEcritureComptable vLigneEcritureComptable : vEcriture.getListLigneEcriture()) {
            
        if (vLigneEcritureComptable.getCredit() != null) {
                
        vRetour = vRetour.add(vLigneEcritureComptable.getCredit());
            
        }
        
        }
        
        Assert.assertEquals(vEcriture.getTotalCredit(), vRetour);
    
         }

         }


