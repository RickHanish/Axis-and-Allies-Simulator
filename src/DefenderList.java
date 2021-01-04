import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class DefenderList{
    private Queue<Defender> defenderList;
    private Map<String, Integer> counter;

    public DefenderList(){
        defenderList = new PriorityQueue<>();
        counter = new TreeMap<>();
    }

    public DefenderList(PriorityQueue<Defender> pq, TreeMap<String, Integer> tm){
        defenderList = pq;
        counter = tm;
    }

    public void addDefender(Defender d){
        defenderList.add(d);
//		attackerList.add(a);
        if(counter.containsKey(d.getName())){
            counter.put(d.getName(), counter.get(d.getName()) + 1);
        }
        else counter.put(d.getName(), 1);
    }

    public void killDefender(){
        if(defenderList.size() != 0){
//			assert defenderList.size() != 0;
            Defender rem = defenderList.remove();
            rem.makeNotCasualty();
            decrementCounter(rem.getName());
        }
//		return defenderList.remove();
    }

    public int size(){
        return defenderList.size();
    }

    public void casualtyDefender(){
        Queue<Defender> temp = new PriorityQueue<>();
        boolean found = false;
        while(defenderList.size() != 0 && !found){
            Defender rem = defenderList.remove();
            if(!rem.isCasualty()){
                rem.makeCasualty();
                temp.add(rem);
//				decrementCounter(rem.getName());
                found = true;
                while(temp.size() != 0){
                    defenderList.add(temp.remove());
                }
//				return;
            }
            else{
                temp.add(rem);
            }
        }
        while(temp.size() != 0)
            defenderList.add(temp.remove());
    }

    private void decrementCounter(String s){
        counter.put(s, counter.get(s) - 1);
        if(counter.get(s) == 0)
            counter.remove(s);
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        for(Map.Entry<String, Integer> entry : counter.entrySet()){
            output.append(entry.getValue()).append(" ").append(entry.getKey()).append("(s), ");
        }
        if(output.length() >= 2)
            return output.substring(0, output.length() - 2);
        else return "no units";
    }

    public int defend(){
        int res = 0;
        for(Defender d : defenderList){
            if(d.rollDie())
                res++;
        }
        return res;
    }

    public int fireAA(int numPlanes){
        int res = 0;
        for(int i = 0; i < numPlanes; i++){
            if(defenderList.size() != 0 && defenderList.peek().getName().equals("Anti-Aircraft")){
//				assert defenderList.peek() != null;
                if(defenderList.peek() != null && defenderList.peek().rollDie())
                    res++;
            }
        }
        return res;

//		int res = 0;
//		if(defenderList.size() != 0 && defenderList.peek().getName().equals("Anti-Aircraft")){
//			if(defenderList.remove().rollDie())
//				res++;
//		}
//		return res;
    }

    public void clear(){
        defenderList.clear();
        counter.clear();
    }

    public DefenderList clone(){
        return new DefenderList(new PriorityQueue<>(defenderList), new TreeMap<>(counter));
    }

    public Defender peek(){
        return defenderList.peek();
    }

    public void removeCasualties(){
        Queue<Defender> temp = new PriorityQueue<>();
        while(defenderList.size() != 0){
            Defender rem = defenderList.remove();
            if(rem.isCasualty()){
                rem.makeNotCasualty();
                decrementCounter(rem.getName());
            }
            else temp.add(rem);
        }
        while(temp.size() != 0)
            defenderList.add(temp.remove());
    }

//	public String unitCount(){
//		Map<String,Integer> counter = new TreeMap<>();
//		for(Defender defender : defenderList){
//			String a = defender.getName();
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
//
//	}
}