package jsh.project.board.account.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.CreateAccountDto;
import jsh.project.board.account.service.AccountService;
import oracle.jdbc.oracore.PickleOutputStream;

@Controller
public class AccountController {
	
	private AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	//예제 코드
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false) String error, 
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessge = null;
        
        if(error != null) {
        	System.out.println("Controller : " +error);
            errorMessge = "Username or Password is incorrect !!";
        }
        if(logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login";
    }
  
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
    
    @GetMapping("/account/join")
    public String joinPage() {
    	return "join";
    }
    
    @PostMapping("/account/join")
    public String join(CreateAccountDto dto) {
    	accountService.register(dto);
    	return "login";
    }
    
    @GetMapping("/account/info")
    public String info(Principal principal, Authentication auth) {
    	System.out.println(principal.getName());//null을 유발할수 있다.
    	//이렇게도 인증한 정보를 얻어올 수 있다.
    	//Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return "login";
    }

	

}
