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

    @GetMapping("/forward")
    public String forward(Authentication authentication, Model model){
        if(userService.findUser(authentication.getName()).getRoleList().contains(roleService.findByRole("USER"))) {
            model.addAttribute("content", "2, /home");
        }
        if(userService.findUser(authentication.getName()).getRoleList().contains(roleService.findByRole("ADMIN"))) {
            model.addAttribute("content", "5, /admin");
        }
        if(userService.findUser(authentication.getName()).getRoleList().contains(roleService.findByRole("STAFF"))) {
            model.addAttribute("content", "5, /staff");
        }
        return "forward";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/staff")
    public String staff(Model model){
        String buff = "";
        buff += workFlow.printRecordsStaff(recordService.getAllRecords());
        model.addAttribute("buff", buff);
        return "staff";
    }

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
//        buff += workFlow.printRecords(userService.findUser(username).getRecordList());
        model.addAttribute("cardListHtml", cardListHtml);
        model.addAttribute("accountListHtml",accountListHtml);
//        model.addAttribute("currentDate", LocalDate.now().toString());
//        model.addAttribute("types", workFlow.typesOfShops(shopService.getAllShops()));
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
//
//
//    public String welcome(){
//        return "welcome";
//    }
//    @GetMapping("/errorPage")
//    public String error(){
//        return "errorPage";
//    }
//    @GetMapping("/shopErrorPage")
//    public String errorShop(){
//        return "shopErrorPage";
//    }
//
//    @GetMapping("/home/show")
//    public @ResponseBody String show(){
//        String buff = "";
//        buff += workFlow.printShopsAdmin(shopService.getAllShops());
//        buff += workFlow.printRecordsAdmin(recordService.getAllRecords());
//        return buff;
//    }
//
//    @PostMapping("/home/addShop")
//    public String add(@RequestParam String name,
//                      @RequestParam String address){
//
//        for (Shop item:shopService.getAllShops()) {
//            if(item.getName().equals(name) && item.getAddress().equals(address)){
//                return "redirect:/shopErrorPage";
//            }
//        }
//
//        Shop shop = new Shop();
//        shop.setName(name);
//        shop.setAddress(address);
//        shopService.addShop(shop);
//        return "redirect:/forward";
//    }
//    @PostMapping("/home/addRecord")
//    public String add(@RequestParam String selectedDate,
//                      @RequestParam String selectedTime, String shopName, Authentication authentication, Model model){
//        Record record = new Record();
//        Shop shop = shopService.getShopById(shopService.getBankId(shopName));
//        User user = userService.findUser(authentication.getName());
//
//        for (Record item:recordService.getAllRecords()) {
//            if(item.getDate().equals(selectedDate) && item.getTime().equals(selectedTime)){
//                return "redirect:/errorPage";
//            }
//        }
//
//        record.setDate(selectedDate);
//        record.setTime(selectedTime);
//        record.user = user;
//        record.shop = shop;
//        recordService.addRecord(record);
//        shop.setRecordList(recordService.getAllRecords());
//        user.setRecordList(recordService.getAllRecords());
//        return "redirect:/forward";
//    }
//    @GetMapping("/home/removeRecord")
//    public String removeCards(@RequestParam Long id){
//        recordService.deleteRecordById(id);
//        return "redirect:/forward";
//    }
//    @GetMapping("/home/removeShop")
//    public String removeBanks(@RequestParam Long id){
//        shopService.deleteShopById(id);
//        return "redirect:/forward";
//    }
//    @GetMapping("/home/getRecordShop")
//    public @ResponseBody String getRecordShop(@RequestParam Long id){
//        return "name:" + recordService.getShopByRecord(id).getName() + " address:" + recordService.getShopByRecord(id).getAddress() + " id:" + recordService.getShopByRecord(id).getId();
//    }
//
//    @GetMapping("/home/getShopByName")
//    public @ResponseBody
//    String getShopByName(){
//        return workFlow.printShopsAdmin(shopService.filterByName());
//    }
//    @GetMapping("/home/getShopById")
//    public @ResponseBody
//    String getShopById(){
//        return workFlow.printShopsAdmin(shopService.filterByShopId());
//    }
//    @GetMapping("/home/getShopByAddress")
//    public @ResponseBody
//    String getShopByAddress(){
//        return workFlow.printShopsAdmin(shopService.filterByAddress());
//    }
//    @GetMapping("/home/getRecordById")
//    public @ResponseBody
//    String getRecordById(){
//        return workFlow.printRecordsAdmin(recordService.filterByRecordId());
//    }
//    @GetMapping("/home/getRecordByTime")
//    public @ResponseBody
//    String getRecordByTime(){
//        return workFlow.printRecordsAdmin(recordService.filterByTime());
//    }
//    @GetMapping("/home/getRecordByDate")
//    public @ResponseBody
//    String getRecordByDate(){
//        return workFlow.printRecordsAdmin(recordService.filterByDate());
//    }

}

