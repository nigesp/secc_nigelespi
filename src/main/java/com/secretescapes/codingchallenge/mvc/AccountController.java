package com.secretescapes.codingchallenge.mvc;

import com.secretescapes.codingchallenge.domain.Account;
import com.secretescapes.codingchallenge.mvc.form.object.AccountPayFormObject;
import com.secretescapes.codingchallenge.mvc.validate.AccountPayValidator;
import com.secretescapes.codingchallenge.service.AccountService;
import com.secretescapes.codingchallenge.service.InsufficientFundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Nigel on 2016-02-29.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountPayValidator accountPayValidator;

    @InitBinder(value = "accountPayFormObject")
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(accountPayValidator);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAccounts(Model model) {

        List<Account> accounts = accountService.findAll();
        model.addAttribute("accounts", accounts);

        return "account/list";
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public String viewAccount(@PathVariable int accountId, Model model) {

        Account account = accountService.findById(accountId);
        model.addAttribute("account", account);

        return "account/view";
    }

    @RequestMapping(value = "/{accountId}/pay", method = RequestMethod.GET)
    public String viewPayForm(@PathVariable int accountId, Model model) {

        Account account = accountService.findById(accountId);
        model.addAttribute("account", account);
        model.addAttribute("accountPayFormObject", new AccountPayFormObject(account));

        List<Account> accounts = accountService.findAll();
        accounts.remove(account);
        model.addAttribute("accounts", accounts);

        return "account/pay";
    }

    @RequestMapping(value = "/{accountId}/pay", method = RequestMethod.POST)
    public String makeTransfer(Model model, @Valid @ModelAttribute AccountPayFormObject accountPayFormObject, BindingResult result) {

        Account account = accountPayFormObject.getFromAccount();

        List<Account> accounts = accountService.findAll();
        accounts.remove(account);
        model.addAttribute("accounts", accounts);

        model.addAttribute("account", account);
        model.addAttribute("accounts", accounts);

        if(result.hasErrors()) {
            return "account/pay";
        }

        try {
            accountService.transferFunds(accountPayFormObject.getFromAccount(), accountPayFormObject.getToAccount(), new BigDecimal(accountPayFormObject.getAmount()));
        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient funds....");
            model.addAttribute("errorMessage", "You have insufficient funds to make the requested transfer.");
            return "account/pay";
        }

        return "account/pay/success";
    }

}
