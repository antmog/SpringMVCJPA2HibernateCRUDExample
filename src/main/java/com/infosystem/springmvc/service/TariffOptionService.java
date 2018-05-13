package com.infosystem.springmvc.service;

import com.infosystem.springmvc.dto.AddTariffOptionDto;
import com.infosystem.springmvc.dto.TariffOptionDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.model.enums.TariffOptionRule;

import java.util.List;
import java.util.Set;

public interface TariffOptionService {

    TariffOption findById(int id) throws DatabaseException;

    TariffOption findByName(String name);

    void saveTariffOption(TariffOption tariffOption);

    void updateTariffOption(TariffOption tariffOption) throws DatabaseException;

    List<TariffOption> findAllTariffOptions();

    Set<TariffOption> selectListByIdList(List<Integer> optionIdList);

    void deleteTariffOptionById(int id) throws DatabaseException, LogicException;

    List<TariffOption> findFirstTariffOptions();

    void addTariffOption(AddTariffOptionDto addTariffOptionDto);

    boolean isTariffOptionUnique(String tariffOptionName);

    void addRuleTariffOptions(Integer tariffOptionId, List<TariffOptionDto> tariffOptionDtoList, TariffOptionRule rule) throws DatabaseException, LogicException;

    void delRuleTariffOptions(Integer tariffOptionId, List<TariffOptionDto> tariffOptionDtoList, TariffOptionRule rule) throws LogicException, DatabaseException;
}
