package com.example.scrapper.web_scrapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
public class Controller {
    @PostMapping("/download")
    public String download(@RequestParam("url") String url){
        try{
            URL site = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) site.openConnection();
            connection.setRequestMethod("GET");
            InputStream input = connection.getInputStream();
            String page = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8)).lines()
                    .reduce("", (acc, x) -> acc + x + "\n");
            String home = System.getProperty("user.home");
            File file = new File(home + "/Downloads/" + site.getHost() + ".txt");
            FileOutputStream output = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(output, StandardCharsets.UTF_8);
            writer.write(page);
            writer.close();

            return "Guardado en carpeta descargas";
        } catch (IOException e){
            e.printStackTrace();
            return "Error al descargar";
        }

    }
}
