package com.infosystem.springmvc.util;

import com.infosystem.springmvc.converters.JavaUtilDateToStringConverter;
import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.dto.adv.AdvTariffDto;
import com.infosystem.springmvc.model.entity.*;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CustomModelMapper {

    @Autowired
    JavaUtilDateToStringConverter javaUtilDateToStringConverter;
    @Autowired
    TariffOptionService tariffOptionService;
    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        userService.setCustomModelMapper(this);
    }

    private ModelMapper modelMapper = new ModelMapper();

    {
        //todo
        //modelMapper.getConfiguration().setAmbiguityIgnored(true);
        //modelMapper.addConverter((Converter<Date, String>) date ->
                //new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date));
    }

    /**
     * @param type Dto type class
     * @param entity entity
     * @param <T> Dto type
     * @return Dto
     */
    public <T> T mapToDto(Class<T> type, Object entity) {
        return modelMapper.map(entity, type);
    }

    /**
     * @param type Entity type class
     * @param dto dto
     * @param <T> entity type
     * @return entity
     */
    public <T> T mapToEntity(Class<T> type, Object dto) {
        return modelMapper.map(dto, type);
    }

    /**
     * @param type Dto type class
     * @param list list of entities
     * @param <T> Dto type
     * @param <D> entity type
     * @return List of Dto
     */
    public <T,D> List<T> mapToList(Class<T> type, List<D> list) {
        List<T> resultList = new ArrayList<>();
        list.forEach(element -> resultList.add(mapToDto(type,element)));
        return resultList;
    }

    /**
     * @param type Dto type class
     * @param set set of entities
     * @param <T> Dto type
     * @param <D> entity type
     * @return List of Dto
     */
    public <T,D> Set<T> mapToSet(Class<T> type, Set<D> set) {
        Set<T> resultSet = new HashSet<>();
        set.forEach(element -> resultSet.add(mapToDto(type,element)));
        return resultSet;
    }

    /**
     * @param tariffOptionDtoList tariffOptionDtoList
     * @return tariffOption list(set) with ID's from tariffOptionDtoList
     */
    public Set<TariffOption> mapToTariffOptionSet(List<TariffOptionDto> tariffOptionDtoList) {
        List<Integer> optionIdList = new ArrayList<>();
        tariffOptionDtoList.forEach(tariffOptionDto -> optionIdList.add(tariffOptionDto.getId()));
        return new HashSet<>(tariffOptionService.selectListByIdList(optionIdList));
    }

    /**
     * @param addTariffDto addTariffDto
     * @return tariff with name and price from addTariffDto
     */
    public Tariff mapToTariff(AddTariffDto addTariffDto) {
        Tariff tariff = new Tariff();
        tariff.setName(addTariffDto.getTariffDto().getName());
        tariff.setPrice(addTariffDto.getTariffDto().getPrice());
        tariff.setDescription(addTariffDto.getTariffDto().getDescription());
        return tariff;
    }

    /**
     * @param addTariffOptionDto addTariffOptionDto
     * @return TariffOption
     */
    public TariffOption mapToTariffOption(AddTariffOptionDto addTariffOptionDto) {
        return modelMapper.map(addTariffOptionDto, TariffOption.class);
    }

    /**
     * @param tariffOptionList tariffOptionList
     * @return tariffOption list(set) with ID's from tariffOptionDtoList
     */
    public Set<TariffOptionDto> mapToTariffOptionDtoSet(Set<TariffOption> tariffOptionList) {
        Set<TariffOptionDto> tariffOptionDtoList = new HashSet<TariffOptionDto>();
        tariffOptionList.forEach(tariffOption -> tariffOptionDtoList.add(modelMapper.map(tariffOption, TariffOptionDto.class)));
        return tariffOptionDtoList;
    }

    public Set<TariffOptionDtoShort> mapToTariffOptionDtoShortSet(Set<TariffOption> tariffOptionList) {
        Set<TariffOptionDtoShort> tariffOptionDtoList = new HashSet<>();
        tariffOptionList.forEach(tariffOption -> tariffOptionDtoList.add(modelMapper.map(tariffOption, TariffOptionDtoShort.class)));
        return tariffOptionDtoList;
    }

    public TariffOptionDto mapToTariffOptionDto(TariffOption tariffOption) {
        return modelMapper.map(tariffOption, TariffOptionDto.class);
    }

    public List<TariffDto> mapToTariffDtoList(List<Tariff> tariffs) {
        List<TariffDto> tariffDtoList = new ArrayList<>();
        tariffs.forEach(tariff -> tariffDtoList.add(modelMapper.map(tariff, TariffDto.class)));
        return tariffDtoList;
    }

    public ContractDto mapToContractDto(Contract contract) {
        return modelMapper.map(contract, ContractDto.class);
    }

    public TariffDto mapToTariffDto(Tariff tariff) {
        return modelMapper.map(tariff, TariffDto.class);
    }

    public Status mapToStatus(String status) {
        return modelMapper.map(status, Status.class);
    }

    public List<AdvProfileDto> mapToAdvProfileDtoList(List<AdvProfile> advProfileList) {
        return mapToList(AdvProfileDto.class,advProfileList);
    }
}
