package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.components.*;
import com.banksource.onlinebank.components.Record;
import com.banksource.onlinebank.service.mainServices.*;
import com.banksource.onlinebank.work.WorkFlow;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class mainController {
    private boolean flag = false;
    private ShopService shopService;
    private RecordService recordService;
    private RoleService roleService;
    private com.banksource.onlinebank.service.mainServices.userService userService;
    private WorkFlow workFlow;
    private BankCardService bankCardService;
    private TransactionService transactionService;
    private CardTransactionService cardTransactionService;
    private AccountService accountService;

    @Autowired
    public mainController(RecordService recordService, ShopService shopService, userService userService, RoleService roleService, BankCardService bankCardService, AccountService accountService, TransactionService transactionService, CardTransactionService cardTransactionService, WorkFlow workFlow) {
        this.shopService = shopService;
        this.roleService = roleService;
        this.recordService = recordService;
        this.userService = userService;
        this.workFlow = workFlow;
        this.bankCardService = bankCardService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.cardTransactionService = cardTransactionService;
    }

//    @GetMapping("/forward")
//    public String forward(Authentication authentication, Model model){
//        if(userService.findUser(authentication.getName()).getRoleList().contains(roleService.findByRole("USER"))) {
//            model.addAttribute("content", "2, /home");
//        }
//        if(userService.findUser(authentication.getName()).getRoleList().contains(roleService.findByRole("ADMIN"))) {
//            model.addAttribute("content", "5, /admin");
//        }
//        if(userService.findUser(authentication.getName()).getRoleList().contains(roleService.findByRole("STAFF"))) {
//            model.addAttribute("content", "5, /staff");
//        }
//        return "forward";
//    }
//
//    @GetMapping("/admin")
//    public String admin(){
//        return "admin";
//    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model){
        String username = authentication.getName();
        model.addAttribute("username", userService.findUser(username).getClient().getClient_name());
        List<BankCard> bankCardList = userService.findUser(username).getClient().getBankCard();
        List<Account> accountList = userService.findUser(username).getClient().getAccount();
        String cardListHtml = "";
        String accountListHtml = "";
        cardListHtml += workFlow.printCards(bankCardList);
        accountListHtml += workFlow.printAccount(accountList);
        model.addAttribute("cardListHtml", cardListHtml);
        model.addAttribute("accountListHtml",accountListHtml);
        return "home";
    }

    public boolean check(User user, String type, Long id){

        if (type.equals("card")) {
            BankCard bankCard = bankCardService.getCardById(id);

            if (bankCard.getClient().getUser().getId().equals(user.getId())) {
                return true;
            }
        }

        if (type.equals("account")) {
            Account account = accountService.findById(id);

            if (account.getClient().getUser().getId().equals(user.getId())) {
                return true;
            }
        }

        return false;
    }

    @GetMapping("/payment")
    public String payment(@RequestParam String type, @RequestParam(required = false) String debit_id, @RequestParam(required = false) String credit_id,
                          Authentication authentication, Model model){
        String username = authentication.getName();
        User user = userService.findUser(username);

        if(!check(user, type, Long.parseLong(!debit_id.isEmpty() ? debit_id : credit_id))){
            ServletRequestAttributes attr = (ServletRequestAttributes)
                    RequestContextHolder.currentRequestAttributes();
            HttpSession session= attr.getRequest().getSession();
            session.invalidate();
            model.asMap().clear();
            return "redirect:/logout";
        }

        Long real_debit_id = null;
        Long real_credit_id = null;

        String debit_list = "";
        String credit_list = "";

        if (type.equals("card")){
            BankCard bankCard;
            if(!debit_id.isEmpty())
            {
                bankCard = bankCardService.getCardById(Long.parseLong(debit_id));
                real_debit_id = bankCard.getId();
                debit_list = workFlow.dropDownListAccount(userService.findUser(username).getClient().getAccount(), real_debit_id);
            }
            else {
                bankCard = bankCardService.getCardById(Long.parseLong(credit_id));
                real_credit_id = bankCard.getId();
                credit_list = workFlow.dropDownListAccount(userService.findUser(username).getClient().getAccount(), real_credit_id);
            }
       }

        if (type.equals("account")) {
            Account account;
            if(!debit_id.isEmpty())
            {
                account = accountService.findById(Long.parseLong(debit_id));
                real_debit_id = account.getId();
                debit_list = workFlow.dropDownListCard(userService.findUser(username).getClient().getBankCard(), real_debit_id);
            }
            else {
                account = accountService.findById(Long.parseLong(credit_id));
                real_credit_id = account.getId();
                credit_list = workFlow.dropDownListCard(userService.findUser(username).getClient().getBankCard(), real_credit_id);
            }
        }

        model.addAttribute("debit_id", debit_list);
        model.addAttribute("credit_id", credit_list);
        model.addAttribute("type", "<input name=\"type\" value=\"" + type + "\">");

        return "payment";
    }

    @PostMapping("/payment/execute")
    public String payment(@RequestParam String type, @RequestParam String debit_id, @RequestParam String credit_id, @RequestParam String amount, @RequestParam String comment,
                          Authentication authentication, Model model){
        String username = authentication.getName();
        User user = userService.findUser(username);

        if(!check(user, type, Long.parseLong(!debit_id.isEmpty() ? debit_id : credit_id))){
            ServletRequestAttributes attr = (ServletRequestAttributes)
                    RequestContextHolder.currentRequestAttributes();
            HttpSession session= attr.getRequest().getSession();
            session.invalidate();
            model.asMap().clear();
            return "redirect:/logout";
        }

        java.util.Date time = new java.util.Date();
        Date date = new Date(time.getTime());
        String uuid = UUID.randomUUID().toString();
        Long sum = Long.parseLong(amount);

        if (type.equals("card")){

            CardTransaction debitCardTransaction = new CardTransaction();
            debitCardTransaction.setBankCard(bankCardService.getCardById(Long.parseLong(debit_id)));
            debitCardTransaction.setSumm(-sum);
            debitCardTransaction.setTransaction_time(date);
            debitCardTransaction.setIs_debit(true);
            debitCardTransaction.setTransaction_group(uuid);
            debitCardTransaction.setComment(comment);

            CardTransaction creditCardTransaction = new CardTransaction();
            creditCardTransaction.setBankCard(bankCardService.getCardById(Long.parseLong(credit_id)));
            creditCardTransaction.setSumm(sum);
            creditCardTransaction.setTransaction_time(date);
            creditCardTransaction.setIs_debit(false);
            creditCardTransaction.setTransaction_group(uuid);
            creditCardTransaction.setComment(comment);

            cardTransactionService.addCardTransaction(debitCardTransaction, creditCardTransaction);
        }

        if (type.equals("account")) {
            Transaction debitTransaction = new Transaction();
            debitTransaction.setAccount(accountService.findById(Long.parseLong(debit_id)));
            debitTransaction.setSumm(-sum);
            debitTransaction.setTransaction_time(date);
            debitTransaction.setIs_debit(true);
            debitTransaction.setTransaction_group(uuid);
            debitTransaction.setComment(comment);

            Transaction creditTransaction = new Transaction();
            creditTransaction.setAccount(accountService.findById(Long.parseLong(credit_id)));
            creditTransaction.setSumm(sum);
            creditTransaction.setTransaction_time(date);
            creditTransaction.setIs_debit(false);
            creditTransaction.setTransaction_group(uuid);
            creditTransaction.setComment(comment);

            transactionService.addTransaction(debitTransaction, creditTransaction);
        }
        return "redirect:/success";
    }

    @GetMapping("/abstract")
    public String getAbstract(@RequestParam String type, @RequestParam String debit_id,
                          Authentication authentication, Model model){
        String username = authentication.getName();
        User user = userService.findUser(username);

        if(!check(user, type, Long.parseLong(debit_id))){
            ServletRequestAttributes attr = (ServletRequestAttributes)
                    RequestContextHolder.currentRequestAttributes();
            HttpSession session= attr.getRequest().getSession();
            session.invalidate();
            model.asMap().clear();
            return "redirect:/logout";
        }

        String debit_transactions_list = "";

        if (type.equals("card")){
            BankCard bankCard;
            if(!debit_id.isEmpty())
            {
                bankCard = bankCardService.getCardById(Long.parseLong(debit_id));
                debit_transactions_list = workFlow.printCardTransactions(bankCard.getCardTransactionsList());
            }
            else {
                debit_transactions_list = "No transactions!";
            }
        }

        if (type.equals("account")) {
            Account account;
            if(!debit_id.isEmpty())
            {
                account = accountService.findById(Long.parseLong(debit_id));
                debit_transactions_list = workFlow.printAccountTransactions(account.getTransactions());
            }
            else {
                debit_transactions_list = "No transactions!";
            }
        }

        model.addAttribute("debit_transactions_list", debit_transactions_list);

        return "abstract";
    }
}

