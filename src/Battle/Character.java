package Battle;

public class Character {
    private String name;
    private int hitPoints;
    private int attackPoints;

    private MoveSet moveSet;

    Character(String name, int hitPoints, int attackPoints, MoveSet moves){
        this.name = name;
        this.hitPoints = hitPoints;
        this.moveSet = moves;
        this.attackPoints = attackPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public String getName() {
        return name;
    }

    public MoveSet getMoveSet(){
        return moveSet;
    }
}
