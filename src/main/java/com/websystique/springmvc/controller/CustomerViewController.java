package com.websystique.springmvc.controller;

import com.websystique.springmvc.model.Contract;
import com.websystique.springmvc.service.ContractService;
import com.websystique.springmvc.service.TariffOptionService;
import com.websystique.springmvc.service.TariffService;
import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class CustomerViewController {

    private String customerPath = "customer/";

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;

    @Autowired
    UserDetailsService customUserDetailsService;

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




    @RequestMapping("/customerPanel")
    public String customerPanel(ModelMap model) {
        String login =  getPrincipal();
        model.addAttribute("loggedinuser", login);
        model.addAttribute("user", userService.findByLogin(login));
        return customerPath+"customerPanel";
    }


    @RequestMapping(value = "/customerPanel/contract/{contract_id}")
    public String contract(@PathVariable(value = "contract_id") Integer contract_id, ModelMap model) {
        Contract contract = contractService.findById(contract_id);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contract", contract);
        return customerPath+"contract";
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
}
