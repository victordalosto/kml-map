package com.dnit.kml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnit.model.BodyKML;
import com.dnit.model.CategoryColor;
import com.dnit.model.InputKML;

public class GenerateKMLMap {
    
    private BodyKML kml;
    private Path pathCSV;
    private List<InputKML> listTrechos;
    private Map<Integer, String> Category_Body;
    
    private static Path pathDefaultKML;
    static {
        try {
            // change build\\assets to assets when building
            String path = Files.walk(Paths.get("build\\assets"))
                               .filter(p -> !Files.isDirectory(p))
                               .map(p -> p.toString().toLowerCase())
                               .filter(f -> f.endsWith("kml"))
                               .findFirst()
                               .orElse("");
            pathDefaultKML = Paths.get(path);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't find a default kml for import");
        }
    }



    public void generate(String pathCSV) {
        this.pathCSV = Paths.get(pathCSV);
        initialize();
        putInputIntoList();
        getKMLBody();
        getKMLHeader();
        getKMLFooting();
        createCategory();
        updatesKMLBody();
        saveKML();
    }




    /* Creates or clear the content of some attributes */
    private void initialize() {
        this.kml = new BodyKML();
        this.listTrechos = new ArrayList<>();
        this.Category_Body = new HashMap<>();
        System.out.println("Iniciando Import...");
    }




    /* Opens the .csv file and updates the List<InputKML> */
    private void putInputIntoList() {
        try (Scanner scanner = new Scanner(pathCSV)) {
            while(scanner.hasNext())
                listTrechos.add(new InputKML(scanner.nextLine()));
        } catch (Exception e) {
            throw new RuntimeException("Couldn't open input file");
        }
    }




    /* Opens the default SNV_20XX and obtains its CORE body */
    private void getKMLBody() {
        StringBuffer sb = new StringBuffer();
        try (Scanner scanner = new Scanner(new FileReader(pathDefaultKML.toString()))) {
            while(scanner.hasNext())
                sb.append(scanner.nextLine());
            this.kml.setBody(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error while creating kml body");
        }
    }




    /* Opens the default SNV_20XX and obtains its CORE body */
    private void getKMLHeader() {
        Pattern pattern = Pattern.compile("(.*?)</Schema>");
        Matcher matcher = pattern.matcher(kml.getBody());
        if (matcher.find()) {
            String appendableHead = matcher.group(0);
            kml.appendHeader(appendableHead);
        } else {
            throw new RuntimeException("Invalid kml header");
        }

    }




    /* Opens the default SNV_202X and obtains its CORE body */
    private void getKMLFooting() {
        kml.setFooting("</Folder></Document></kml>");
    }




    /* Creates category (int, SNV_Body) */
    private void createCategory() {
        listTrechos.forEach(t -> {
            int numCategory = t.getCategoria();
            if (!Category_Body.containsKey(numCategory)) {
                Category_Body.put(numCategory, "");
                String headerStyle = "<Style id=\"style"+numCategory+"\">" +
                                     "<LineStyle><color>" + CategoryColor.color(numCategory) +
                                     "</color><width>10</width></LineStyle>" +
                                     "</Style>";
                kml.appendHeader(headerStyle);
            }
        });
    }




    /* Updates the HashMap with the actual SNV_Body */
    private void updatesKMLBody() {
        listTrechos.forEach(t -> {
            Pattern pattern = Pattern.compile("<Placemark>[\\s]+<name>" + t.getSnv() + "(.*?)</Placemark>");
            Matcher matcher = pattern.matcher(kml.getBody());
            if (matcher.find()) {
                String appendableBody = matcher.group(0);
                Integer numCategoria = t.getCategoria();
                appendableBody = appendableBody.replaceAll("<styleUrl>#style0</styleUrl>", "<styleUrl>#style" + t.getCategoria() + "</styleUrl>");
                Category_Body.put(numCategoria, Category_Body.get(numCategoria) + appendableBody);
                System.out.println("Imported: " + t);
            } else {
                System.out.println("Error importing: " + t + " ##### ERROR #####");
            }
        });
        StringBuffer sb = new StringBuffer("<Folder><name>Trechos</name><open>1</open>");
        Category_Body.forEach((numCategory, kmlBody) -> {
            sb.append("<Folder><name>Categoria: " + numCategory + "</name><open>1</open>");
            sb.append(kmlBody);
            sb.append("</Folder>");
        });
        kml.setBody(sb.toString());
    }




    /* Saves the kml file */
    private void saveKML() {
        String nameFile = pathCSV.getFileName().toString();
        nameFile = nameFile.substring(0, nameFile.lastIndexOf('.')) + ".kml";
        Path outputPath = Paths.get(pathCSV.getParent().toString(), nameFile);
        try (PrintWriter out = new PrintWriter(outputPath.toString())) {
            out.println(kml.getHeader() + kml.getBody() + kml.getFooting());
            System.out.println("KML Generated.");
            System.out.println("File located at: " + outputPath + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}
