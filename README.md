# Tubes-PM-Kelompok-6

## Info

Nama aplikasi: Hepengku  
Tim pengembang: Kelompok 6

- Sintong sutanto Johannes L.Tobing - 221402076  (Project Manager) 
  Role *()*
- Farhan Anfasa Maulana - 221402097  
  Role *()*
- Ghalbi Daffa Yustiawan - 221402103  
  Role *()*
- Ivan Mulatua Tambunan  - 221402106  
  Role *()*
- Perwira Satria Taufik QD - 221402124  
  Role *()*
- Wahyu Jhon Riadi Sianipar - 221402135  
  Role *()*

## **Desc**

Hepengku adalah aplikasi money tracker yang dirancang untuk membantu pengguna mengelola keuangan pribadi dengan lebih efisien. Hepengku memungkinkan Anda untuk mencatat pendapatan dan pengeluaran sehari-hari, memantau kebiasaan belanja, serta merencanakan anggaran dengan mudah.

## UI Design

[Link Figma](https://www.figma.com/design/mPpeR4zjzgxXvZZdBbcgvR/Hepengki?node-id=0-1&t=qBPSTOTxqcwNBmKJ-1)

## Database Laravel
[Link Github](https://github.com/Sintong23/Database_Hepengku)

## **Features**

   
**1. Dashboard:**
- Melihat pengeluaran/pemasukan berdasarkan periode (harian, mingguan, bulanan).
- Melihat sisa saldo/dana kita.
- Menampilkan transaksi terbaru (baik pengeluaran maupun pemasukan).
- Menampilkan transaksi yang mendekati batas anggaran atau target keuangan.
- Menamilkan riwayat transaksi.
- Menampilkan riwayat pemasukan dan pengeluaran
    
**2. Profil:**
- Melihat data pengguna/profil pengguna.

**3. Reports:**
- Melihat statistik pengeluaran, pemasukan, dan saldo bulanan.
- Melihat budget bulanan.
- Mengedit budget bulanan.

**4. Add:**
- Menambahkan pemasukan dan kategori pemasukan.
- Menambahkan pengeluaran dan kategori pengeluaran.

**5. Data:**
- Statistik pemasukan dan pengeluaran

  
## **Library**

- Retrofit (untuk komunikasi API RESTful)
-	Gson (untuk parsing JSON)
-	MPAndroidChart (untuk visualisasi data seperti PieChart) 
-	Lifecycle & Coroutines (untuk pengelolaan UI dan pemanggilan asinkron)
-	Navigation Component (untuk navigasi antar fragment)
-	Material Design Component

## **Permission**

- Internet Access Permission
- Network State (Opsional, jika Anda ingin memastikan koneksi tersedia sebelum mengakses API) Permission

## **Environment**
Beberapa syarat environment untuk menjalankan aplikasi ini:

- **Kotlin**: Versi terbaru (>= 1.6.0)
- **Android Studio**: Versi terbaru dengan Android SDK 31 atau lebih tinggi.
- **JDK**: Java Development Kit versi 21.
- **Gradle**: Versi yang sesuai dengan Android Studio terbaru.

### **Instalasi dan Setup**

1. **Install Android Studio**
    - Download dan install Android Studio dari [Android Studio Official Website](https://developer.android.com/studio).
    - Pastikan Android SDK 31 atau lebih tinggi terinstal melalui SDK Manager.
2. **Install Database Api Laravel**
   - Untuk informasi lebih lanjut bisa dilihat di [Database Laravel](https://github.com/Sintong23/Database_Hepengku)
3. **Install JDK**
    - Download dan install JDK versi 11 dari [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) atau [OpenJDK](https://openjdk.java.net/install/).
4. **Clone Repository**
    - Clone repository ini ke lokal komputer:
      ```
      git clone https://github.com/Sintong23/Hepengku.git
      ```
      cd Hepengku
      ```
