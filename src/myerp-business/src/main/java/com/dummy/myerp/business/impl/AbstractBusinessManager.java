package com.dummy.myerp.business.impl;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;


/**
 * <p>Classe mère des Managers</p>
 */
public abstract class AbstractBusinessManager {

    /** Le Proxy d'accès à la couche Business */
    private static BusinessProxy businessProxy;
    
    /** Le Proxy d'accès à la couche Consumer-DAO */
    private static DaoProxy daoProxy;
    
    /** Le gestionnaire de Transaction */
    private static TransactionManager transactionManager;


    // ==================== Constructeurs ====================

    /**
     * Méthode de configuration de la classe
     *
     * @param pBusinessProxy      -
     * @param pDaoProxy           -
     * @param pTransactionManager -
     */
    public static void configure(BusinessProxy pBusinessProxy,
                                                       DaoProxy pDaoProxy,
                                                       TransactionManager pTransactionManager) {
        
                                                       businessProxy = pBusinessProxy;
                                                       daoProxy = pDaoProxy;
                                                       transactionManager = pTransactionManager;
              
                                                       }


    // ==================== Getters/Setters ====================

    /**
     * Renvoie le Proxy d'accès à la couche Business
     *
     * @return {@link BusinessProxy}
     */
    protected BusinessProxy getBusinessProxy() {
        return businessProxy;
    }


    


  


    /**
     * Renvoie un {@link Validator} de contraintes
     *
     * @return Validator
     */
    protected Validator getConstraintValidator() {
        
        Configuration<?> vConfiguration = Validation.byDefaultProvider().configure();
        ValidatorFactory vFactory = vConfiguration.buildValidatorFactory();
        Validator vValidator = vFactory.getValidator();
        
        
        return vValidator;
    }
    
    
    //********************************************************************************************//
    
    /**
     * Renvoie le Proxy d'accès à la couche Consumer-DAO
     *
     * @return {@link DaoProxy}
     */
    protected DaoProxy getDaoProxy() {
        return daoProxy;
    }
    
    /*
    Implementation de la méthode static setDaoProxy [ A initialiser dans la classe mère ]
    Méthode qui va nous servir pour configurer les tests dans la classe ComptamanagerImplTest
    Pour que nous puissions définir le DaoProxy
    @param daoProxy
    */
    public static void setDaoProxy(DaoProxy daoProxy) {
        
    AbstractBusinessManager.daoProxy = daoProxy;    
        
    }
    
     //********************************************************************************************//
    
    
      /**
     * Renvoie le gestionnaire de Transaction
     *
     * @return TransactionManager
     */
    protected TransactionManager getTransactionManager() {
        return transactionManager;
    }
    
    /*
    Idem pour la transactionManager [Setters à initialiser dans la classe mère des Managers]
    Définition de la transactionManager qui servira à configurer les test dans la classe ComptaManagerImplTest
    @param transactionManager
    */
    public static void setTransactionManager(TransactionManager transactionManager) {
        
    AbstractBusinessManager.transactionManager = transactionManager;
        
        
    }
    
    
}
