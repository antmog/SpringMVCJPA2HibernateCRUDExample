package com.infosystem.springmvc.controller;


import com.infosystem.springmvc.dto.*;
import com.infosystem.springmvc.dto.editUserDto.EditUserDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.enums.Role;
import com.infosystem.springmvc.service.dataservice.DataService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.UserService;
import com.infosystem.springmvc.validators.EditUserValidator;
import com.infosystem.springmvc.validators.TariffOptionFormValidator;
import com.infosystem.springmvc.validators.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@SessionAttributes("roles")
public class AdminController extends ControllerTemplate {

    private static final int ITEMS_PER_PAGE = 10;
    private static final String WRONG_PATH_VARIABLE = "Wrong path variable.";
    private static final String NO_SUCH_PAGE = "No such page.";
    private final UserFormValidator userFormValidator;
    private final TariffOptionFormValidator tariffOptionFormValidator;
    private final EditUserValidator editUserValidator;
    private final UserService userService;
    private final DataService dataService;
    private final TariffOptionService tariffOptionService;

    @Autowired
    public AdminController(UserFormValidator userFormValidator, TariffOptionFormValidator tariffOptionFormValidator,
                           EditUserValidator editUserValidator, UserService userService, DataService dataService,
                           TariffOptionService tariffOptionService) {
        super("admin/");
        this.userFormValidator = userFormValidator;
        this.tariffOptionFormValidator = tariffOptionFormValidator;
        this.editUserValidator = editUserValidator;
        this.userService = userService;
        this.dataService = dataService;
        this.tariffOptionService = tariffOptionService;
    }

    /**
     * Returns view of main admin panel.
     *
     * @param model model
     * @return view of main admin panel
     */
    @RequestMapping("/adminPanel")
    public String adminPanel(ModelMap model) throws DatabaseException {
        AdminPanelDto adminPanelDto = dataService.getAdminPanelData(getPrincipal());
        model.addAttribute("adminPanelDto", adminPanelDto);
        return path + "adminPanel";
    }

    @RequestMapping("/adminPanel/advProfile/{profileId}/{tariffId}")
    public String advProfileTariff(@PathVariable String profileId, @PathVariable String tariffId, ModelMap model)
            throws ValidationException, DatabaseException {
        if (!(pathVariableIsANumber(profileId) && pathVariableIsANumber(tariffId))) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        AdvProfileTariffDto advProfileTariffDto = advProfileTariffDto =
                dataService.getAdvProfileTariffData(Integer.parseInt(profileId), Integer.parseInt(tariffId));
        model.addAttribute("advProfileTariffDto", advProfileTariffDto);
        return path + "advProfileTariff";
    }

    @RequestMapping("/adminPanel/advProfile/addTariff/{profileId}")
    public String advProfileAddTariff(@PathVariable String profileId, ModelMap model) throws ValidationException {
        if (!(pathVariableIsANumber(profileId))) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        AdvProfileAddTariffDto advProfileAddTariffDto = dataService.getAdvProfileAddTariffData();
        advProfileAddTariffDto.setAdvProfileId(Integer.parseInt(profileId));
        model.addAttribute("advProfileAddTariffDto", advProfileAddTariffDto);
        return path + "advProfileAddTariff";
    }

