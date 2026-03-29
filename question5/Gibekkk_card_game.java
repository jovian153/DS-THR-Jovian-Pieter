package question5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Gibekkk_card_game {
    
    static class Kartu implements Comparable<Kartu> {
        int nilai;
        int kategori;
        
        public Kartu(int nilai, int kategori) {
            this.nilai = nilai;
            this.kategori = kategori;
        }
        @Override
        public int compareTo(Kartu kLain) {
            if (this.kategori != kLain.kategori) {
                return this.kategori - kLain.kategori;
            }
            return this.nilai - kLain.nilai;
        }
        
        @Override
        public String toString() {
            return nilai + "," + kategori;
        }
    }

    public static void main(String[] args) {
        Scanner pemindai = new Scanner(System.in);
        List<List<Kartu>> tanganPemain = new ArrayList<>();
        
        for (int i = 0; i < 4; i++) {
            List<Kartu> tangan = new ArrayList<>();
            if (!pemindai.hasNextLine()) return;
            String baris = pemindai.nextLine().trim();

            if (baris.isEmpty()) { 
                i--; 
                continue; 
            }
            String[] bagian = baris.split("\\s+");
            for (String b : bagian) {
                String[] nk = b.split(",");
                tangan.add(new Kartu(Integer.parseInt(nk[0]), Integer.parseInt(nk[1])));
            }
            Collections.sort(tangan);
            tanganPemain.add(tangan);
        }
        int pemainAwal = pemindai.nextInt() - 1;
        
        Stack<Kartu> tumpukanStack = new Stack<>();
        
        int pemainSaatIni = pemainAwal;
        int kategoriPutaran = -1;
        int nilaiAtas = -1;
        int pemainTerakhirJalan = -1;
        int jumlahPass = 0;
        
        int pemenangMatch = -1;

        while (true) {
            List<Kartu> tangan = tanganPemain.get(pemainSaatIni);
            
            if (kategoriPutaran == -1) {
                Kartu dimainkan = tangan.remove(0);
                tumpukanStack.push(dimainkan);
                
                kategoriPutaran = dimainkan.kategori;
                nilaiAtas = dimainkan.nilai;
                pemainTerakhirJalan = pemainSaatIni;
                jumlahPass = 0; // Reset pass count
                
                if (tangan.isEmpty()) {
                    pemenangMatch = pemainSaatIni + 1;
                    break;
                }
                
                pemainSaatIni = (pemainSaatIni + 1) % 4;
                
            } else {
                int indexBisaDimainkan = -1;
                for (int i = 0; i < tangan.size(); i++) {
                    Kartu k = tangan.get(i);
                    if (k.kategori == kategoriPutaran && k.nilai > nilaiAtas) {
                        indexBisaDimainkan = i;
                        break;
                    }
                }
                
                if (indexBisaDimainkan != -1) {
                    Kartu dimainkan = tangan.remove(indexBisaDimainkan);
                    tumpukanStack.push(dimainkan);
                    
                    nilaiAtas = dimainkan.nilai;
                    pemainTerakhirJalan = pemainSaatIni;
                    jumlahPass = 0;
                    if (tangan.isEmpty()) {
                        pemenangMatch = pemainSaatIni + 1;
                        break;
                    }
                } else {
                    jumlahPass++;
                    if (jumlahPass == 3) {
                        kategoriPutaran = -1; 
                        pemainSaatIni = pemainTerakhirJalan;
                        jumlahPass = 0;
                        continue;
                    }
                }
                
                pemainSaatIni = (pemainSaatIni + 1) % 4;
            }
        }
        System.out.println(pemenangMatch);
        while (!tumpukanStack.isEmpty()) {
            System.out.println(tumpukanStack.pop());
        }
        pemindai.close();
    }
}