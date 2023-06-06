package Battle;

import java.util.HashMap;
import java.util.Map;

public class MoveSet {
    private Map<String, Integer> moveAttackPower;

    public MoveSet(){
        moveAttackPower = new HashMap<>();
    }

    public void addMove(String moveName, int attackPower){
        moveAttackPower.put(moveName, attackPower);
    }

    public int getMoveAttackPower(String moveName){
        Integer attackPower = moveAttackPower.get(moveName);
        if(attackPower == null) throw new IllegalArgumentException("Move not found");
        return attackPower;
    }
}
