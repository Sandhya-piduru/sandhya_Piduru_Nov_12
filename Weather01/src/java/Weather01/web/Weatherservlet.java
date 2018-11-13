/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Weather01.web;

import java.awt.PageAttributes;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import sun.reflect.annotation.AnnotationType;

@WebServlet(urlPatterns = {"/weather"})
/**
 *
 * @author issuser
 */
public class Weatherservlet extends HttpServlet {
    private static final String WEATHER_URL ="http://api.openweathermap.org/data/2.5/weather";
    private static final String APPID = "c560f8838d37c7842d5433f0a09a32ce";
   private Client client;    
//initialize the client
   
    @Override
    public void init() throws ServletException {
       client = ClientBuilder.newClient();
        }
//Clean Up
    @Override
    public void destroy() {
        client.close();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //form field name
        
        String CityName = req.getParameter("CityName");
        
        if ((CityName == null) || (CityName.trim().length() <=0)) {
            CityName = "Singapore";
        }
        
        //Create the target
        
        WebTarget target = client.target(WEATHER_URL);
        //Set the query String
        target = target.queryParam("q",CityName)
                        .queryParam("appid",APPID)
                        .queryParam("units","metric");
        //Create Invocation, expect JSON as result
        Invocation.Builder inv = target.request(MediaType.APPLICATION_JSON);
        
        //Make the call using GET HTTP method
        
        JsonObject result = inv.get(JsonObject.class);
        JsonArray weatherDetails = result.getJsonArray("weather");
        log("RESULT: " + result);
        
        //echo back the name
        resp.setStatus(HttpServletResponse.SC_OK);
        
        //Set the content type /Media Type
        resp.setContentType(MediaType.TEXT_HTML);
        
        try (PrintWriter pw =resp.getWriter()){
            pw.print("<h2> The weather for city " + CityName + "</h2>");
            for (int i = 0; i < weatherDetails.size(); i++) {
                JsonObject wd = weatherDetails.getJsonObject(i);
                String main = wd.getString("main");
                String description = wd.getString("description");
                String icon = wd.getString("icon");
                pw.print("<div>");
              
              //pw.printf("%s &dash; %s", main,description);
                  pw.print(main + " &dash; " + description);
                pw.printf("<img src=\"http://openweathermap.org/img/w/%s.png\">", icon);
                pw.print("</div>");
            }
        }
    }
        
    }
    
    
