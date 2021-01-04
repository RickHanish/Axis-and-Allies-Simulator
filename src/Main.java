import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main{
    private static boolean landBattle;
    private static int numSims;

    public static void main(String[] args){
//		StringBuilder output = new StringBuilder();
//		System.out.println(output.substring(0, output.length() - 2));
        AttackerList aList = new AttackerList();
        DefenderList dList = new DefenderList();
        List<Attacker> attackerData = loadAttackerData("Attacker Values.txt");
        List<Defender> defenderData = loadDefenderData("Defender Values.txt");
        System.out.println("Enter \"user\" if you would like to enter data yourself or \"file\" if you would like to input data from a preexisting file:");
        Scanner input = new Scanner(System.in);
        String in = input.nextLine().toLowerCase();
        boolean acceptable = false;
        while(!acceptable){
            switch (in){
                case "user" :
                    inputOtherData();
                    inputAttackerData(aList, attackerData);
                    inputDefenderData(dList, defenderData);
                    simulateCombat(aList, dList);
                    acceptable = true;
                    break;
                case "file" :
                    inputTestData(aList, dList, attackerData, defenderData);
                    acceptable = true;
                    break;
                default :
                    System.out.println("The data you entered is not acceptable. Please enter \"user\" or \"file\".");
            }
        }
//		inputTestData(aList, dList, attackerData, defenderData);
//		inputOtherData();
//		inputAttackerData(aList, attackerData);
//		inputDefenderData(dList, defenderData);
    }

    private static void inputTestData(AttackerList aList, DefenderList dList, List<Attacker> attackerData, List<Defender> defenderData){
        try{
            Scanner input = new Scanner(new File("Test Values.txt"));
            Scanner landScan = new Scanner(new File("Land Battles.txt"));
            Scanner seaScan = new Scanner(new File("Sea Battles.txt"));
            while(input.hasNextLine()){
                String land = input.nextLine();
                landBattle = land.equals("Land");
//			input.nextLine();
                numSims = input.nextInt();
                input.nextLine();
//		int aInfantry = input.nextInt();
                if(landBattle){
                    System.out.println(landScan.nextLine());
//				System.out.println("Enter the number of attacking infantry:");
                    enterAttackingUnits(aList, attackerData.get(0), landScan);
//				System.out.println("Enter the number of attacking armor");
                    enterAttackingUnits(aList, attackerData.get(1), landScan);
//				System.out.println("Enter the number of attacking fighters:");
                    enterAttackingUnits(aList, attackerData.get(2), landScan);
//				System.out.println("Enter the number of attacking bombers:");
                    enterAttackingUnits(aList, attackerData.get(3), landScan);
//				System.out.println("Enter the number of attacking battleships:");
                    enterAttackingUnits(aList, attackerData.get(5), landScan);
                } else{
                    System.out.println(seaScan.nextLine());
//					Scanner seaScan = new Scanner(new File("Sea Battles.txt"));
//				System.out.println("Enter the number of attacking transports:");
                    enterAttackingUnits(aList, attackerData.get(7), seaScan);
//				System.out.println("Enter the number of attacking submarines:");
                    enterAttackingUnits(aList, attackerData.get(8), seaScan);
//				System.out.println("Enter the number of attacking aircraft carriers:");
                    enterAttackingUnits(aList, attackerData.get(6), seaScan);
//				System.out.println("Enter the number of attacking fighters:");
                    enterAttackingUnits(aList, attackerData.get(2), seaScan);
//				System.out.println("Enter the number of attacking bombers:");
                    enterAttackingUnits(aList, attackerData.get(3), seaScan);
//				System.out.println("Enter the number of attacking battleships:");
                    enterAttackingUnits(aList, attackerData.get(5), seaScan);
                }
//			System.out.println("Enter the number of attacking fighters:");
//				enterAttackingUnits(aList, attackerData.get(2), input);
//			System.out.println("Enter the number of attacking bombers:");
//				enterAttackingUnits(aList, attackerData.get(3), input);
//			System.out.println("Enter the number of attacking battleships:");
//				enterAttackingUnits(aList, attackerData.get(5), input);
                System.out.println("The attacking army consists of: " + aList.toString());

//		Scanner input = new Scanner(System.in);
                if(landBattle){
//				System.out.println("Enter the number of defending infantry:");
                    enterDefendingUnits(dList, defenderData.get(0), landScan);
//				System.out.println("Enter the number of defending armor");
                    enterDefendingUnits(dList, defenderData.get(1), landScan);
//				System.out.println("Enter the number of defending fighters:");
                    enterDefendingUnits(dList, defenderData.get(2), landScan);
//				System.out.println("Enter the number of defending bombers:");
                    enterDefendingUnits(dList, defenderData.get(3), landScan);
//				System.out.println("Enter the number of defending anti-aircraft:");
                    enterDefendingUnits(dList, defenderData.get(4), landScan);
                } else{
//				System.out.println("Enter the number of defending transports:");
                    enterDefendingUnits(dList, defenderData.get(7), seaScan);
//				System.out.println("Enter the number of defending submarines:");
                    enterDefendingUnits(dList, defenderData.get(8), seaScan);
//				System.out.println("Enter the number of defending aircraft carriers:");
                    enterDefendingUnits(dList, defenderData.get(6), seaScan);
//				System.out.println("Enter the number of defending battleships:");
                    enterDefendingUnits(dList, defenderData.get(5), seaScan);
//				System.out.println("Enter the number of defending fighters:");
                    enterDefendingUnits(dList, defenderData.get(2), seaScan);
//				System.out.println("Enter the number of defending bombers:");
                    enterDefendingUnits(dList, defenderData.get(3), seaScan);
//					seaScan.nextLine();
                }
//			System.out.println("Enter the number of defending fighters:");
//				enterDefendingUnits(dList, defenderData.get(2), input);
//			System.out.println("Enter the number of defending bombers:");
//				enterDefendingUnits(dList, defenderData.get(3), input);
                System.out.println("The defending army consists of: " + dList.toString());
                simulateCombat(aList, dList);
                aList.clear();
                dList.clear();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void simulateCombat(AttackerList attackerList, DefenderList defenderList){
        int attackerWins = 0, defenderWins = 0, ties = 0;
        for(int j = 0; j < numSims; j++){
//			try{
            AttackerList aList = attackerList.clone();
            DefenderList dList = defenderList.clone();
            while(aList.size() != 0 && dList.size() != 0){
                if(landBattle){
                    //AA gun fires at each plane
                    while(dList.peek().getName().equals("Anti-Aircraft")){
                        int numAAFighters = dList.fireAA(aList.getNumFighters());
                        for(int i = 0; i < numAAFighters; i++){
                            aList.killFighter();
                        }
                        int numAABombers = dList.fireAA(aList.getNumBombers());
                        for(int i = 0; i < numAABombers; i++){
                            aList.killBomber();
                        }
                        dList.killDefender();
                    }

                    //Beachheads
                    int beachHeads = aList.beachHead();
                    for(int i = 0; i < beachHeads; i++){
                        dList.killDefender();
                    }

                    //Attacker fires each unit
                    int casualties = aList.attack();
                    for(int i = 0; i < casualties; i++){
                        dList.casualtyDefender();
                    }

                    //Defender fires each unit
                    int hits = dList.defend();
                    for(int i = 0; i < hits; i++){
                        aList.killAttacker();
                    }

                    //All casualties are removed
                    dList.removeCasualties();

                    //Tabulate results
//						if(aList.size() == 0)
//							defenderWins++;
//						if(dList.size() == 0)
//							attackerWins++;
                }
                else{
                    //Attacking subs fire
                    int subHits = aList.subsFire();
                    for(int i = 0; i < subHits; i++){
                        dList.killDefender();
                    }

                    //Attacker fires other units
                    int casualties = aList.attack();
                    for(int i = 0; i < casualties; i++){
                        dList.casualtyDefender();
                    }

                    //Defender fires remaining units
                    int hits = dList.defend();
                    for(int i = 0; i < hits; i++){
                        aList.killAttacker();

                    }


                    //All casualties are removed
                    dList.removeCasualties();

                    //Tabulate results
                }
            }
            if(aList.size() == 0 && dList.size() == 0)
                ties++;
            else if(aList.size() == 0)
                defenderWins++;
            else if(dList.size() == 0)
                attackerWins++;
//			}
//			catch(CloneNotSupportedException e){
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//				System.exit(0);
//			}
        }
        System.out.println("The attacker won " + attackerWins + " time(s), and the defender won " + defenderWins + " time(s). There were " + ties + " ties.\n");
    }

    public static boolean isLandBattle(){
        return landBattle;
    }

    private static List<Attacker> loadAttackerData(String fileName){
        List<Attacker> list = new LinkedList<>();
        try{
            Scanner input = new Scanner(new File(fileName));
            while(input.hasNextLine()){
                String name = input.nextLine();
                int attackRating = input.nextInt();
//				int defendRating = input.nextInt();
                input.nextLine();
                Attacker unit = new Attacker(name, attackRating);
                list.add(unit);
            }
            input.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error reading " + fileName);
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        return list;

    }

    private static List<Defender> loadDefenderData(String fileName){
        List<Defender> list = new LinkedList<>();
        try{
            Scanner input = new Scanner(new File(fileName));
            while(input.hasNextLine()){
                String name = input.nextLine();
                int defendRating = input.nextInt();
//				int defendRating = input.nextInt();
                input.nextLine();
                Defender unit = new Defender(name, defendRating, false);
                list.add(unit);
            }
            input.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error reading " + fileName);
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        return list;

    }

    private static void inputOtherData(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter \"Land\" if in land battle or \"Sea\" if in naval battle.");
        boolean acceptable = false;
        while(!acceptable){
            String battleType = input.nextLine();
            switch(battleType.toLowerCase()){
                case "land":
                    landBattle = true;
                    acceptable = true;
                    break;
                case "sea":
                    landBattle = false;
                    acceptable = true;
                    break;
                default:
                    landBattle = true;
                    System.out.println("Not an acceptable input. Please enter \"Land\" or \"Sea\"");
            }
        }
        System.out.println("How many times would you like to simulate the combat?");
        numSims = input.nextInt();
    }

    private static void inputAttackerData(AttackerList aList, List<Attacker> unitData){
        Scanner input = new Scanner(System.in);
        if(landBattle){
            System.out.println("Enter the number of attacking infantry:");
            enterAttackingUnits(aList, unitData.get(0), input);
            System.out.println("Enter the number of attacking armor");
            enterAttackingUnits(aList, unitData.get(1), input);
        }
        else{
            System.out.println("Enter the number of attacking transports:");
            enterAttackingUnits(aList, unitData.get(7), input);
            System.out.println("Enter the number of attacking submarines:");
            enterAttackingUnits(aList, unitData.get(8), input);
            System.out.println("Enter the number of attacking aircraft carriers:");
            enterAttackingUnits(aList, unitData.get(6), input);
        }
        System.out.println("Enter the number of attacking fighters:");
        enterAttackingUnits(aList, unitData.get(2), input);
        System.out.println("Enter the number of attacking bombers:");
        enterAttackingUnits(aList, unitData.get(3), input);
        System.out.println("Enter the number of attacking battleships:");
        enterAttackingUnits(aList, unitData.get(5), input);
//		System.out.println("The attacking army consists of: " + aList.toString());
    }

    private static void inputDefenderData(DefenderList dList, List<Defender> unitData){
        Scanner input = new Scanner(System.in);
        if(landBattle){
            System.out.println("Enter the number of defending infantry:");
            enterDefendingUnits(dList, unitData.get(0), input);
            System.out.println("Enter the number of defending armor");
            enterDefendingUnits(dList, unitData.get(1), input);
            System.out.println("Enter the number of defending anti-aircraft:");
            enterDefendingUnits(dList, unitData.get(4), input);
        }
        else{
            System.out.println("Enter the number of defending transports:");
            enterDefendingUnits(dList, unitData.get(7), input);
            System.out.println("Enter the number of defending submarines:");
            enterDefendingUnits(dList, unitData.get(8), input);
            System.out.println("Enter the number of defending aircraft carriers:");
            enterDefendingUnits(dList, unitData.get(6), input);
        }
        System.out.println("Enter the number of defending fighters:");
        enterDefendingUnits(dList, unitData.get(2), input);
        System.out.println("Enter the number of defending bombers:");
        enterDefendingUnits(dList, unitData.get(3), input);
//		System.out.println("The defending army consists of: " + dList.unitCount());
    }

    private static void enterAttackingUnits(AttackerList list, Attacker unit, Scanner input){
        int numUnits = input.nextInt();
        for(int i = 0; i < numUnits; i++){
            list.addAttacker(new Attacker(unit.getName(), unit.getAttackRating()));
        }
        input.nextLine();
    }

    private static void enterDefendingUnits(DefenderList list, Defender unit, Scanner input){
        int numUnits = input.nextInt();
        for(int i = 0; i < numUnits; i++){
            list.addDefender(new Defender(unit.getName(), unit.getDefendRating(), false));
        }
        input.nextLine();
    }
}