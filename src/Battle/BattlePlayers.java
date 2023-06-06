package Battle;

public class BattlePlayers {
    private final Character selectedChar;

    public BattlePlayers(Character selectedChar){
        this.selectedChar = selectedChar;
    }

    public void setCharactersMoveSet(){
        if(selectedChar.getName().equals("Pawn")){
            selectedChar.getMoveSet().addMove("Strike", 5);
            selectedChar.getMoveSet().addMove("Slap", 7);
            selectedChar.setHitPoints(15);
        }
        if(selectedChar.getName().equals("Knight")){
            selectedChar.getMoveSet().addMove("Hop", 8);
            selectedChar.getMoveSet().addMove("Stomp", 9);
            selectedChar.setHitPoints(20);
        }
        if(selectedChar.getName().equals("Bishop")){
            selectedChar.getMoveSet().addMove("Curse", 8);
            selectedChar.getMoveSet().addMove("Condemn", 9);
            selectedChar.setHitPoints(20);
        }
        if(selectedChar.getName().equals("Rook")){
            selectedChar.getMoveSet().addMove("Brick Toss", 5);
            selectedChar.getMoveSet().addMove("Bump", 6);
            selectedChar.setHitPoints(35);
        }
        if(selectedChar.getName().equals("Queen")){
            selectedChar.getMoveSet().addMove("Punish", 15);
            selectedChar.getMoveSet().addMove("Slit", 12);
            selectedChar.setHitPoints(30);
        }
    }
}
