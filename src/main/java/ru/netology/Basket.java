package ru.netology;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Basket {

    protected String[] products;
    protected int[] prices;
    protected int[] cart;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        cart = new int[products.length];
    }

    private Basket(String[] products, int[] prices, int[] cart) {
        this.products = products;
        this.prices = prices;
        this.cart = cart;
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
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < getCart().length; i++) {
            if (getCart()[i] > 0) {
                System.out.println(getProducts()[i] + " " + getCart()[i] + " шт, " + getPrices()[i] + " руб/шт, " + getCart()[i] * getPrices()[i] + " рублей в сумме");
            }
        }
    }

    public void saveTxt(File textFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (int i : getCart()) {
                out.print(i + " ");
            }
            out.println(" ");
            for (String product : getProducts()) {
                out.print(product + " ");
            }
            out.println(" ");
            for (int price : getPrices()) {
                out.print(price + " ");
            }
            out.println(" ");
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(textFile))) {
            String[] products = scanner.nextLine().trim().split(" ");
            int[] prices = Arrays.stream(scanner.nextLine().trim().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int[] cart = Arrays.stream(scanner.nextLine().trim().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            return new Basket(products, prices, cart);
        }
    }
}