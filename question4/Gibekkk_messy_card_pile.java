package question4;

import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Gibekkk_messy_card_pile {
    public static void main(String[] args) {
        Scanner pemindai = new Scanner(System.in);

        String barisInput = pemindai.nextLine().trim();
        String[] daftarKartu = barisInput.split("\\s+");
        Stack<String> tumpukanRaksasa = new Stack<>();
        for (int i = daftarKartu.length - 1; i >= 0; i--) {
            tumpukanRaksasa.push(daftarKartu[i]);
        }
        List<Stack<String>> daftarTumpukanBaru = new ArrayList<>();
        List<Set<String>> pelacakDuplikat = new ArrayList<>();

        while (!tumpukanRaksasa.isEmpty()) {
            String kartuSaatIni = tumpukanRaksasa.pop();
            boolean berhasilDitempatkan = false;
            for (int i = 0; i < daftarTumpukanBaru.size(); i++) {
                if (!pelacakDuplikat.get(i).contains(kartuSaatIni)) {
                    daftarTumpukanBaru.get(i).push(kartuSaatIni);
                    pelacakDuplikat.get(i).add(kartuSaatIni);
                    berhasilDitempatkan = true;
                    break;
                }
            }
            if (!berhasilDitempatkan) {
                Stack<String> tumpukanBaru = new Stack<>();
                tumpukanBaru.push(kartuSaatIni);
                daftarTumpukanBaru.add(tumpukanBaru);
                Set<String> setBaru = new HashSet<>();
                setBaru.add(kartuSaatIni);
                pelacakDuplikat.add(setBaru);
            }
        }
        System.out.println("Output:");
        for (Stack<String> tumpukan : daftarTumpukanBaru) {
            StringBuilder hasilCetak = new StringBuilder();
            for (String kartu : tumpukan) {
                hasilCetak.append(kartu).append(" ");
            }
            System.out.println(hasilCetak.toString().trim());
        }
        pemindai.close();
    }
}