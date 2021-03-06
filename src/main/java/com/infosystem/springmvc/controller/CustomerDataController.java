package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.service.*;
import com.infosystem.springmvc.sessioncart.SessionCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CustomerDataController extends ControllerTemplate {

    private final UserService userService;
    private final ContractService contractService;
    private final SessionCart sessionCart;

    @Autowired
    public CustomerDataController(UserService userService, ContractService contractService, SessionCart sessionCart) {
        this.userService = userService;
        this.contractService = contractService;
        this.sessionCart = sessionCart;
    }

    /**
     * Changes tariff of current contract to selected.
     *
     * @param switchTariffDto switchTariffDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if tariff is not selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/customerPanel/contract/switchTariff", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String switchTariff(@RequestBody @Valid SwitchTariffDto switchTariffDto, BindingResult result) throws DatabaseException, ValidationException, LogicException {
        if (result.hasErrors()) {
            String exceptionMessage = "Select tariff.";
            throw new ValidationException(exceptionMessage);
        }
        contractService.customerSwitchTariff(switchTariffDto);
        return "Switched to tariff (id:" + switchTariffDto.getTariffId() + ").";
    }

    /**
     * Adding selected options to cart.
     *
     * @param editContractDto editContractDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/customerPanel/contract/addOptionsToCart", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractAddOptionsToCart(@RequestBody @Valid EditContractDto editContractDto, BindingResult result) throws DatabaseException, ValidationException, LogicException {
        if (result.hasErrors()) {
            String exceptionMessage = "Select options to add.";
            throw new ValidationException(exceptionMessage);
        }
        sessionCart.addCartItems(editContractDto);
        return "Options added to cart.";
    }

    /**
     * Deleting selected options from the contract.
     *
     * @param editContractDto editContractDto
     * @param result          validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/customerPanel/contract/delOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractDelOptions(@RequestBody @Valid EditContractDto editContractDto, BindingResult result) throws DatabaseException, ValidationException, LogicException {
        if (result.hasErrors()) {
            String exceptionMessage = "Select options to delete.";
            throw new ValidationException(exceptionMessage);
        }
        contractService.customerDelOptions(editContractDto);
        return "Options deleted.";
    }

    /**
     * Deleting selected options from cart.
     *
     * @param deleteFromCartDto deleteFromCartDto
     * @param result            validation result
     * @return message
     * @throws ValidationException if no options selected
     * @throws DatabaseException   if contract doesn't exist
     */
    @RequestMapping(value = "/customerPanel/contract/delOptionsFromCart", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String contractDelOptionsFromCart(@RequestBody @Valid DeleteFromCartDto deleteFromCartDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            String exceptionMessage = "Wrong delete data.";
            throw new ValidationException(exceptionMessage);
        }
        sessionCart.delCartItems(deleteFromCartDto);
        return "Options deleted from cart.";
    }

    /**
     * Adding options to contract (buying).
     *
     * @return message
     * @throws LogicException    if no options selected
     * @throws DatabaseException if contract doesn't exist
     */
    @RequestMapping(value = "/customerPanel/addOptions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String addOptions(@Valid @RequestBody AddOptionsDto addOptionsDto, BindingResult result) throws DatabaseException, LogicException, ValidationException {
        if (result.hasErrors()) {
            String exceptionMessage = "Wrong input.";
            throw new ValidationException(exceptionMessage);
        }
        contractService.customerAddOptions(addOptionsDto);
        return "Bought all successfully.";
    }

    /**
     * Adding funds to user.
     *
     * @return message
     * @throws ValidationException if amount value is null
     * @throws DatabaseException   if user doesn't exist
     */
    @RequestMapping(value = "/customerPanel/addFunds", method = RequestMethod.POST)
    public String addFunds(@RequestBody @Valid FundsDto fundsDto, BindingResult result) throws DatabaseException, ValidationException {
        if (result.hasErrors()) {
            String exceptionMessage = "Chose the amount of money you want to add (min 10).";
            throw new ValidationException(exceptionMessage);
        }
        userService.addFunds(fundsDto, getPrincipal());
        return fundsDto.getAmount() + " funds added.";
    }

    /**
     * Getting current balance value.
     *
     * @return message
     * @throws ValidationException if amount value is null
     * @throws DatabaseException   if user doesn't exist
     */
    @RequestMapping(value = "/customerPanel/getBalance/{userId}", method = RequestMethod.GET)
    public String getBalance(@PathVariable String userId) throws DatabaseException, ValidationException {
        if(!pathVariableIsANumber(userId)){
            String exceptionMessage = "Wrong path variable.";
            throw new ValidationException(exceptionMessage);
        }
        return userService.getBalance(Integer.parseInt(userId));
    }

    /**
     * Adding funds to user.
     *
     * @return message
     */
    @RequestMapping(value = "/customerPanel/getCartItemsCount", method = RequestMethod.GET)
    public String getCartItemsCount() {
        return String.valueOf(sessionCart.getCount());
    }
}
