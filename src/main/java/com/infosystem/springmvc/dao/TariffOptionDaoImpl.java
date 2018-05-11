package com.infosystem.springmvc.dao;


import com.infosystem.springmvc.model.TariffOption;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("TariffOptionDao")
public class TariffOptionDaoImpl extends AbstractDao<Integer, TariffOption> implements TariffOptionDao {

    public TariffOption findById(int id) {
        TariffOption tariffOption = getByKey(id);
        if(tariffOption!=null){
            Hibernate.initialize(tariffOption);
        }
        return tariffOption;
    }

    @SuppressWarnings("unchecked")
    public List<TariffOption> findAllTariffOptions() {
        List<TariffOption> tariffOptions = getSession()
                .createQuery("SELECT t FROM TariffOption t")
                .getResultList();
        return tariffOptions;
    }

    @SuppressWarnings("unchecked")
    public Set<TariffOption> selectListByIdList(List<Integer> optionIdList) {
        List<TariffOption> tariffOptions = getSession()
                .createQuery("SELECT t FROM TariffOption t WHERE t.id IN (:idList)")
                .setParameter("idList",optionIdList)
                .getResultList();
        System.out.println(tariffOptions);
        return new HashSet<TariffOption>(tariffOptions) ;
    }

    public void save(TariffOption tariffOption) {
        persist(tariffOption);
    }

    public void deleteById(int id) {
        TariffOption tariffOption = (TariffOption) getSession()
                .createQuery("SELECT t FROM TariffOption t WHERE t.id LIKE :Id")
                .setParameter("Id", id)
                .getSingleResult();
        delete(tariffOption);
    }



}