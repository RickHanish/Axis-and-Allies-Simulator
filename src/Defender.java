public class Defender implements Comparable<Defender>{
    private boolean isCasualty;
    private String name;
    private int defendRating;

    public Defender(String name, int defendRating, boolean isCasualty){
        this.name = name;
        this.defendRating = defendRating;
        this.isCasualty = isCasualty;
    }

    public String getName(){
        return name;
    }

    public int getDefendRating(){
        return defendRating;
    }

    public boolean isCasualty(){
        return isCasualty;
    }

    @Override
    public int compareTo(Defender other){
        if(name.equals("Anti-Aircraft"))
            return -1;
        else if(other.name.equals("Anti-Aircraft"))
            return 1;
        else return defendRating - other.defendRating;
    }

    public boolean rollDie(){
        int roll = (int) (Math.random() * 6 + 1);
        return roll <= defendRating;
    }

    public void makeCasualty(){
        isCasualty = true;
//		return this;
    }

    public void makeNotCasualty(){
        isCasualty = false;
    }

    public String toString(){
        return name + " " + defendRating + " " + isCasualty;
    }
}