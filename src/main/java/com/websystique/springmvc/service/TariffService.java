package com.websystique.springmvc.service;

import com.websystique.springmvc.dto.GetOptionsAsJsonDto;
import com.websystique.springmvc.dto.GetTarifAsJsonDto;
import com.websystique.springmvc.model.Tariff;
import com.websystique.springmvc.model.User;

import java.util.List;

public interface TariffService {

    Tariff findById(int id);

    void saveTariff(Tariff tariff);

    void saveTariff(GetTarifAsJsonDto tariff);

    void updateTariff(Tariff tariff);

    List<Tariff> findAllTariffs();

    void deleteTariffById(int id);

    List<Tariff> findFirstTariffs();
}
