import java.util.*;

public class AttackerList{
    private Queue<Attacker> attackerList;
    private Map<String, Integer> counter;

    public AttackerList(){
        attackerList = new PriorityQueue<>();
        counter = new TreeMap<>();
    }

    public AttackerList(PriorityQueue<Attacker> pq, TreeMap<String, Integer> tm){
        attackerList = pq;
        counter = tm;
    }

    public int size(){
        return attackerList.size();
    }

    public void addAttacker(Attacker a){
        attackerList.add(a);
        if(counter.containsKey(a.getName())){
            counter.put(a.getName(), counter.get(a.getName()) + 1);
        }
        else counter.put(a.getName(), 1);
    }

    public Attacker killAttacker(){
        if(attackerList.size() != 0){
            Attacker rem = attackerList.remove();
            counter.put(rem.getName(), counter.get(rem.getName()) - 1);
            if(counter.get(rem.getName()) == 0)
                counter.remove(rem.getName());
            return rem;
        }
        else return null;
    }

    public void clear(){
        attackerList.clear();
        counter.clear();
    }

    public AttackerList clone(){
//		AttackerList clone = (AttackerList) super.clone();
        return new AttackerList(new PriorityQueue<>(attackerList), new TreeMap<>(counter));
    }

//	public String toString(){
//		return attackerList.toString();
//	}

    public String toString(){
        StringBuilder output = new StringBuilder();
        for(Map.Entry<String, Integer> entry : counter.entrySet()){
            output.append(entry.getValue()).append(" ").append(entry.getKey()).append("(s), ");
        }
        if(output.length() >= 2)
            return output.substring(0, output.length() - 2);
        else return "no units";
    }

    public int attack(){
        int res = 0;
        for(Attacker a : attackerList){
            if(!a.getName().equals("Submarine"))
                if(a.rollDie())
                    res++;
        }
        return res;
    }

    public int beachHead(){
        int res = 0;
        while(attackerList.size() != 0 && attackerList.peek().getName().equals("Battleship")){
            if(killAttacker().rollDie())
                res++;
        }
        return res;
    }

    public int subsFire(){
        int res = 0;
        for(Attacker a : attackerList){
            if(a.getName().equals("Submarine")){
                if(a.rollDie())
                    res++;
            }
        }
        return res;
    }

//	public Attacker peek(){
//		return attackerList.peek();
//	}

    public void killFighter(){
        Queue<Attacker> temp = new PriorityQueue<>();
//		boolean found = false;
        while(attackerList.size() != 0){
            Attacker rem = attackerList.remove();
            if(rem.getName().equals("Fighter")){
                rem.setAttackRating(0);
                temp.add(rem);
//				found = true;
                while(temp.size() != 0){
                    attackerList.add(temp.remove());
                }
                killAttacker();
                return;
            }
            else temp.add(rem);
        }
        while(temp.size() != 0){
            attackerList.add(temp.remove());
        }
    }

    public void killBomber(){
        Queue<Attacker> temp = new PriorityQueue<>();
//		boolean found = false;
        while(attackerList.size() != 0){
            Attacker rem = attackerList.remove();
            if(rem.getName().equals("Bomber")){
                rem.setAttackRating(0);
                temp.add(rem);
//				found = true;
                while(temp.size() != 0){
                    attackerList.add(temp.remove());
                }
                killAttacker();
                return;
            }
            else temp.add(rem);
        }
        while(temp.size() != 0){
            attackerList.add(temp.remove());
        }
    }

    public int getNumFighters(){
        return counter.getOrDefault("Fighter", 0);
    }

    public int getNumBombers(){
        return counter.getOrDefault("Bomber", 0);
    }

//	public String unitCount(){
//		Map<String,Integer> counter = new TreeMap<>();
//		for(Attacker attacker : attackerList){
//			String a = attacker.getName();
//			if(counter.containsKey(a)){
//				counter.put(a, counter.get(a) + 1);
//			}
//			else{
//				counter.put(a, 1);
//			}
//		}
//
//		StringBuilder output = new StringBuilder();
//		for(Map.Entry<String, Integer> entry : counter.entrySet()){
//			output.append(entry.getValue()).append(" ").append(entry.getKey()).append("(s), ");
//		}
//		return output.substring(0, output.length() - 2);
//	}
}