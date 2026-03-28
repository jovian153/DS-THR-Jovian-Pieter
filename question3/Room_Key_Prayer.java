package question3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Room_Key_Prayer {
    static class Peminjam {
        String nama;
        int ruang;
        int prioritas;

        public Peminjam(String nama, int ruang) {
            this.nama = nama;
            this.ruang = ruang;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] kunciTersedia = new int[n];

        for (int i = 0; i < n; i++) {
            kunciTersedia[i] = sc.nextInt();
        }
        Queue<Peminjam> antrean = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            String nama = sc.next();
            int ruang = sc.nextInt();
            antrean.offer(new Peminjam(nama, ruang));
        }
        for (Peminjam p : antrean) {
            p.prioritas = sc.nextInt();
        }

        List<Integer> urutanRuangan = new ArrayList<>();
        Map<Integer, List<Peminjam>> petaRuangan = new HashMap<>();

        while (!antrean.isEmpty()) {
            Peminjam p = antrean.poll();
            
            if (!petaRuangan.containsKey(p.ruang)) {
                petaRuangan.put(p.ruang, new ArrayList<>());
                urutanRuangan.add(p.ruang);
            }
            petaRuangan.get(p.ruang).add(p);
        }
        for (List<Peminjam> daftar : petaRuangan.values()) {
            daftar.sort((p1, p2) -> Integer.compare(p1.prioritas, p2.prioritas));
        }
        Stack<String> tumpukanSistem = new Stack<>();
        for (int i = urutanRuangan.size() - 1; i >= 0; i--) {
            int ruang = urutanRuangan.get(i);
            List<Peminjam> daftar = petaRuangan.get(ruang);
            for (int j = daftar.size() - 1; j >= 0; j--) {
                Peminjam p = daftar.get(j);
                tumpukanSistem.push(p.nama + " | " + p.ruang);
            }
        }
        while (!tumpukanSistem.isEmpty()) {
            System.out.println(tumpukanSistem.pop());
        }
        sc.close();
    }
}