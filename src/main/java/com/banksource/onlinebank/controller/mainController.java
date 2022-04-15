//package com.banksource.onlinebank.controller;
//
//import com.banksource.onlinebank.components.*;
//import com.banksource.onlinebank.service.mainServices.*;
//import com.banksource.onlinebank.work.WorkFlow;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import javax.servlet.http.HttpSession;
//import java.sql.Date;
//import java.util.List;
//import java.util.UUID;
//
//// TODO: Переработать контроллер. Сделать так чтобы выдавал JSON.
//// TODO: Также пересмотреть контроллер на предмет разделения на несколько отдельных контроллеров
//
//@Deprecated
//@Controller
//public class mainController {
//    private boolean flag = false;
//    private RoleService roleService;
//    private com.banksource.onlinebank.service.mainServices.userService userService;
//    private WorkFlow workFlow;
//    private BankCardService bankCardService;
//    private TransactionService transactionService;
//    private CardTransactionService cardTransactionService;
//    private AccountService accountService;
//
//    @Autowired
//    public mainController( userService userService, RoleService roleService, BankCardService bankCardService, AccountService accountService, TransactionService transactionService, CardTransactionService cardTransactionService, WorkFlow workFlow) {
//        this.roleService = roleService;
//        this.userService = userService;
//        this.workFlow = workFlow;
//        this.bankCardService = bankCardService;
//        this.accountService = accountService;
//        this.transactionService = transactionService;
//        this.cardTransactionService = cardTransactionService;
//    }
//
//    @GetMapping("/home")
//    public String home(Authentication authentication, Model model){
//        String username = authentication.getName();
//        model.addAttribute("username", userService.findUser(username).getClient().getClientName());
//        List<BankCard> bankCardList = userService.findUser(username).getClient().getBankCard();
//        List<Account> accountList = userService.findUser(username).getClient().getAccount();
//        String cardListHtml = "";
//        String accountListHtml = "";
//        cardListHtml += workFlow.printCards(bankCardList);
//        accountListHtml += workFlow.printAccount(accountList);
//        model.addAttribute("cardListHtml", cardListHtml);
//        model.addAttribute("accountListHtml",accountListHtml);
//        return "home";
//    }
//
//    public boolean check(User user, String type, Long id){
//
//        if (type.equals("card")) {
//            BankCard bankCard = bankCardService.getCardById(id);
//
//            if (bankCard.getClient().getUser().getId().equals(user.getId())) {
//                return true;
//            }
//        }
//
//        if (type.equals("account")) {
//            Account account = accountService.findById(id);
//
//            if (account.getClient().getUser().getId().equals(user.getId())) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    @GetMapping("/payment")
//    public String payment(@RequestParam String type, @RequestParam(required = false) String debit_id, @RequestParam(required = false) String credit_id,
//                          Authentication authentication, Model model){
//        String username = authentication.getName();
//        User user = userService.findUser(username);
//
//        if(!check(user, type, Long.parseLong(!debit_id.isEmpty() ? debit_id : credit_id))){
//            ServletRequestAttributes attr = (ServletRequestAttributes)
//                    RequestContextHolder.currentRequestAttributes();
//            HttpSession session= attr.getRequest().getSession();
//            session.invalidate();
//            model.asMap().clear();
//            return "redirect:/logout";
//        }
//
//        Long real_debit_id = null;
//        Long real_credit_id = null;
//
//        String debit_list = "";
//        String credit_list = "";
//
//        if (type.equals("card")){
//            BankCard bankCard;
//            if(!debit_id.isEmpty())
//            {
//                bankCard = bankCardService.getCardById(Long.parseLong(debit_id));
//                real_debit_id = bankCard.getId();
//                debit_list = workFlow.dropDownListCard(userService.findUser(username).getClient().getBankCard(), real_debit_id);
//                credit_list = workFlow.dropDownListCard(userService.findUser(username).getClient().getBankCard(), (long) -1);
//            }
//            else {
//                bankCard = bankCardService.getCardById(Long.parseLong(credit_id));
//                real_credit_id = bankCard.getId();
//                debit_list = workFlow.dropDownListCard(userService.findUser(username).getClient().getBankCard(), (long) -1);
//                credit_list = workFlow.dropDownListCard(userService.findUser(username).getClient().getBankCard(), real_credit_id);
//            }
//       }
//
//        if (type.equals("account")) {
//            Account account;
//            if(!debit_id.isEmpty())
//            {
//                account = accountService.findById(Long.parseLong(debit_id));
//                real_debit_id = account.getId();
//                debit_list = workFlow.dropDownListAccount(userService.findUser(username).getClient().getAccount(), real_debit_id);
//                credit_list = workFlow.dropDownListAccount(userService.findUser(username).getClient().getAccount(), (long) -1);
//            }
//            else {
//                account = accountService.findById(Long.parseLong(credit_id));
//                real_credit_id = account.getId();
//                debit_list = workFlow.dropDownListAccount(userService.findUser(username).getClient().getAccount(), (long) -1);
//                credit_list = workFlow.dropDownListAccount(userService.findUser(username).getClient().getAccount(), real_credit_id);
//            }
//        }
//
//        model.addAttribute("debit_id", debit_list);
//        model.addAttribute("credit_id", credit_list);
//        model.addAttribute("type", "<input name=\"type\" value=\"" + type + "\">");
//
//        return "payment";
//    }
//
//    @PostMapping("/payment/execute")
//    public String payment(@RequestParam String type, @RequestParam String debit_id, @RequestParam String credit_id, @RequestParam String amount, @RequestParam String comment,
//                          Authentication authentication, Model model){
//        String username = authentication.getName();
//        User user = userService.findUser(username);
//
//        if(!check(user, type, Long.parseLong(!debit_id.isEmpty() ? debit_id : credit_id))){
//            ServletRequestAttributes attr = (ServletRequestAttributes)
//                    RequestContextHolder.currentRequestAttributes();
//            HttpSession session= attr.getRequest().getSession();
//            session.invalidate();
//            model.asMap().clear();
//            return "redirect:/logout";
//        }
//
//        java.util.Date time = new java.util.Date();
//        Date date = new Date(time.getTime());
//        String uuid = UUID.randomUUID().toString();
//        float sum = Float.parseFloat(amount);
//
//        if (type.equals("card")){
//            BankCard bankCard_debit = bankCardService.getCardById(Long.parseLong(debit_id));
//            if(bankCard_debit.getSumm() - sum < 0){
//                return "redirect:/error";
//            }
//            float percentage = bankCard_debit.getTariff().getTariffPercentage()/100;
//            CardTransaction debitCardTransaction = new CardTransaction();
//            debitCardTransaction.setBankCard(bankCard_debit);
//            debitCardTransaction.setSumm(-(sum + (sum*percentage)));
//            debitCardTransaction.setTransactionTime(date);
//            debitCardTransaction.setIsDebit(true);
//            debitCardTransaction.setTransactionGroup(uuid);
//            debitCardTransaction.setComment(comment);
//
//            BankCard bankCard_credit = bankCardService.getCardById(Long.parseLong(credit_id));
//            CardTransaction creditCardTransaction = new CardTransaction();
//            creditCardTransaction.setBankCard(bankCard_credit);
//            creditCardTransaction.setSumm(sum);
//            creditCardTransaction.setTransactionTime(date);
//            creditCardTransaction.setIsDebit(false);
//            creditCardTransaction.setTransactionGroup(uuid);
//            creditCardTransaction.setComment(comment);
//
//            cardTransactionService.addCardTransaction(debitCardTransaction, creditCardTransaction);
//
//            bankCardService.updateById(bankCard_debit.getSumm() - (sum + (sum*percentage)), bankCard_debit.getId());
//            bankCardService.updateById(bankCard_credit.getSumm() + sum, bankCard_credit.getId());
//        }
//
//        if (type.equals("account")) {
//            Account account_debit = accountService.findById(Long.parseLong(debit_id));
//            if(account_debit.getBalance() - sum < 0){
//                return "redirect:/error";
//            }
//            float percentage = account_debit.getTariff().getTariffPercentage()/100;
//            Transaction debitTransaction = new Transaction();
//            debitTransaction.setAccount(account_debit);
//            debitTransaction.setSumm(-(sum + (sum*percentage)));
//            debitTransaction.setTransactionTime(date);
//            debitTransaction.setIsDebit(true);
//            debitTransaction.setTransactionGroup(uuid);
//            debitTransaction.setComment(comment);
//
//            Account account_credit = accountService.findById(Long.parseLong(credit_id));
//            Transaction creditTransaction = new Transaction();
//            creditTransaction.setAccount(account_credit);
//            creditTransaction.setSumm(sum);
//            creditTransaction.setTransactionTime(date);
//            creditTransaction.setIsDebit(false);
//            creditTransaction.setTransactionGroup(uuid);
//            creditTransaction.setComment(comment);
//
//            transactionService.addTransaction(debitTransaction, creditTransaction);
//
//            accountService.updateById(account_debit.getBalance() - (sum + (sum*percentage)), account_debit.getId());
//            accountService.updateById(account_credit.getBalance() + sum, account_credit.getId());
//        }
//        return "redirect:/success";
//    }
//
//    @GetMapping("/abstract")
//    public String getAbstract(@RequestParam String type, @RequestParam String debit_id,
//                          Authentication authentication, Model model){
//        String username = authentication.getName();
//        User user = userService.findUser(username);
//
//        if(!check(user, type, Long.parseLong(debit_id))){
//            ServletRequestAttributes attr = (ServletRequestAttributes)
//                    RequestContextHolder.currentRequestAttributes();
//            HttpSession session= attr.getRequest().getSession();
//            session.invalidate();
//            model.asMap().clear();
//            return "redirect:/logout";
//        }
//
//        String debit_transactions_list = "";
//
//        if (type.equals("card")){
//            BankCard bankCard;
//            if(!debit_id.isEmpty())
//            {
//                bankCard = bankCardService.getCardById(Long.parseLong(debit_id));
//                debit_transactions_list = workFlow.printCardTransactions(bankCard.getCardTransactionsList());
//            }
//            else {
//                debit_transactions_list = "No transactions!";
//            }
//        }
//
//        if (type.equals("account")) {
//            Account account;
//            if(!debit_id.isEmpty())
//            {
//                account = accountService.findById(Long.parseLong(debit_id));
//                debit_transactions_list = workFlow.printAccountTransactions(account.getTransactions());
//            }
//            else {
//                debit_transactions_list = "No transactions!";
//            }
//        }
//
//        model.addAttribute("debit_transactions_list", debit_transactions_list);
//
//        return "abstract";
//    }
//    @GetMapping("/success")
//    public String success(Model model) {
//        model.addAttribute("content", "2, /home");
//        return "success";
//    }
//    @GetMapping("/error")
//    public String error() {
//        return "error";
//    }
//
//    @GetMapping("/welcome")
//    public String welcome() {
//        return "welcome";
//    }
//
//    // TODO: В конце избавиться от тестовых методов и классов в проекте
//    @GetMapping("/test")
//    public String test() {
//        return "Вход прошел успешно";
//    }
//}
//
