package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.dto.TariffOptionDto;
import com.infosystem.springmvc.dto.TariffOptionDtoShort;
import com.infosystem.springmvc.dto.editUserDto.EditAddressDto;
import com.infosystem.springmvc.dto.editUserDto.EditMailDto;
import com.infosystem.springmvc.dto.editUserDto.EditPassportDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;


@RestController
public class GlobalDataController {

    private final UserService userService;

    private final ContractService contractService;

    private final TariffService tariffService;

    @Autowired
    public GlobalDataController(UserService userService, ContractService contractService, TariffOptionService tariffOptionService, MessageSource messageSource, TariffService tariffService) {
        this.userService = userService;
        this.contractService = contractService;
        this.tariffService = tariffService;
    }

    /**
     * Returns options available for tariff.
     *
     * @param tariffId tariffId
     * @param result    validation result
     * @return message
     * @throws DatabaseException   if tariff doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/tariffOptions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Set<TariffOptionDtoShort> tariffOptions(@RequestBody @NotNull String tariffId, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong tariff ID!");
        }
        return tariffService.getAvailableOptionsForTariff(Integer.parseInt(tariffId));
    }

    // Careful. Status setters are NOT protected from "hackers" :D (you still can generate request with valid header etc
    // and do smth like unBan user if you are not admin. In application customer just don't have button to set status
    // if account/contract is banned.

    /**
     * Set new status for contract.
     *
     * @param setNewStatusDto setNewStatusDto
     * @param result          validation result
     * @return message
     * @throws DatabaseException   if contract doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/contract/setStatus", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String setContractStatus(@RequestBody @Valid SetNewStatusDto setNewStatusDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong contract id or status!");
        }
        contractService.setStatus(setNewStatusDto);
        return "Contract is now " + setNewStatusDto.getEntityStatus() + ".";
    }

    /**
     * Set new status for user.
     *
     * @param setNewStatusDto setNewStatusDto
     * @param result          validation result
     * @return message
     * @throws DatabaseException   if user doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/user/setStatus", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String setUserStatus(@RequestBody @Valid SetNewStatusDto setNewStatusDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong contract id or status!");
        }
        userService.setStatus(setNewStatusDto);
        return "User is now " + setNewStatusDto.getEntityStatus() + ".";
    }

    /**
     * Set new status for tariff.
     *
     * @param setNewStatusDto setNewStatusDto
     * @param result          validation result
     * @return message
     * @throws DatabaseException   if tariff doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/tariff/setStatus", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String setTariffStatus(@RequestBody @Valid SetNewStatusDto setNewStatusDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong contract id or status!");
        }
        tariffService.setStatus(setNewStatusDto);
        return "User is now " + setNewStatusDto.getEntityStatus() + ".";
    }

    /**
     * Modifies selected value of user.
     *
     * @param editMailDto editMailDto
     * @param result      validation result
     * @return message
     * @throws DatabaseException   if user doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/user/editUserMail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String editUserMail(@RequestBody @Valid EditMailDto editMailDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong input!");
        }
        userService.updateUserMail(editMailDto);
        return "Email modified successfully.";
    }

    /**
     * Modifies selected value of user.
     *
     * @param editPassportDto editPassportDto
     * @param result      validation result
     * @return message
     * @throws DatabaseException   if user doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/user/editUserPassport", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String editUserPassport(@RequestBody @Valid EditPassportDto editPassportDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong input!");
        }
        userService.updateUserPassport(editPassportDto);
        return "Passport modified successfully.";
    }

    /**
     * Modifies selected value of user.
     *
     * @param editAddressDto editAddressDto
     * @param result      validation result
     * @return message
     * @throws DatabaseException   if user doesn't exist
     * @throws ValidationException if input is wrong
     */
    @RequestMapping(value = "/user/editUserAddress", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String editUserAddress(@RequestBody @Valid EditAddressDto editAddressDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            throw new ValidationException("Wrong input!");
        }
        userService.updateUserAddress(editAddressDto);
        return "Address modified successfully.";
    }


//    //todo
//    @RequestMapping(value = "/prelogin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
//    public String prelogin(@RequestBody @Valid PreLoginDto editAddressDto, BindingResult result) throws DatabaseException, ValidationException {
//        if (result.hasErrors()) {
//            throw new ValidationException("Wrong input!");
//        }
//        userService.updateUserAddress(editAddressDto);
//        return "Address modified successfully.";
//    }

}
