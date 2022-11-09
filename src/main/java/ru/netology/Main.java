package ru.netology;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File loadFile = new File("basket.txt");
        Basket shopCart = null;
        if (loadFile.exists()) {
            shopCart = Basket.loadFromTxtFile(loadFile);
        } else {
            shopCart = new Basket(new String[]{"Хлеб", "Молоко", "Сахар", "Яблоки", "Чай"}, new int[]{40, 80, 70, 110, 300});
        }
        shopCart.addToCart(0, 2);
        shopCart.addToCart(1, 2);
        shopCart.addToCart(2, 1);
        shopCart.addToCart(3, 10);
        shopCart.addToCart(4, 1);
        shopCart.saveTxt(new File("basket.txt"));
        shopCart.printCart();
    }
}