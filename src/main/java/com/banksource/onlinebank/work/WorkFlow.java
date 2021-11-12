package com.banksource.onlinebank.work;

import com.banksource.onlinebank.components.*;
import com.banksource.onlinebank.components.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkFlow {
    public String printShops(List<Shop> list){
        String buff = "<h style='color: white'>Барбрешопы</h><table style='color: white' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Название</th>" +
                "      <th scope='col'>Адрес</th>" +
                "      <th scope='col'>#</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        int i = 0;
        for (Shop item: list) {
            buff += "<tr><td>" + item.getName() + "</td><td>" + item.getAddress() + "</td><td><a href='/removeShop?id=" + item.getId() + "'>Delete</a></td></tr>";
            i++;
        }
        if (i == 0)
        {
            buff+= "<tr><td>Нет барбершопов</td></tr>";
        }
        i = 0;
        buff += "</tbody>" +
                "</table>";
        return buff;
    }
    public String printRecords(List<Record> list){
        int i = 0;
        String buff = "<table style='color: white; text-align: center' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Время</th>" +
                "      <th scope='col'>Дата</th>" +
                "      <th scope='col'>Салон</th>" +
                "      <th scope='col'>#</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        for (Record item: list) {
            buff += "<tr><td>" + item.getTime() + "</td><td>" + item.getDate() +"</td><td>" + item.getShop().getName() + "</td><td><a href='home/removeRecord?id=" + item.getId() + "'>Delete</a></td></tr>";
            i++;
        }
        if (i == 0)
        {
            buff+= "<tr><td>Нет</td><td>новых</td><td>записей</td><td> </td></tr>";
        }
        buff += "</tbody>" +
                "</table>";
        return buff;
    }
    public String printRecordsStaff(List<Record> list){
        int i = 0;
        String buff = "<table style='color: white; text-align: center' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Время</th>" +
                "      <th scope='col'>Дата</th>" +
                "      <th scope='col'>Салон</th>" +
                "      <th scope='col'>Адресс</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        for (Record item: list) {
            buff += "<tr><td>" + item.getTime() + "</td><td>" + item.getDate() +"</td><td>" + item.getShop().getName() + "</td><td>" + item.getShop().getAddress() + "</td></tr>";
            i++;
        }
        if (i == 0)
        {
            buff+= "<tr><td>Нет</td><td>новых</td><td>записей</td><td> </td></tr>";
        }
        buff += "</tbody>" +
                "</table>";
        return buff;
    }
    public String printShopsAdmin(List<Shop> list){
        String buff = "<h style='color: black'>Барбрешопы</h><table style='color: black' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Название</th>" +
                "      <th scope='col'>Адрес</th>" +
                "      <th scope='col'>#</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        int i = 0;
        for (Shop item: list) {
            buff += "<tr><td>" + item.getName() + "</td><td>" + item.getAddress() + "</td><td><a href='/removeShop?id=" + item.getId() + "'>Delete</a></td></tr>";
            i++;
        }
        if (i == 0)
        {
            buff+= "<tr><td>Нет барбершопов</td></tr>";
        }
        i = 0;
        buff += "</tbody>" +
                "</table>";
        return buff;
    }
    public String printRecordsAdmin(List<Record> list){
        int i = 0;
        String buff = "<table style='color: black; text-align: center' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Время</th>" +
                "      <th scope='col'>Дата</th>" +
                "      <th scope='col'>Салон</th>" +
                "      <th scope='col'>#</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        for (Record item: list) {
            buff += "<tr><td>" + item.getTime() + "</td><td>" + item.getDate() +"</td><td>" + item.getShop().getName() + "</td></tr>";
            i++;
        }
        if (i == 0)
        {
            buff+= "<tr><td>Нет</td><td>новых</td><td>записей</td><td> </td></tr>";
        }
        buff += "</tbody>" +
                "</table>";
        return buff;
    }
    public String typesOfShops(List<Shop> list){
        String buff = "";
        for (Shop item: list) {
            buff += "<option value='" + item.getName() + "'>" + item.getName() + "</option>";
        }
        return buff;
    }
    public String printCards(List<BankCard> list){
        int i = 0;
        String buff = "<table style='color: black; text-align: center' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Номер карты</th>" +
                "      <th scope='col'>Остаток</th>" +
                "      <th scope='col'>Перевести</th>" +
                "      <th scope='col'>Пополнить</th>" +
                "      <th scope='col'>Выписка</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        for (BankCard item: list) {
            buff += "<tr><td>" + item.getCode() + "</td><td>" + item.getSumm() +"</td><td><a href=\"payment.html?type=card&debit_id=" + item.getId() + "\"></a></td><td><a href=\"payment.html?type=card&credit_id=" + item.getId() + "\"></a><td><a href=\"abstract.html?type=card&id=" + item.getId() + "\"></a></td></tr>";
            i++;
        }
        if (i == 0)
        {
            return "";
        }
        buff += "</tbody>" +
                "</table>";
        return buff;
    }
    public String printAccount(List<Account> list){
        int i = 0;
        String buff = "<table style='color: black; text-align: center' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Номер счета</th>" +
                "      <th scope='col'>Остаток</th>" +
                "      <th scope='col'>Перевести</th>" +
                "      <th scope='col'>Пополнить</th>" +
                "      <th scope='col'>Выписка</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        for (Account item: list) {
            buff += "<tr><td>" + item.getAccount_number() + "</td><td>" + item.getBalance() +"</td><td><a href=\"payment.html?type=account&debit_id=" + item.getId() + "\"></a></td><td><a href=\"payment.html?type=account&credit_id=" + item.getId() + "\"></a><td><a href=\"abstract.html?type=account&id=" + item.getId() + "\"></a></td></tr>";
            i++;
        }
        if (i == 0)
        {
            return "";
        }
        buff += "</tbody>" +
                "</table>";
        return buff;
    }

    public String dropDownListCard(List<BankCard> list, Long exclude_id){
        String buff = "";

        for (BankCard item: list) {
            buff += "<option value='" + item.getId() + "' " + (exclude_id.equals(item.getId()) ? "selected" : "") + ">" + item.getCode() + "</option>";
        }

        return buff;
    }

    public String dropDownListAccount(List<Account> list, Long exclude_id){
        String buff = "";

        for (Account item: list) {
            buff += "<option value='" + item.getId() + "' " + (exclude_id.equals(item.getId()) ? "selected" : "") + ">" + item.getAccount_number() + "</option>";
        }

        return buff;
    }

    public String printAccountTransactions(List<Transaction> list){
        int i = 0;
        String buff = "<table style='color: black; text-align: center' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Номер счета</th>" +
                "      <th scope='col'>Сумма перевода</th>" +
                "      <th scope='col'>Дата</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        for (Transaction item: list) {
            buff += "<tr><td>" + item.getAccount().getAccount_number() + "</td><td>" + item.getSumm() +"</td>"+ item.getTransaction_time() +"</tr>";
            i++;
        }
        if (i == 0)
        {
            return "";
        }
        buff += "</tbody>" +
                "</table>";
        return buff;
    }
    public String printCardTransactions(List<CardTransaction> list){
        int i = 0;
        String buff = "<table style='color: black; text-align: center' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Номер карты</th>" +
                "      <th scope='col'>Сумма перевода</th>" +
                "      <th scope='col'>Дата</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        for (CardTransaction item: list) {
            buff += "<tr><td>" + item.getBankCard().getCode() + "</td><td>" + item.getSumm() +"</td>"+ item.getTransaction_time() +"</tr>";
            i++;
        }
        if (i == 0)
        {
            return "";
        }
        buff += "</tbody>" +
                "</table>";
        return buff;
    }
}
