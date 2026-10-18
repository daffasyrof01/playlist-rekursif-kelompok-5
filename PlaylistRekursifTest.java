import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class PlaylistRekursifTest {
    public static void main(String[] args) {
        int[] sizes = {0, 1, 2, 3, 4, 5, 6, 8, 10};
        int assertions = 0;
        for (int n : sizes) {
            double expectedTotal = 0;
            double expectedMax = 0;
            for (int i = 0; i < n; i++) {
                expectedTotal += PlaylistRekursif.playlist[i].getDurasi();
                expectedMax = Math.max(expectedMax, PlaylistRekursif.playlist[i].getDurasi());
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
            System.out.println("PASS n=" + n + " total=" + String.format(java.util.Locale.US, "%.2f", expectedTotal)
                    + " max=" + String.format(java.util.Locale.US, "%.2f", expectedMax) + " reverse=" + lines.length);
        }
        Lagu[] same = {new Lagu("A", "Artis", 2.5), new Lagu("B", "Artis", 2.5)};
        check(PlaylistRekursif.cariDurasiTerpanjang(same, 2) == 2.5, "Durasi sama gagal");
        check(PlaylistRekursif.totalDurasi(new Lagu[0], 0) == 0, "Playlist kosong gagal");
        check(PlaylistRekursif.cariDurasiTerpanjang(new Lagu[0], 0) == 0, "Maksimum kosong gagal");
        System.out.println("ALL PASS: " + (assertions + 3) + " pemeriksaan; file asli tidak diubah.");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
