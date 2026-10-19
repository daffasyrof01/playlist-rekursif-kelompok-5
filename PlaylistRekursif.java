
/*
 * KELOMPOK 5
 * Data Structures and Algorithm Analysis
 *
 * Nama, NIM, kelas:
 * Adinda Diva Latifah - 2902827270 - LYCA-LEC
 * M. Taufan Ariq Hafizh - 2902821254 - LYCA-LEC
 * Alfira Putri Nurlita - 2902814942 - LYCA-LEC
 * Risky Saputra Siahaan - 2902816222 - LYCA-LEC
 * Khenaro Daffa Asyrof - 2902824590 - LYCA-LEC
 */

// Class Lagu dari pengembangan tugas sebelumnya - adinda
class Lagu {
    private String judul;
    private String artis;
    private double durasi;

    // Input M.SS dari tugas sebelumnya dikonversi sekali menjadi menit riil - khenaro.
    public Lagu(String judul, String artis, double durasi) {
        this.judul = judul;
        this.artis = artis;
        if (!Double.isFinite(durasi) || durasi < 0) {
            throw new IllegalArgumentException("Durasi harus berupa M.SS yang tidak negatif");
        }
        int menit = (int) durasi;
        int detik = (int) Math.round((durasi - menit) * 100);
        if (detik >= 60) {
            throw new IllegalArgumentException("Bagian detik harus antara 00 dan 59");
        }
        this.durasi = menit + detik / 60.0;
    }

    // Mengambil judul lagu - adinda
    public String getJudul() {
        return judul;
    }

    // Mengambil nama artis - adinda
    public String getArtis() {
        return artis;
    }

    // Durasi dikembalikan dalam menit riil, bukan angka berformat M.SS - khenaro.
    public double getDurasi() {
        return durasi;
    }
}

// Class utama - adinda
public class PlaylistRekursif {

    // Pembulatan menghindari kehilangan satu detik akibat floating point - khenaro.
    static String formatDurasi(double menit) {
        long detik = Math.round(menit * 60);
        return String.format(java.util.Locale.US, "%d:%02d", detik / 60, detik % 60);
    }

    // Array objek Lagu dengan kapasitas 10 lagu - adinda
    static Lagu[] playlist = new Lagu[10];

    // Mengisi playlist menggunakan data dari tugas sebelumnya - adinda
    static {
        playlist[0] = new Lagu(
            "Perfect", "Ed Sheeran", 4.23
        );

        playlist[1] = new Lagu(
            "DATURA [paroxysm]", "Crywolf", 3.35
        );

        playlist[2] = new Lagu(
            "IN THE NAME OF LOVE",
            "world's end girlfriend", 7.29
        );

        playlist[3] = new Lagu(
            "Burning Down The Nicotine Armoire, Pt. 2",
            "Dance Gavin Dance", 3.31
        );

        playlist[4] = new Lagu(
            "Teh Hijau", "Tulus", 3.33
        );
        playlist[5] = new Lagu(
            "Lathi", "Weird Genius", 3.30
        );
        playlist[6] = new Lagu(
            "Happier Than Ever", "Billie Eilish", 4.58
        );
        playlist[7] = new Lagu(
            "Yellow", "Coldplay", 4.26
        );
        playlist[8] = new Lagu(
            "Fix You", "Coldplay", 4.55
        );
        playlist[9] = new Lagu(
            "Viva La Vida", "Coldplay", 4.02
        );
    }

