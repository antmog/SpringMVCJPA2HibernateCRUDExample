package com.infosystem.springmvc.service;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.TariffOption;

import java.util.List;
import java.util.Set;

public interface TariffOptionService {

    TariffOption findById(int id) throws DatabaseException;

    void saveTariffOption(TariffOption tariffOption);

    void updateTariffOption(TariffOption tariffOption) throws DatabaseException;

    List<TariffOption> findAllTariffOptions();

    Set<TariffOption> selectListByIdList(List<Integer> optionIdList);

    void deleteTariffOptionById(int id) throws DatabaseException, LogicException;

    List<TariffOption> findFirstTariffOptions();

}
