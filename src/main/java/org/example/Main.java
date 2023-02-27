package org.example;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread threadOne = new Thread(() ->  // Первый поток
        {
            for (String text : texts) {
                if (Counters.isPalindrome(text) && Counters.isSomeChar(text)) {
                    Counters.incrementCounter(texts.length);

                }
            }
        });
        threadOne.start();


        Thread threadTwo = new Thread(() -> {  // Второй поток
            for (String text : texts) {
                if (!Counters.isSomeChar(text) && Counters.isAscendingOrder(text)) {
                    Counters.incrementCounter(text.length());
                }
            }
        });
        threadTwo.start();

        Thread threadThree = new Thread(() -> {  // Третий поток
            for (String text : texts) {
                if (Counters.isSomeChar(text) && Counters.isAscendingOrder(text)) {
                    Counters.incrementCounter(texts.length);
                }
            }
        });
        threadThree.start();

        threadTwo.join();
        threadThree.join();
        threadOne.join();

        System.out.println("Красивых слов с длиной 3: " + Counters.counter3);
        System.out.println("Красивых слов с длиной 4: " + Counters.counter4);
        System.out.println("Красивых слов с длиной 5: " + Counters.counter5);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }


}