    // Fungsi rekursif total durasi (Soal 1) - risky
    /**
     * totalDurasi - risky
     * Tujuan   : Menghitung total durasi seluruh lagu pada playlist secara rekursif.
     * Parameter: list = array playlist, n = jumlah lagu yang dihitung
     *            (pemanggilan awal: totalDurasi(playlist, jumlahLagu)).
     *
     * Base case      : n == 0 (tidak ada lagu tersisa) -> return 0.
     * Recursive case : durasi lagu terakhir (list[n-1]) + totalDurasi(list, n-1).
     *                  Ukuran masalah mengecil 1 lagu di tiap pemanggilan.
     *
     * Kompleksitas waktu : O(n) -> n+1 pemanggilan, tiap pemanggilan kerja O(1).
     * Kompleksitas ruang : O(n) -> kedalaman call stack sebesar n.
     * Recurrence         : T(n) = T(n-1) + c, T(0) = c0  =>  T(n) = O(n)
     */
    static double totalDurasi(Lagu[] list, int n) { // - risky
        // Base case
        if (n == 0) {
            return 0;
        }
        // Recursive case
        return list[n - 1].getDurasi() + totalDurasi(list, n - 1);
    }

    // Recursive function to show reverse playlist (Q2) - Taufan
    /**
     * Parameter: list = array playlist, n = jumlah lagu, bukan indeks array - khenaro
     *
     * Base case      : n == 0 (no song left) -> return
     * Recursive case : tampilkan lagu ke-n, lalu panggil tampilkanMundur(list, n-1), cetak lagu terakhir -> ... -> lagu pertama
     *
     * Kompleksitas waktu : O(n) -> n+1 pemanggilan termasuk base case - khenaro
     * Kompleksitas ruang : O(n) -> kedalaman call stack sebesar n
     */
    static void tampilkanMundur(Lagu[] list, int n) { // - Taufan
        // Base case
        if (n == 0) {return;}
        
        // Recursive case
        int index = n - 1; // Convert from number of songs to index array
        System.out.println(
            (index + 1) + ". "
            + list[index].getJudul()
            + " - " + list[index].getArtis()
            + " (" + formatDurasi(list[index].getDurasi()) + ")"
        );
        tampilkanMundur(list, n - 1);
    }

    // Recursive function to find longest song duration (Q3) - Taufan
    /**
     * Tujuan: mencari nilai durasi terbesar dari n lagu pertama.
     * Parameter: list = array playlist, n = jumlah lagu yang diperiksa.
     * Return   : durasi terpanjang (double), sesuai instruksi tugas - khenaro
     *
     * Base case      : n == 0 -> return 0; durasi lagu diasumsikan tidak negatif.
     * Recursive case : maksimum durasi list[n-1] dan hasil untuk n-1 lagu.
     *
     * Kompleksitas waktu : O(n) -> n+1 pemanggilan termasuk base case.
     * Kompleksitas ruang : O(n) -> kedalaman call stack sebesar n
     */
    static double cariDurasiTerpanjang(Lagu[] list, int n) { // - Taufan; penyesuaian return type - khenaro
        // Base case
        if (n == 0) { return 0; }
        
        // Recursive case
        return Math.max(list[n - 1].getDurasi(), cariDurasiTerpanjang(list, n - 1));
    }

    //alfira - pengukuran waktu eksekusi
    // Rata-rata 10.000 pemanggilan dengan nanoTime; bukan fungsi rekursif - khenaro.
    
