package question2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Confusing_Calulator {
   public static void main(String[] args) {
        Scanner pemindai = new Scanner(System.in);
        
        int n = pemindai.nextInt();
        
        Stack<String> tumpukanAwal = new Stack<>();
        for (int i = 0; i < n; i++) {
            tumpukanAwal.push(pemindai.next());
        }

        Queue<String> antrean = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            antrean.offer(pemindai.next());
        }
        
        Stack<String> tumpukanEkspresi = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (!antrean.isEmpty()) tumpukanEkspresi.push(antrean.poll());
            if (!tumpukanAwal.isEmpty()) tumpukanEkspresi.push(tumpukanAwal.pop());
        }

        StringBuilder ekspresiMentah = new StringBuilder();
        while (!tumpukanEkspresi.isEmpty()) {
            ekspresiMentah.append(tumpukanEkspresi.pop());
        }
        
        String rumusKalkulator = ekspresiMentah.toString();
        System.out.println(hitungKiriKeKanan(rumusKalkulator));
        
        pemindai.close();
    }
    private static int hitungKiriKeKanan(String ekspresi) {
        int total = 0;
        int angkaSaatIni = 0;
        char operatorSaatIni = '+';
        boolean sedangMembacaAngka = false;

        for (int i = 0; i < ekspresi.length(); i++) {
            char karakter = ekspresi.charAt(i);
            if (Character.isDigit(karakter)) {
                angkaSaatIni = (angkaSaatIni * 10) + (karakter - '0');
                sedangMembacaAngka = true;
            } else {
                if (sedangMembacaAngka) {
                    total = hitungMatematika(total, angkaSaatIni, operatorSaatIni);
                    angkaSaatIni = 0;
                    sedangMembacaAngka = false;
                }
                operatorSaatIni = karakter;
            }
        }
    
        if (sedangMembacaAngka) {
            total = hitungMatematika(total, angkaSaatIni, operatorSaatIni);
        }
        
        return total;
    }

    private static int hitungMatematika(int angka1, int angka2, char operator) {
        switch (operator) {
            case '+': return angka1 + angka2;
            case '-': return angka1 - angka2;
            case '*': return angka1 * angka2;
            case '/': return angka1 / angka2;
            default: return angka1;
        }
    }
}