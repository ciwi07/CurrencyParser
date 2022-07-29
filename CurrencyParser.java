package com.example.demo.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;

public class CurrencyParser {
    static final String APP_TOKEN = "28bac53d8a624cd491c9333eaf3d88bc";
    static final String SITE_URL = "https://openexchangerates.org/api/";
    public static String start() throws ParseException, IOException {
            LocalDate date = LocalDate.now();
        date = date.minusDays(1);
            System.out.println(date);
            double today = getRate(SITE_URL+"latest.json?app_id="+APP_TOKEN);
            double yesterday =  getRate(SITE_URL+"historical/"+date+".json?app_id="+APP_TOKEN);
            String gif = getGIF(today>yesterday?"rich":"broke");
// if (today>yesterday) getGIF("rich");
//else getGIF("broke");
            System.out.println(gif);
        return gif;
    }
        static double getRate(String siteUrl) throws ParseException, IOException {

            URL url = new URL(siteUrl);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(content.toString());
            JSONObject rates = (JSONObject) jsonParser.parse(jsonObject.get("rates").toString());
            System.out.println(rates.get("RUB"));

            double result = Double.parseDouble(rates.get("RUB").toString());
            return result;
        }

        static String getGIF(String q) throws IOException, ParseException {

            final String API_KEY = "cEvlKfDFIR5a2C0kJaTHxPol2Zc7zttB";
            URL url = new URL("https://api.giphy.com/v1/gifs/search?api_key="+API_KEY+"&q="+q);
            URLConnection urlConnection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer content2 = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content2.append(inputLine);
            }
            in.close();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(content2.toString());
            int randomInt = (int) Math.random()*49;
            JSONArray data = (JSONArray) jsonParser.parse(jsonObject.get("data").toString());
            JSONObject gifInfo = (JSONObject) jsonParser.parse(data.get(randomInt).toString());
            JSONObject images = (JSONObject) jsonParser.parse(gifInfo.get("images").toString());
            JSONObject original = (JSONObject) jsonParser.parse(images.get("original").toString());
          String result = original.get("url").toString();
            System.out.println(result);
            return result;

        }

    }


