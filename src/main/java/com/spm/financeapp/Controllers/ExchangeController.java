package com.spm.financeapp.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spm.financeapp.Models.Exchange;
import com.spm.financeapp.DTOs.ExchangeRatesResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Controller
public class ExchangeController {
    String apiURL = "https://api.freecurrencyapi.com/v1/latest?apikey=DllhFwwu1qAlOhyuP2EpcANEnekNYBhIDhjR5uRu";


    @GetMapping("/exchange")
    public String getExchange(Model model) throws IOException {
        ArrayList<Exchange> exchangeList = new ArrayList<Exchange>();
        exchangeList = getList(exchangeList,apiURL);
        model.addAttribute("exchanges", exchangeList);
        return "exchange";

    }
    @PostMapping("/exchange")
    public String postExchange(@RequestParam(value = "currency", required = false) String currency, Model model) throws IOException {
        ArrayList<String> currArr = new ArrayList<String>();
        ArrayList<Exchange> exList = new ArrayList<Exchange>();
        Collections.addAll(currArr,"USD","AUD","BGN","BRL","CAD","CHF","CNY","CZK","DKK","EUR","GBP","HKD","HRK","HUF","IDR","ILS","INR","ISK","JPY",
                "KRW","MXN","MYR","NOK","NZD","PHP","PLN","RON","RUB","SEK","SGD","THB","TRY","ZAR");

        if (currArr.contains(currency.toUpperCase())) {
            System.out.println("dogru");
            String url = apiURL + "&currencies=" + currency.toUpperCase();
            exList = getList(exList,url);
            model.addAttribute("exchanges",exList);
            return "exchange";
        }else{
            System.out.println("yanlis");

            exList = getList(exList,apiURL);
            model.addAttribute("exchanges",exList);
            return "exchange";
        }





    }
    public ArrayList<Exchange> getList(ArrayList<Exchange> arrList, String url1) throws IOException {
        URL url = new URL(url1);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        String inline = "";
        int responceCode = conn.getResponseCode();
        if(responceCode != 200){
            throw new RuntimeException("HTTP Response Code: "+responceCode);
        }
        else{

            Scanner scn = new Scanner(url.openStream());
            while (scn.hasNext()){
                inline += scn.nextLine();
            }
            scn.close();
        }
        JSONObject obj = new JSONObject(inline);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ExchangeRatesResponse exchangeRatesResponse = objectMapper.readValue(inline, ExchangeRatesResponse.class);
            exchangeRatesResponse.getData().forEach((name, value) -> {
                arrList.add(new Exchange(name, value));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrList;
    }
}