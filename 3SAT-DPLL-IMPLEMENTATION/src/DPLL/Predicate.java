package DPLL;

import java.util.HashMap;
import java.util.Random;

public class Predicate {
	public HashMap<Integer, Clause> clauses;
	
	public Predicate(int T){
		clauses = new HashMap<Integer, Clause>();
		for(int i=1; i<=T; i++){
			clauses.put(i, new Clause());
		}
	}
	
	// generates random clauses
	public void createRandomClauses(int[] vList){
		int i = 1;
		while(i<=clauses.size()){
			Random random = new Random();
			Clause temp = clauses.get(i);
			temp.setL1(random.nextInt(vList.length)+1);
			random = new Random();
			temp.setL2(random.nextInt(vList.length)+1);
			random = new Random();
			temp.setL3(random.nextInt(vList.length)+1);
			i++;
		}
	}
	
	// checks whether each clause is true
	public boolean isSatisfiable(){
		for(int i : clauses.keySet()){
			if(!clauses.get(i).isTrue()){
				return false;
			}
		}
		return true;
	}
	
	// returns 1 if a variable exists in the predicate
	public int isContainsVariable(int val, int numOfVar){
		
		for(int i : clauses.keySet()){
			if(clauses.get(i).containsValue(val, numOfVar)){
				return 1;
			}
		}
		return 0;
	}
	
	// updates clause's literals with the given variable value
	public void updateClauses(int val, int bool, int numOfVar){
		for(int i : clauses.keySet()){
			if(clauses.get(i).containsValue(val, numOfVar)){
				clauses.get(i).updateLiteral(val, bool, numOfVar);
			}
		}
	}
	
}
