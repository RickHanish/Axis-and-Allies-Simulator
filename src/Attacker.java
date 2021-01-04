public class Attacker implements Comparable<Attacker>{
    private String name;
    private int attackRating;

    public Attacker(String name, int attackRating){
        this.name = name;
        this.attackRating = attackRating;
    }

    public String getName(){
        return name;
    }

    public int getAttackRating(){
        return attackRating;
    }

    @Override
    public int compareTo(Attacker other){
        if(Main.isLandBattle() && (name.equals("Battleship") && !other.getName().equals("Fighter")) && ((name.equals("Battleship") && !other.getName().equals("Bomber"))))
            return -1;
        else if(Main.isLandBattle() && (other.name.equals("Battleship") && !name.equals("Fighter")) && (other.name.equals("Battleship") && !name.equals("Bomber")))
            return 1;
        else return attackRating - other.attackRating;
    }

    public void setAttackRating(int num){
        attackRating = num;
    }

    public String toString(){
        return name + " " + attackRating;
    }

    public boolean rollDie(){
        int roll = (int) (Math.random() * 6 + 1);
        return roll <= getAttackRating();
    }
}
