package com.websystique.springmvc.controller;


import com.websystique.springmvc.dto.GetContractAsJsonDtoById;
import com.websystique.springmvc.dto.SwitchTariffDto;
import com.websystique.springmvc.service.ContractService;
import com.websystique.springmvc.service.TariffOptionService;
import com.websystique.springmvc.service.TariffService;
import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class CustomerDataController {

    @Autowired
    UserService userService;

    @Autowired
    ContractService contractService;

    @Autowired
    TariffOptionService tariffOptionService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    TariffService tariffService;

    @RequestMapping(value = "/customerPanel/contract/switchTariff", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String switchTariff(@RequestBody @Valid SwitchTariffDto switchTariffDto, BindingResult result) {
        //check if user auth is admin if unblock (protection agaisnt HACKERS!!!11 (direct requests) )
        contractService.customerSwitchTariff(switchTariffDto);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }


    @RequestMapping(value = "/customerPanel/contract/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractAddOptions(@RequestBody @Valid GetContractAsJsonDtoById getContractAsJsonDtoById, BindingResult result) {
        contractService.customerAddOptions(getContractAsJsonDtoById);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }

    @RequestMapping(value = "/customerPanel/contract/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractDelOptions(@RequestBody @Valid GetContractAsJsonDtoById getContractAsJsonDtoById, BindingResult result) {
        contractService.customerDelOptions(getContractAsJsonDtoById);
        if (result.hasErrors()) {
            return "notok";
        }
        return "ok";
    }
}