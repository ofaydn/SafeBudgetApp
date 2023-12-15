package com.spm.financeapp.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spm.financeapp.Models.Exchange;
import com.spm.financeapp.DTOs.ExchangeRatesResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class ExchangeController {

    @GetMapping("/exchange")
    public String getExchange(Model model) throws IOException {
            URL url = new URL("https://api.freecurrencyapi.com/v1/latest?apikey=DllhFwwu1qAlOhyuP2EpcANEnekNYBhIDhjR5uRu");
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
        //-------
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ExchangeRatesResponse exchangeRatesResponse = objectMapper.readValue(inline, ExchangeRatesResponse.class);

            List<Exchange> exchangeList = new ArrayList<>();
            exchangeRatesResponse.getData().forEach((name, value) -> {
                exchangeList.add(new Exchange(name, value));
            });

            model.addAttribute("exchanges", exchangeList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //-----
        return "exchange";

    }
}