    static void ujiWaktuEksekusi() {
        int[] ukuranData = {3, 5, 10};
        int pengulangan = 10000;

        java.io.PrintStream outputAsli = System.out;

        java.io.PrintStream outputKosong =
            new java.io.PrintStream(
                java.io.OutputStream.nullOutputStream()
            );

        System.out.println("\n=== PENGUKURAN WAKTU EKSEKUSI ===");
        System.out.println("Rata-rata waktu per pemanggilan (milidetik)");

        System.out.printf(
            "%-12s %-18s %-18s %-18s%n",
            "Jumlah Lagu",
            "totalDurasi",
            "tampilkanMundur",
            "cariDurasiTerpanjang"
        );

        try {
            for (int n : ukuranData) {

                // Pemanasan awal
                totalDurasi(playlist, n);
                cariDurasiTerpanjang(playlist, n);

                // 1. Mengukur totalDurasi()
                long mulai = System.nanoTime();

                for (int i = 0; i < pengulangan; i++) {
                    totalDurasi(playlist, n);
                }

                long selesai = System.nanoTime();

                double waktuTotalDurasi =
                    (selesai - mulai) / 1_000_000.0 / pengulangan;

                // 2. Mengukur tampilkanMundur()
                double waktuMundur;

                try {
                    System.setOut(outputKosong);
                    mulai = System.nanoTime();

                    for (int i = 0; i < pengulangan; i++) {
                        tampilkanMundur(playlist, n);
                    }

                    selesai = System.nanoTime();

                    waktuMundur =
                        (selesai - mulai) / 1_000_000.0 / pengulangan;

                } finally {
                    System.setOut(outputAsli);
                }

                // 3. Mengukur cariDurasiTerpanjang()
                mulai = System.nanoTime();

                for (int i = 0; i < pengulangan; i++) {
                    cariDurasiTerpanjang(playlist, n);
                }

                selesai = System.nanoTime();

                double waktuTerpanjang =
                    (selesai - mulai) / 1_000_000.0 / pengulangan;

                // Menampilkan hasil pengukuran
                System.out.printf(
                    java.util.Locale.US,
                    "%-12d %-18.6f %-18.6f %-18.6f%n",
                    n,
                    waktuTotalDurasi,
                    waktuMundur,
                    waktuTerpanjang
                );
            }

        } finally {
            System.setOut(outputAsli);
            outputKosong.close();
        }
    }


    // Menjalankan ketiga operasi dan eksperimen; prasyarat 0 <= n <= list.length - khenaro.
    public static void main(String[] args) {

        System.out.println(
            "=== ANALISIS REKURSIF PLAYLIST ==="
        );

        // Menghitung jumlah lagu yang terisi - adinda
        int jumlahLagu = 0;

        for (Lagu lagu : playlist) {
            if (lagu != null) {
                jumlahLagu++;
            }
        }

        System.out.println("Jumlah lagu: " + jumlahLagu);
        
        // Menghitung dan menampilkan total durasi playlist - risky
        double total = totalDurasi(playlist, jumlahLagu);
        System.out.println(String.format(
            java.util.Locale.US,
            "Total durasi: %s (%.2f menit)", formatDurasi(total), total
        ));

        // find and display the song with the longest duration - taufan
        double durasiTerpanjang = cariDurasiTerpanjang(playlist, jumlahLagu);
        if (jumlahLagu > 0) {
            // Pencarian judul hanya untuk tampilan; fungsi rekursif tetap mengembalikan double - khenaro.
            Lagu terpanjang = playlist[0];
            for (Lagu lagu : playlist) {
                if (lagu != null && Double.compare(lagu.getDurasi(), durasiTerpanjang) == 0) {
                    terpanjang = lagu;
                    break;
                }
            }
            System.out.println(String.format(
                java.util.Locale.US,
                "Lagu terpanjang: %s - %s : %s",
                terpanjang.getJudul(),
                terpanjang.getArtis(),
                formatDurasi(terpanjang.getDurasi())
            ));
        } else {
            System.out.println("\nPlaylist kosong!");
        }

        // Menampilkan data playlist - adinda
        System.out.println("\nDaftar Lagu:");

        for (int i = 0; i < playlist.length; i++) {
            if (playlist[i] != null) {
                System.out.println(
                    (i + 1) + ". "
                    + playlist[i].getJudul()
                    + " - " + playlist[i].getArtis()
                    + " (" + formatDurasi(playlist[i].getDurasi()) + ")"
                );
            }
        }
        
        // Show reverse playlist - Taufan
        System.out.println("\nPlaylist secara terbalik:");
        tampilkanMundur(playlist, jumlahLagu);

        //pengukuran waktu eksekusi - alfira
        ujiWaktuEksekusi();

   }
}
      
