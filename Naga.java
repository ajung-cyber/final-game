public class Naga extends Musuh implements BisaTerbang, BisaLoot {

    public Naga() { super("saipul nafas panjang", 500);
    }    

    @Override
    public void serangPemain() {
        System.out.println(this.namaMusuh + " menggigit! Pemain -30 HP.");
    }
    public void suaraKhas() {
        System.out.println(this.namaMusuh + " WAUZ... WAUZ.. ");
    }
    @Override
    public void lepasLandas() {
        System.out.println(this.namaMusuh + " terbang tinggi! Sulit diserang.");
    }
    @Override
    public void seranganUdara() {
        System.out.println(this.namaMusuh + " menyemburkan badai api!a Pemain -80 HP.");
    }
   @Override
    public void jatuhkanItem() {
        System.out.println(this.namaMusuh + " telah dijatuhkan");   
    } 
}