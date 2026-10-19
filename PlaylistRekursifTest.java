import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class PlaylistRekursifTest {
    public static void main(String[] args) {
        int[] sizes = {0, 1, 2, 3, 4, 5, 6, 8, 10};
        int[] seconds = {263, 215, 449, 211, 213, 210, 298, 266, 295, 242};
        int assertions = 0;
        for (int n : sizes) {
            double expectedTotal = 0;
            double expectedMax = 0;
            for (int i = 0; i < n; i++) {
                expectedTotal += seconds[i] / 60.0;
                expectedMax = Math.max(expectedMax, seconds[i] / 60.0);
            }
            check(Math.abs(PlaylistRekursif.totalDurasi(PlaylistRekursif.playlist, n) - expectedTotal) < 1e-9,
                    "Total salah untuk n=" + n);
            check(Math.abs(PlaylistRekursif.cariDurasiTerpanjang(PlaylistRekursif.playlist, n) - expectedMax) < 1e-9,
                    "Maksimum salah untuk n=" + n);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PrintStream original = System.out;
            try (PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
                System.setOut(capture);
                PlaylistRekursif.tampilkanMundur(PlaylistRekursif.playlist, n);
            } finally {
                System.setOut(original);
            }
            String output = buffer.toString(StandardCharsets.UTF_8);
            String[] lines = output.isEmpty() ? new String[0] : output.strip().split("\\R");
            check(lines.length == n, "Jumlah baris terbalik salah untuk n=" + n);
            for (int i = 0; i < n; i++) {
                int index = n - 1 - i;
                check(lines[i].startsWith((index + 1) + ". " + PlaylistRekursif.playlist[index].getJudul() + " - "),
                        "Urutan terbalik salah untuk n=" + n);
                assertions++;
            }
            assertions += 3;
            System.out.println("PASS n=" + n + " total=" + PlaylistRekursif.formatDurasi(expectedTotal)
                    + " max=" + PlaylistRekursif.formatDurasi(expectedMax) + " reverse=" + lines.length);
        }
        Lagu[] same = {new Lagu("A", "Artis", 2.5), new Lagu("B", "Artis", 2.5)};
        check(Math.abs(PlaylistRekursif.cariDurasiTerpanjang(same, 2) - 170 / 60.0) < 1e-9, "Durasi sama gagal");
        check(PlaylistRekursif.totalDurasi(new Lagu[0], 0) == 0, "Playlist kosong gagal");
        check(PlaylistRekursif.cariDurasiTerpanjang(new Lagu[0], 0) == 0, "Maksimum kosong gagal");
        check(PlaylistRekursif.formatDurasi(PlaylistRekursif.totalDurasi(PlaylistRekursif.playlist, 10)).equals("44:22"),
                "Total menit:detik gagal");
        check(PlaylistRekursif.formatDurasi(PlaylistRekursif.cariDurasiTerpanjang(PlaylistRekursif.playlist, 10)).equals("7:29"),
                "Durasi terpanjang gagal");
        Lagu[] carry = {new Lagu("A", "Artis", 0.59), new Lagu("B", "Artis", 0.02)};
        check(PlaylistRekursif.formatDurasi(PlaylistRekursif.totalDurasi(carry, 2)).equals("1:01"),
                "Penjumlahan melewati 60 detik gagal");
        check(PlaylistRekursif.formatDurasi(new Lagu("A", "Artis", 4.02).getDurasi()).equals("4:02"),
                "Nol di depan detik gagal");
        for (double invalid : new double[] {-1, Double.NaN, Double.POSITIVE_INFINITY, 1.60}) {
            boolean rejected = false;
            try {
                new Lagu("Tidak valid", "Artis", invalid);
            } catch (IllegalArgumentException expected) {
                rejected = true;
            }
            check(rejected, "Durasi tidak valid diterima: " + invalid);
        }
        System.out.println("ALL PASS: " + (assertions + 11) + " pemeriksaan; file asli tidak diubah.");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
