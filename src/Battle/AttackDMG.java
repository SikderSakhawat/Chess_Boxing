package Battle;

public class AttackDMG {
    public int energy, damage;
    private String name, special;

    public AttackDMG(String pieceName, int dmg, int energy, String special){
        this.damage = dmg;
        this.name = pieceName;
        this.special = special;
        this.energy = energy;
    }
    public String toString(){
        // Formats Chess Piece stats for the piece that is moving
        return String.format("\t< Chess Attack: %-12s > \nDamage: %-4d \nEnergy: %-4s \nSpecial %-4s", name, damage, energy, special);
    }
    public int getEnergy(){
        return energy;
    }
    public int getDamage(){
        return damage;
    }

    public String getSpecial() {
        return special;
    }

    public String getName() {
        return name;
    }
}
