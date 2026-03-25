import java.util.*;

public class JembatanTerlarang {
    
    static class Auror {
        int id;
        int waktu;
        
        public Auror(int id, int waktu) {
            this.id = id;
            this.waktu = waktu;
        }
    }

    public static void main(String[] args) {
        Scanner pemindai = new Scanner(System.in);
        
        int jumlahAuror = pemindai.nextInt();
        int waktuMaksimal = pemindai.nextInt();
        
        Auror[] daftarAuror = new Auror[jumlahAuror];
        for (int i = 0; i < jumlahAuror; i++) {
            daftarAuror[i] = new Auror(i + 1, pemindai.nextInt());
        }
        
        Arrays.sort(daftarAuror, (a, b) -> Integer.compare(a.waktu, b.waktu));
        
        Queue<Auror> antreanCepat = new LinkedList<>();
        Stack<Auror> tumpukanLambat = new Stack<>();
        
        if (jumlahAuror >= 1) antreanCepat.offer(daftarAuror[0]);
        if (jumlahAuror >= 2) antreanCepat.offer(daftarAuror[1]);
        
        for (int i = 2; i < jumlahAuror; i++) {
            tumpukanLambat.push(daftarAuror[i]);
        }
        
        int waktuBerjalan = 0;
        boolean tertangkap = false;
        
        while (tumpukanLambat.size() >= 2) {
            Auror lambat1 = tumpukanLambat.pop(); 
            Auror lambat2 = tumpukanLambat.pop(); 
            Auror cepat1 = antreanCepat.poll();
            Auror cepat2 = antreanCepat.poll();
            
            int biayaStrategi1 = cepat1.waktu + (2 * cepat2.waktu) + lambat1.waktu;
            int biayaStrategi2 = (2 * cepat1.waktu) + lambat1.waktu + lambat2.waktu;
            
            antreanCepat.offer(cepat1);
            antreanCepat.offer(cepat2);
            
            List<String> aksiBatch = new ArrayList<>();
            List<Integer> biayaBatch = new ArrayList<>();
            
            if (biayaStrategi1 <= biayaStrategi2) {
                aksiBatch.add(cepat1.id + " " + cepat2.id + " ->"); biayaBatch.add(cepat2.waktu);
                aksiBatch.add(cepat1.id + " <-"); biayaBatch.add(cepat1.waktu);
                int idKecil = Math.min(lambat1.id, lambat2.id);
                int idBesar = Math.max(lambat1.id, lambat2.id);
                aksiBatch.add(idKecil + " " + idBesar + " ->"); biayaBatch.add(lambat1.waktu);
                aksiBatch.add(cepat2.id + " <-"); biayaBatch.add(cepat2.waktu);
            } else {
                int idKecil1 = Math.min(cepat1.id, lambat1.id);
                int idBesar1 = Math.max(cepat1.id, lambat1.id);
                aksiBatch.add(idKecil1 + " " + idBesar1 + " ->"); biayaBatch.add(lambat1.waktu);
                aksiBatch.add(cepat1.id + " <-"); biayaBatch.add(cepat1.waktu);
                
                int idKecil2 = Math.min(cepat1.id, lambat2.id);
                int idBesar2 = Math.max(cepat1.id, lambat2.id);
                aksiBatch.add(idKecil2 + " " + idBesar2 + " ->"); biayaBatch.add(lambat2.waktu);
                aksiBatch.add(cepat1.id + " <-"); biayaBatch.add(cepat1.waktu);
            }
            for (int i = 0; i < aksiBatch.size(); i++) {
                waktuBerjalan += biayaBatch.get(i);
                System.out.println(aksiBatch.get(i));
                if (waktuBerjalan > waktuMaksimal) {
                    tertangkap = true;
                    break;
                }
            }
            if (tertangkap) break;
        }
        if (!tertangkap) {
            if (tumpukanLambat.size() == 1) {
                Auror sisa = tumpukanLambat.pop();
                Auror c1 = antreanCepat.poll();
                Auror c2 = antreanCepat.poll();
                
                waktuBerjalan += c2.waktu;
                System.out.println(c1.id + " " + c2.id + " ->");
                if (waktuBerjalan > waktuMaksimal) tertangkap = true;
                
                if (!tertangkap) {
                    waktuBerjalan += c1.waktu;
                    System.out.println(c1.id + " <-");
                    if (waktuBerjalan > waktuMaksimal) tertangkap = true;
                }
                
                if (!tertangkap) {
                    waktuBerjalan += sisa.waktu;
                    System.out.println(Math.min(c1.id, sisa.id) + " " + Math.max(c1.id, sisa.id) + " ->");
                    if (waktuBerjalan > waktuMaksimal) tertangkap = true;
                }
            } else if (tumpukanLambat.isEmpty() && antreanCepat.size() == 2) {
                Auror c1 = antreanCepat.poll();
                Auror c2 = antreanCepat.poll();
                waktuBerjalan += Math.max(c1.waktu, c2.waktu);
                System.out.println(c1.id + " " + c2.id + " ->");
                if (waktuBerjalan > waktuMaksimal) tertangkap = true;
            } else if (tumpukanLambat.isEmpty() && antreanCepat.size() == 1) {
                Auror c1 = antreanCepat.poll();
                waktuBerjalan += c1.waktu;
                System.out.println(c1.id + " ->");
                if (waktuBerjalan > waktuMaksimal) tertangkap = true;
            }
        }
        if (tertangkap) {
            List<Integer> korban = new ArrayList<>();
            for (Auror a : antreanCepat) korban.add(a.id);
            for (Auror a : tumpukanLambat) korban.add(a.id);
            Collections.sort(korban);
            
            StringBuilder formatKorban = new StringBuilder("Non-survivors: [");
            for (int i = 0; i < korban.size(); i++) {
                formatKorban.append(korban.get(i));
                if (i < korban.size() - 1) {
                    formatKorban.append(",");
                }
            }
            formatKorban.append("]");
            System.out.println(formatKorban.toString());
        }
        
        pemindai.close();
    }
}