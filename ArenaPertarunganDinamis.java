import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class ArenaPertarunganDinamis {
    public static void main(String[] args) {

        Scanner input= new Scanner(System.in);

        ArrayList<Musuh>gelombangMonster = new ArrayList<>();
        gelombangMonster.add(new Slime()); 
        gelombangMonster.add(new Naga()); 
        gelombangMonster.add(new Zombie()); 

        System.out.println("======================================");
        System.out.println("      ARENA RPG: GELOMBANG MONSTER");
        System.out.println("======================================\n");
        System.out.println("AWAS! Sekelompok monster menghadang Anda!");

        boolean isBermain = true;

        // Loop berjalan selama ArrayList tidak boleh kosong
        while (isBermain && !gelombangMonster.isEmpty() ){

            System.out.println("\n--- STATUS MONSTER ---");
        // Menggunakan .size() sebagai pengganti .length
            for (int i = 0; i < gelombangMonster.size(); i++) {
                Musuh m = gelombangMonster.get(i);
                System.out.println( (i + 1) + ". " +m.namaMusuh + " (HP: " +m.healthPoint + ")" );
            }
            System.out.println("0.kabur dari pertarungan");
            System.out.print("\nPilih target monster: ");

            try {

                int pilihanTarget = input.nextInt();

                // kabur
                if (pilihanTarget == 4) {
                    System.out.println("Anda lari terbirit-birit dari arena...");
                    continue;
                }
                else if (pilihanTarget == 8) {
                    try (ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream("savegane_rpg.dat"))) {
                            oos.writeObject(gelombangMonster);
                            System.out.println(
                                ">>> BERHASIL: Game telah disimpan! <<<");

                        } catch (IOException e) {
                            System.out.println(
                                ">>> GAGAL: Terjadi kesalahan saat menyimpan game. "
                                + e.getMessage());
                        }
                        continue;
                    }
                    else if (pilihanTarget == 9) {
                        try (ObjectInputStream ois =
                        new ObjectInputStream(new FileInputStream("savegame_rpg.dat"))) {
                            gelombangMonster = (ArrayList<Musuh>) ois.readObject();
                            System.out.println(">>> BERHASIL; Game berhasil dimuat! <<<");
                        } catch (FileNotFoundException e){
                            System.out.println
                            (">>> GAGAL: File save game belum ada . Silahkan Save Game terlebih dahulu");
                        }catch (IOException | ClassNotFoundException e){
                            System.out.println
                            (">>> GAGAL: Terjadi kesalahan saat membaca file save. " + e.getMessage());
                        }
                        continue;
                    }

                // validasi input
                if (pilihanTarget < 1 || pilihanTarget > 3) {
                    System.out.println("Input tidak valid! Anda membuang giliran.");
                    continue;
                }
                
                int indeksMonster = pilihanTarget - 1;
                Musuh target = gelombangMonster.get(indeksMonster);
                
                System.out.println("Masukan kekuatan serangan Anda (10-100): ");

                int power = input.nextInt();

                if (power < 10 || power > 100){
                // Lemparkan Custom E   xception Anda secara sengaja disini beserta pesannya!
                    throw new SeranganTidakValidException("Kekuatan serangan harus antara 10 sampai 100!");
                } 

                System.out.println("\n>>> HASIL SERANGAN ANDA <<<");
                target.terimaDamage(power);
                
                // Damage pemain
                
                if (target.healthPoint <= 0) {
                    System.out.println(target.namaMusuh+ " hancur menjadi debu!");
                
                if (target instanceof BisaLoot) {
                    BisaLoot loot = (BisaLoot) target;
                    loot.jatuhkanItem();
                }
            gelombangMonster.remove(indeksMonster);
            }
                
                    
            
        } catch (Exception e) {
             System.out.println("Terjadi kesalahan input, silahkan coba lagi.");
             input.nextLine();
             continue;

        }

        // jika semua monster mati setelah serangan pemain, langsung keluar dari

        if (gelombangMonster.isEmpty()) {
            System.out.println("\nSELAMAT! Semua monster telah dibersihkan dari arena!");
            break;
        }
        
            // Giliran monster menyerang
            System.out.println("\n<<< GILIRAN MONSTER MEMBALAS >>>");
            for (int i = 0; i < gelombangMonster.size(); i++) {
                if (gelombangMonster.get (i).healthPoint > 0) {
                    Musuh monsterAktif = gelombangMonster.get(i);
                    monsterAktif.suaraKhas();
                    if (monsterAktif instanceof BisaTerbang) {
                        System.out.println(
                            "[PERINGATAN! SERANGAN UDARA TERDETEKSI]"
                        );
                        BisaTerbang monsterTerbang =
                            (BisaTerbang) monsterAktif;
                        monsterTerbang.lepasLandas();
                        monsterTerbang.seranganUdara();
                    } else {
                        monsterAktif.serangPemain();
                    }
                }
            }
            // Cek semua monster mati
            boolean semuaMati = true;
            for (int i = 0; i < gelombangMonster.size(); i++) {
                if (gelombangMonster.get (i).healthPoint > 0) {
                    semuaMati = false;
                    break;
                }
            }
            if (semuaMati) {
                System.out.println(
                    "\nSELAMAT! Anda telah mengalahkan semua monster!"
                );
                isBermain = false;
            }
            System.out.println("------------------------------------");
        }
        input.close();
        System.out.println("Permainan Berakhir.");
    }
}