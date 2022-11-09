package ru.netology;

import com.google.gson.Gson;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Basket {

    private String[] products;
    private int[] prices;
    private int[] cart;
    ClientLog clientLog;

    private static List<String> settingsFromXml;
    private static boolean loadFromFile;
    private static String loadFileName;
    private static String typeLoadFile;
    private static boolean saveForFile;
    private static String saveFileName;
    private static String typeSaveFile;
    private static boolean iLog;
    private static String logFileName;

    public ClientLog getClientLog() {
        return clientLog;
    }

    public static boolean isLoadFromFile() {
        return loadFromFile;
    }

    public static String getLoadFileName() {
        return loadFileName;
    }

    public static String getTypeLoadFile() {
        return typeLoadFile;
    }

    public static boolean isSaveForFile() {
        return saveForFile;
    }

    public static String getSaveFileName() {
        return saveFileName;
    }

    public static String getTypeSaveFile() {
        return typeSaveFile;
    }

    public static boolean isiLog() {
        return  iLog;
    }

    public static String getLogFileName() {
        return logFileName;
    }

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        cart = new int[products.length];
        clientLog = new ClientLog();
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }

    public int[] getCart() {
        return cart;
    }

    public void addToCart(int productNum, int amount) {
        cart[productNum] += amount;
        this.clientLog.log(productNum, amount);
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < getCart().length; i++) {
            if (getCart()[i] > 0) {
                System.out.println(getProducts()[i] + " " + getCart()[i] + " шт, " + getPrices()[i] + " руб/шт, " + getCart()[i] * getPrices()[i] + " рублей в сумме");
            }
        }
    }

    public void saveJson(File jsonFile) throws IOException {
        try (PrintWriter out = new PrintWriter(jsonFile)) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            out.println(json);
        }
    }

    static Basket loadFromJsonFile(File jsonFile) throws IOException {
        try (Scanner scanner = new Scanner(jsonFile)) {
            String json = scanner.nextLine();
            Gson gson = new Gson();
            return gson.fromJson(json, Basket.class);
        }
    }

    public static void loadSettings() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("shop.xml"));
            Node root = doc.getDocumentElement();
            settingsFromXml = new ArrayList<>();
            read(root);
        } catch (Exception e) {
            System.out.println("Произошла ошибка загрузки информации из файла");
        }

        loadFromFile = Boolean.parseBoolean(settingsFromXml.get(0));
        loadFileName = settingsFromXml.get(1);
        typeLoadFile = settingsFromXml.get(2);

        saveForFile = Boolean.parseBoolean(settingsFromXml.get(3));
        saveFileName = settingsFromXml.get(4);
        typeSaveFile = settingsFromXml.get(5);

        iLog = Boolean.parseBoolean(settingsFromXml.get(6));
        logFileName = settingsFromXml.get(7);
    }

    public static void read(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (Node.ELEMENT_NODE == currentNode.getNodeType()) {
                if (!currentNode.getNodeName().equals("load") && !currentNode.getNodeName().equals("save") && !currentNode.getNodeName().equals("log")) {
                    settingsFromXml.add(currentNode.getTextContent());
                }
                read(currentNode);
            }
        }
    }
}