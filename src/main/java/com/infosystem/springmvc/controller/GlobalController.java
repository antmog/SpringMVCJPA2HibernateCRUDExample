package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.TariffDto;
import com.infosystem.springmvc.service.dataservice.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@SessionAttributes({"loggedinuser"})
public class GlobalController extends ControllerTemplate {

    @Autowired
    DataService dataService;

    /**
     * Mapping to login screen.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String onLogin() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/";
        }
    }

    /**
     * Mapping to forgotPassword screen.
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String forgotPassword() {
        return "forgotPassword";
    }

    /**
     * Access denied.
     */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied() {
        return "403";
    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    /**
     * Redirects to LK(main menu of account interface) admin/customer.
     *
     * @param model model
     * @return adminPanel if current user is ADMIN, customerPanel if CUSTOMER
     */
    @RequestMapping("/lk")
    public String lk(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("loggedinuser", getPrincipal());
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        }
        if (authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/adminPanel";
        }
        return "redirect:/customerPanel";
    }

    /**
     * Index page.
     *
     * @param model model
     * @return startPage
     */
    @RequestMapping("/")
    public String startPage(ModelMap model) {
        List<TariffDto> tariffDtoList = dataService.getIndexPageData();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffDtoList", tariffDtoList);
        return "index";
    }
}
