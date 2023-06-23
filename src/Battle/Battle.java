package Battle;

import java.util.ArrayList;
import java.util.Random;

public class Battle {
    private boolean attackHit = true,usedEnergy = true, disabledPiece = false;
    private boolean pieceStunned = false;
    private int initialHP, currentHP, energy;
    ArrayList<AttackDMG> attacks;
    String name, special;

    public Battle(String name, int HP, ArrayList<AttackDMG> attacks, String special){
        this.name = name;
        this.initialHP = HP;
        this.currentHP = initialHP; // this number changes!
        this.attacks = attacks;
        this.energy = 50;
        this.special = special;
    }

    // Made to attack the enemy chess piece in battle
    public int randomAttack(Battle enemy){
        boolean canMove = false; // first sets your moves to false
        ArrayList<Integer> usableMoves = new ArrayList<>();
        Random number = new Random();
        for(int i = 0; i < enemy.attacks.size(); i++){
            if(pieceHasEnergy(enemy, i)) usableMoves.add(i);
        }
        if(usableMoves.size() == 0) return -1; // if we run out of energy this happens
        return number.nextInt(usableMoves.size()); // choose options of possible attacks in battle
    }

    // checks if the piece selected has enough energy to execute another attack
    public boolean pieceHasEnergy(Battle now, int attackInfo){
        boolean pieceHasEnergy = false;
        AttackDMG pieceAttack = now.attacks.get(attackInfo);
        if(now.energy >= pieceAttack.getEnergy())  pieceHasEnergy = true;
        return pieceHasEnergy;
    }

    public void attackPiece(Battle you, int attackInfo, Battle enemy){
        AttackDMG pieceAttack = you.attacks.get(attackInfo);
        if(attackHit) attackCalc(you, attackInfo, enemy);
    }
    public void setEnergy(Battle you, int energy){
        you.energy -= energy;
        if(you.energy <= 0) you.energy = 0;
    }

    public void attackCalc(Battle you, int attackInfo, Battle enemy){
        AttackDMG attack = you.attacks.get(attackInfo);
        int damage = attack.getDamage();
        if(enemy.currentHP <= 0) enemy.currentHP = 0;
        if(you.currentHP <= 0) you.currentHP = 0;
        if(attackHit){
            enemy.currentHP -= damage;
            if(usedEnergy){
                setEnergy(you,attack.getEnergy()); // energy left after attack is done
            }
        }
        //this displays the current stats of the pieces in the battle with nice formatting
        System.out.println("\t\t\tCurrent Chess Piece Stats: \n");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("\t%-11s: HP: %-3d Energy: %-4d\n",enemy.name,enemy.currentHP,enemy.energy);
        System.out.printf("\t%-11s: HP: %-3d Energy: %-4d\n",you.name,you.currentHP,you.energy);
        System.out.println("-------------------------------------------------------------------------");
    }

    public static boolean pieceFaint(Battle piece){
        return piece.currentHP <= 0;
    }

    // Special Moves
    public void useSpecial(Battle you, int attackInfo, Battle enemy){
        AttackDMG attack = you.attacks.get(attackInfo);
        if(attack.getSpecial().equals("recharge")){
            System.out.printf("%s has used %s\n", you.name, attack.getName());
            recharge(you);
        }
        else if(attack.getSpecial().equals("stun")){
            System.out.printf("%s has used %s\n",you.name,attack.getName());
            stun(you, enemy);
        }
        else if(attack.getSpecial().equals("disable")){
            if(enemy.disabledPiece){
                System.out.printf("%s has already been disabled\n",enemy.name);
            }
            else{
                System.out.printf("%s used $s\n %s has been disabled",you.name, attack.getName(),enemy.name);
                for(int i = 0; i < enemy.attacks.size(); i++){
                    enemy.attacks.get(i).damage -= 10;
                    if(enemy.attacks.get(i).damage < 0){
                        enemy.attacks.get(i).damage = 0;//attack damage can never go below 0.
                    }
                    disabledPiece = true;
                }
            }
        }
    }
    // Basically recharges 20 energy
    public void recharge(Battle piece){
        piece.energy += 20;
        if(piece.energy >= 50) piece.energy = 50;
        System.out.printf("%s has gained 20 energy\n", piece.name);
    }

    // 50% chance of working to stun the target for a turn
    public void stun(Battle you, Battle enemy){
        if(passOrFail()){
            enemy.pieceStunned = true;
            System.out.printf("%s has been stunned, %s pass\n", enemy.name, enemy.name);
        }
        else{
            System.out.printf("%s failed to stun %s\n", you.name, enemy.name);
        }
    }

    public boolean passOrFail(){
        boolean pass = false;
        Random number = new Random();
        int result = number.nextInt(2);
        if(result == 0){
            pass = true;
        }
        return pass;
    }
}