    /**
     * Returns view with list of all users.
     *
     * @param model model
     * @return view with list of all users
     */
    @RequestMapping("/adminPanel/allUsers/{pageNumber}")
    public String adminPanelAllUsers(@PathVariable(value = "pageNumber") String pageNumber, ModelMap model) throws ValidationException {
        if (!pathVariableIsANumber(pageNumber)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        int pageNumberInt = Integer.parseInt(pageNumber);
        AllEntitiesDto<UserDto> allUsersDto = dataService.getAllEntityPageData(UserDto.class, pageNumberInt, ITEMS_PER_PAGE);
        if (illegalPage(allUsersDto.getPageCount(), pageNumberInt, model)) {
            throw new ValidationException(NO_SUCH_PAGE);
        }
        allUsersDto.setPageNumber(pageNumberInt);
        model.addAttribute("allUsersDto", allUsersDto);
        return path + "allUsers";
    }

    /**
     * Returns view of all contracts.
     *
     * @param model model
     * @return view of all contracts
     */
    @RequestMapping("/adminPanel/allContracts/{pageNumber}")
    public String adminPanelAllContracts(@PathVariable(value = "pageNumber") String pageNumber, ModelMap model) throws ValidationException {
        if (!pathVariableIsANumber(pageNumber)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        int pageNumberInt = Integer.parseInt(pageNumber);
        AllEntitiesDto<ContractDto> allContractsDto = dataService.getAllEntityPageData(ContractDto.class, pageNumberInt, ITEMS_PER_PAGE);
        if (illegalPage(allContractsDto.getPageCount(), pageNumberInt, model)) {
            throw new ValidationException(NO_SUCH_PAGE);
        }
        allContractsDto.setPageNumber(pageNumberInt);
        model.addAttribute("allContractsDto", allContractsDto);
        return path + "allContracts";
    }

    /**
     * Returns view with all tariffs.
     *
     * @param model model
     * @return view of all tariffs
     */
    @RequestMapping("/adminPanel/allTariffs/{pageNumber}")
    public String adminPanelAllTariffs(@PathVariable(value = "pageNumber") String pageNumber, ModelMap model) throws ValidationException {
        if (!pathVariableIsANumber(pageNumber)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        int pageNumberInt = Integer.parseInt(pageNumber);
        AllEntitiesDto<TariffDto> allTariffsDto = dataService.getAllEntityPageData(TariffDto.class, pageNumberInt, ITEMS_PER_PAGE);
        if (illegalPage(allTariffsDto.getPageCount(), pageNumberInt, model)) {
            throw new ValidationException(NO_SUCH_PAGE);
        }
        allTariffsDto.setPageNumber(pageNumberInt);
        model.addAttribute("allTariffsDto", allTariffsDto);
        return path + "allTariffs";
    }

    /**
     * Returns view with all tariffOptions.
     *
     * @param model model
     * @return view of all options
     */
    @RequestMapping("/adminPanel/allOptions/{pageNumber}")
    public String adminPanelAllOptions(@PathVariable(value = "pageNumber") String pageNumber, ModelMap model) throws ValidationException {
        if (!pathVariableIsANumber(pageNumber)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        int pageNumberInt = Integer.parseInt(pageNumber);
        AllEntitiesDto<TariffOptionDtoShort> allTariffOptionsDto =
                dataService.getAllEntityPageData(TariffOptionDtoShort.class, pageNumberInt, ITEMS_PER_PAGE);
        if (illegalPage(allTariffOptionsDto.getPageCount(), pageNumberInt, model)) {
            throw new ValidationException(NO_SUCH_PAGE);
        }
        allTariffOptionsDto.setPageNumber(pageNumberInt);
        model.addAttribute("allTariffOptionsDto", allTariffOptionsDto);
        return path + "allOptions";
    }

    /**
     * Returns view with addUser submit form.
     *
     * @param model model
     * @return add user page
     */
    @RequestMapping(value = "/adminPanel/addUser", method = RequestMethod.GET)
    public String addUser(ModelMap model) {
        AddUserDto addUserDto = new AddUserDto();
        model.addAttribute("addUserDto", addUserDto);
        model.addAttribute("roles", Role.getAllRoles());
        return path + "addUser";
    }

    /**
     * Validates and saves user if data is correct
     *
     * @param addUserDto addUserDto
     * @param result     validation result
     * @param model      model
     * @return success page on success or addUser view
     */
    @RequestMapping(value = "/adminPanel/addUser", method = RequestMethod.POST)
    public String saveUser(@Valid AddUserDto addUserDto, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return path + "addUser";
        }
        userFormValidator.validate(addUserDto, result);
        if (result.hasErrors()) {
            return path + "addUser";
        }
        userService.addUser(addUserDto);
        model.addAttribute("success", "User " + addUserDto.getFirstName() + " " +
                addUserDto.getLastName() + " registered successfully");
        return path + "addSuccess";
    }

    /**
     * Returns view with addContract custom form.
     *
     * @param model model
     * @return view with addContract custom form
     */
    @RequestMapping(value = "/adminPanel/addContract", method = RequestMethod.GET)
    public String addContract(ModelMap model) {
        List<TariffDto> tariffDtoList = dataService.findAllActiveTariffs();
        model.addAttribute("tariffs", tariffDtoList);
        return path + "addContract";
    }

    /**
     * Returns view with addContract custom form with selected userId
     *
     * @param userId userId
     * @param model  model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addContractToUser/{userId}", method = RequestMethod.GET)
    public String addContractToUser(@PathVariable(value = "userId") String userId, ModelMap model) throws ValidationException {
        if (!pathVariableIsANumber(userId)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        int userIdInt = Integer.parseInt(userId);
        List<TariffDto> tariffDtoList = dataService.findAllActiveTariffs();
        model.addAttribute("userId", userIdInt);
        model.addAttribute("tariffs", tariffDtoList);
        return path + "addContract";
    }

    /**
     * Returns view with addOption custom form with selected userId.
     *
     * @param model model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addOption", method = RequestMethod.GET)
    public String addOption(ModelMap model) {
        AddTariffOptionDto addTariffOptionDto = new AddTariffOptionDto();
        model.addAttribute("addTariffOptionDto", addTariffOptionDto);
        return path + "addOption";
    }

    /**
     * Validates and saves tariffOption if data is correct.
     *
     * @param addTariffOptionDto addTariffOptionDto
     * @param result             validation result
     * @param model              model
     * @return result
     */
    @RequestMapping(value = "/adminPanel/addOption", method = RequestMethod.POST)
    public String saveOption(@Valid AddTariffOptionDto addTariffOptionDto, BindingResult result, ModelMap model) {
        tariffOptionFormValidator.validate(addTariffOptionDto, result);
        if (result.hasErrors()) {
            return path + "addOption";
        }
        tariffOptionService.addTariffOption(addTariffOptionDto);
        model.addAttribute("success", "Option " + addTariffOptionDto.getName() + " registered successfully");
        return path + "addSuccess";
    }

    /**
     * Returns view with addTariff custom form.
     *
     * @param model model
     * @return view
     */
    @RequestMapping(value = "/adminPanel/addTariff", method = RequestMethod.GET)
    public String addTariff(ModelMap model) {
        List<TariffOptionDtoShort> tariffOptionDtoShortList = dataService.findAllTariffOptions();
        model.addAttribute("options", tariffOptionDtoShortList);
        return path + "addTariff";
    }

    /**
     * Returns user page view.
     *
     * @param userId userId
     * @param model  model
     * @return error page view if user doesn't exist
     */
    @RequestMapping(value = "/adminPanel/user/{userId}")
    public String user(@PathVariable(value = "userId") String userId, ModelMap model) throws ValidationException, DatabaseException {
        if (!pathVariableIsANumber(userId)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        UserPageDto userPageDto = dataService.getUserPageDto(Integer.valueOf(userId));
        model.addAttribute("userPageDto", userPageDto);
        return path + "user";
    }

    /**
     * Returns contract page view.
     *
     * @param contractId contractId
     * @param model      model
     * @return error page view if contract doesn't exist
     */
    @RequestMapping(value = "/adminPanel/contract/{contractId}")
    public String contract(@PathVariable(value = "contractId") String contractId, ModelMap model) throws DatabaseException, ValidationException {
        if (!pathVariableIsANumber(contractId)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        int contractIdInt = Integer.parseInt(contractId);
        ContractPageDto contractPageDto = dataService.getContractPageData(contractIdInt);
        model.addAttribute("contractPageDto", contractPageDto);
        return path + "contract";
    }

    /**
     * Returns tariff page view.
     *
     * @param tariffId tariffId
     * @param model    model
     * @return error page view if tariff doesn't exist
     */
    @RequestMapping(value = "/adminPanel/tariff/{tariffId}")
    public String tariff(@PathVariable(value = "tariffId") String tariffId, ModelMap model) throws ValidationException, DatabaseException {
        if (!pathVariableIsANumber(tariffId)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        int tariffIdInt = Integer.parseInt(tariffId);
        TariffPageDto tariffPageDto = dataService.getTariffPageData(tariffIdInt);
        model.addAttribute("tariffPageDto", tariffPageDto);
        return path + "tariff";
    }

    /**
     * Returns option page view.
     *
     * @param optionId optionId
     * @param model    model
     * @return error page view if option doesn't exist
     */
    @RequestMapping(value = "/adminPanel/option/{optionId}")
    public String option(@PathVariable(value = "optionId") String optionId, ModelMap model) throws ValidationException, DatabaseException {
        if (!pathVariableIsANumber(optionId)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        int optionIdInt = Integer.parseInt(optionId);
        TariffOptionPageDto tariffOptionPageDto = dataService.getTariffOptionPageData(optionIdInt);
        model.addAttribute("tariffOptionPageDto", tariffOptionPageDto);
        return path + "option";
    }

    /**
     * Returns addFunds view.
     *
     * @return view
     */
    @RequestMapping("/adminPanel/addFunds/{userId}")
    public String addFunds(@PathVariable(value = "userId") String userId, ModelMap model) throws ValidationException, DatabaseException {
        if (!pathVariableIsANumber(userId)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        int userIdInt = Integer.parseInt(userId);
        UserFundsDto userFundsDto = dataService.getUserAddFundsData(userIdInt);
        model.addAttribute("userFundsDto", userFundsDto);
        return path + "addFunds";
    }

    /**
     * @param userId userId
     * @param model  model
     * @return edit user view
     */
    @RequestMapping("/adminPanel/editUser/{userId}")
    public String editUser(@PathVariable(value = "userId") String userId, ModelMap model) throws ValidationException, DatabaseException {
        if (!pathVariableIsANumber(userId)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        int userIdInt = Integer.parseInt(userId);
        EditUserDto editUserDto = dataService.getEditUserData(userIdInt);
        model.addAttribute("editUserDto", editUserDto);
        return path + "editUser";
    }

    /**
     * Validates and saves user data if it is correct.
     *
     * @param editUserDto editUserDto
     * @param result      validation result
     * @param model       model
     * @return result
     */
    @RequestMapping(value = "/adminPanel/editUser/{userId}", method = RequestMethod.POST)
    public String editUserSubmit(@PathVariable(value = "userId") String userId, @Valid EditUserDto editUserDto, BindingResult result, ModelMap model) throws ValidationException, DatabaseException {
        if (!pathVariableIsANumber(userId)) {
            throw new ValidationException(WRONG_PATH_VARIABLE);
        }
        if (result.hasErrors()) {
            return path + "editUser";
        }
        editUserValidator.validate(editUserDto, result);
        if (result.hasErrors()) {
            return path + "editUser";
        }
        userService.editUser(editUserDto);
        model.addAttribute("success", "User " + editUserDto.getFirstName() + " " +
                editUserDto.getLastName() + " edited successfully");
        return path + "addSuccess";
    }


    private boolean illegalPage(int pagesCount, int pageNumber, ModelMap model) {
        return pageNumber > pagesCount || pageNumber < 1;
    }
}
