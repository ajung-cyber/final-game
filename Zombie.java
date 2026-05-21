public class Zombie extends Musuh implements BisaLoot {

    public Zombie() {
    super("zombie lemper", 200);
    }

    @Override
    public void serangPemain() {
        System.out.println(this.namaMusuh + "menyemburkan air liur! Player -30 HP");
    }
    public void suaraKhas() {
        System.out.println(this.namaMusuh + " Wau..Wau.. ");
    }
    @Override
    public void jatuhkanItem() {
        System.out.println(this.namaMusuh + " telah dijatuhkan");   
    }
}