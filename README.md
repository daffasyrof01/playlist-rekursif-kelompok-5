# Playlist Rekursif Kelompok 5

Tugas Kelompok 3 COSC6025 Data Structures and Algorithm Analysis, kelas LYCA-LEC.

## Anggota

| Nama | NIM |
|---|---|
| Adinda Diva Latifah | 2902827270 |
| M. Taufan Ariq Hafizh | 2902821254 |
| Alfira Putri Nurlita | 2902814942 |
| Risky Saputra Siahaan | 2902816222 |
| Khenaro Daffa Asyrof | 2902824590 |

## Menjalankan

Memerlukan JDK 11 atau lebih baru; telah diuji pada JDK 21.

```sh
javac -encoding UTF-8 PlaylistRekursif.java
java PlaylistRekursif
```

Pengujian opsional:

```sh
javac -encoding UTF-8 PlaylistRekursif.java PlaylistRekursifTest.java
java PlaylistRekursifTest
```

77 pemeriksaan lulus: playlist kosong, berbagai ukuran sampai 10 lagu, durasi sama, total, maksimum, urutan reverse traversal, konversi menit:detik, dan penolakan durasi tidak valid.

## Analisis

`totalDurasi`, `tampilkanMundur`, dan `cariDurasiTerpanjang` memiliki waktu O(n) dan ruang call stack O(n). Relasinya T(n)=T(n-1)+c; jumlah pemanggilan C(n)=n+1, termasuk base case. Grafik pertumbuhan adalah model teoritis, bukan waktu milidetik.

Total 10 lagu adalah **44 menit 22 detik** (44,366666... menit riil); maksimum **7 menit 29 detik**. Data awal menggunakan M.SS seperti tugas sebelumnya: 4.23 berarti 4 menit 23 detik. Constructor mengubahnya menjadi menit riil (`menit + detik / 60.0`). Getter dan kedua fungsi numerik mengembalikan `double` dalam satuan menit riil; keluaran memakai menit:detik.

Gambar dokumentasi merupakan transkrip keluaran Java aktual, bukan screenshot IDE. Berkas teks lengkap disertakan untuk pemeriksaan dan reproduksi.

## Koreksi dan batasan

Koreksi bertanda `- khenaro` menyesuaikan return type fungsi maksimum menjadi `double` sesuai instruksi, berikut pemanggil, komentar parameter, serta konversi satuan durasi. File asli kelompok disimpan terpisah secara lokal. Data lagu dan pola pengukuran Anggota 4 tetap dipertahankan.

Masukan diasumsikan tidak null, 0 <= n <= panjang array, elemen prefix terisi, serta durasi tidak negatif. Rekursi pada playlist sangat besar berisiko StackOverflowError. Angka pengukuran dipengaruhi JIT dan biaya output; bukan bukti tunggal kompleksitas.

Repository ini berisi source, pengujian opsional, keluaran aktual, grafik, screenshot, serta laporan final. File `.class` dan berkas kerja internal tidak disertakan.
