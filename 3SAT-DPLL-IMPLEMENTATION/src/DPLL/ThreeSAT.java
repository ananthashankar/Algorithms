package DPLL;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;


public class ThreeSAT {

	static int assignedCount = 0;
	static ArrayList<int[]> sats;
	static int firstElementChange = 0;
	static int backTrackedCount = 0;
	static int failedPoint = -1;
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		System.out.println("Please Enter The File Path to Evaluate");
		if(in.hasNext()){
			getCustomizedSat(in.next());
		}
		System.out.println();
		assignedCount = 0;
		firstElementChange = 0;
		backTrackedCount = 0;
		failedPoint = -1;
		sats = new ArrayList<int[]>();
		
		// Number of Variables V
		int V = 0;
		
		// Number of Clauses T
		int T = 0;
		
		System.out.println("Please Enter Number of Variables To Be Created: ");
		if(in.hasNext()){
			V = Integer.parseInt(in.next());
		}
		
		// Variable List of Size 2V
		int[] vList = new int[2*V];
		for(int i=0; i<vList.length; i++){
			vList[i] = -1;
		}
		
		System.out.println("Please Enter Number of Clauses To Be Created: ");
		if(in.hasNext()){
			T = Integer.parseInt(in.next());
		}

		in.close();
		
		Predicate pred = new Predicate(T);
		pred.createRandomClauses(vList);
		isSAT(pred, vList);
		
	}
	
	// Back Tracks all for variable assignment and outputs results
	public static void isSAT(Predicate pred, int[] vList){
		
		Stack<Integer> assignedVariables = new Stack<Integer>();
		boolean satisfiability = false;
		while(assignedCount<vList.length/2){
			if(pred.isContainsVariable(assignedCount+1, vList.length/2) == 0){
				vList[assignedCount] = 0;
				vList[assignedCount+vList.length/2] = 1;
				assignedCount++;
			} else {
				failedPoint = assignedCount+1;
				satisfiability = assignVariableAndEvaluatePred(pred, vList, assignedVariables);
				
				if(!satisfiability){
					break;
				}
			}
		}
		
		if(satisfiability){
			
			sats.add(vList);
			
			getAllSat(pred, vList);
			
			System.out.println("Satisfied Assigned Values for Variables:");
			for(int[] arr: sats){
				for(int i=0; i<vList.length/2; i++){
					System.out.print(arr[i] + " ");
				}
				System.out.println();
			}
			
			System.out.println("Satisfied Clauses:");
			for(int i : pred.clauses.keySet()){
				Clause c = pred.clauses.get(i);
				if(i==pred.clauses.size()){
					System.out.print("(" + c.getL1().value + " V " + c.getL2().value + " V " + c.getL3().value + " )");
				} else {
					System.out.print("(" + c.getL1().value + " V " + c.getL2().value + " V " + c.getL3().value + " ) ^ ");
				}
			}
			System.out.println();
			
		} else {
			System.out.println("No Possible Variable Values for Satisfiability");
			
			System.out.println("UnSatisfied Variable: " + failedPoint);
			
			System.out.println("Backtracked Node Count: " + backTrackedCount);
			
			System.out.println("UnSatisfied Clauses:");
			for(int i : pred.clauses.keySet()){
				Clause c = pred.clauses.get(i);
				if(i==pred.clauses.size()){
					System.out.print("(" + c.getL1().value + " V " + c.getL2().value + " V " + c.getL3().value + ")");
				} else {
					System.out.print("(" + c.getL1().value + " V " + c.getL2().value + " V " + c.getL3().value + ") ^ ");
				}
			}
		}
		
	}
	
	
	//Davis–Putnam–Logemann–Loveland (DPLL) algorithm with Time Complexity = O(2^n) and Space Complexity = O(n)
	//Recursively Backtracks when a variable is unsatisfied
	public static boolean assignVariableAndEvaluatePred(Predicate pred, int[] vList,  
			Stack<Integer> assignedVariables){
		// Base Case for Root/Starting Variable
		if(assignedVariables.size()==0){
			// Result tried for both 1 and 0 already non satisfiable result
			if(firstElementChange >= 2){
				return false;
			} else {
				firstElementChange++;
			}
			vList[assignedCount] = vList[assignedCount] == 0 ? 1 : 0;
			vList[assignedCount+(vList.length/2)] = vList[assignedCount] == 0 ? 1 : 0; 
			pred.updateClauses(assignedCount+1, vList[assignedCount],  vList.length/2);
			if(pred.isSatisfiable()){
				assignedVariables.push(assignedCount);
				assignedCount++;
				return true;
			} else {
				vList[assignedCount] = vList[assignedCount] == 0 ? 1 : 0;
				vList[assignedCount+(vList.length/2)] = vList[assignedCount] == 0 ? 1 : 0;
				pred.updateClauses(assignedCount+1, vList[assignedCount], vList.length/2);
				if(pred.isSatisfiable()){
					assignedVariables.push(assignedCount);
					assignedCount++;
					return true;
				} else {
					return false;
				}
			}
		} else {
			vList[assignedCount] = vList[assignedCount] == 0 ? 1 : 0;
			vList[assignedCount+(vList.length/2)] = vList[assignedCount] == 0 ? 1 : 0;
			pred.updateClauses(assignedCount+1, vList[assignedCount], vList.length/2);
			if(pred.isSatisfiable()){
				assignedVariables.push(assignedCount);
				assignedCount++;
				return true;
			} else {
				vList[assignedCount] = vList[assignedCount] == 0 ? 1 : 0;
				vList[assignedCount+(vList.length/2)] = vList[assignedCount] == 0 ? 1 : 0;
				pred.updateClauses(assignedCount+1, vList[assignedCount], vList.length/2);
				if(pred.isSatisfiable()){
					assignedVariables.push(assignedCount);
					assignedCount++;
					return true;
				} else {
					if(assignedVariables.size()>0){
						assignedVariables.pop();
						vList[assignedCount] = -1;
						vList[assignedCount+(vList.length/2)] = -1;
						assignedCount--;
						backTrackedCount++;
						return assignVariableAndEvaluatePred(pred, vList, assignedVariables);
					}
					return false;
				}
			}
		}
	}
	
	// Retrieves all satisfied values for vList with Time Complexity = O(n*2^n) and Space Complexity = O(n)
	public static void getAllSat(Predicate pred, int[] vList){	
		HashSet<String> set = new HashSet<String>();
		set.add(Arrays.toString(vList));
		if(assignedCount >= vList.length/2){
			for(int i=0; i<vList.length/2; i++){
				Stack<Integer> assignedVariables = new Stack<Integer>();
				for(int j=0; j<i;j++){assignedVariables.push(vList[j]);}
				assignedCount = i;
				int[] tempList = new int[vList.length];
				for(int k=0; k<tempList.length; k++){tempList[k] = vList[k];};
				while(assignedCount < tempList.length/2){
					if(vList[assignedCount] == 1 || pred.isContainsVariable(assignedCount+1, tempList.length/2) == 0){
						assignedCount++;
					} else {
						boolean satis = assignVariableAndEvaluatePred(pred, tempList, assignedVariables);
						if(satis && !set.contains(Arrays.toString(tempList))){
							int[] temp = new int[tempList.length];
							set.add(Arrays.toString(tempList));
							for(int k=0; k<tempList.length/2; k++){temp[k] = tempList[k];}
							sats.add(temp);
						}
					}
				}
			}
		}		
	}
	
	//eg:- UnSat->(x ∨ y ∨ z)∧(x ∨ y ∨ ¬z)∧(x ∨ ¬y ∨ z)∧(x ∨ ¬y ∨ ¬z)∧(¬x ∨ y ∨ z)∧(¬x ∨ y ∨ ¬z)∧(¬x ∨ ¬y ∨ z)∧(¬x ∨ ¬y ∨ ¬z)
	//eg:- UnSat->(1 ∨ 2 ∨ 3)∧(1 ∨ 2 ∨ 6)∧(1 ∨ 5 ∨ 3)∧(1 ∨ 5 ∨ 6)∧(4 ∨ 2 ∨ 3)∧(4 ∨ 2 ∨ 6)∧(4 ∨ 5 ∨ 3)∧(4 ∨ 5 ∨ 6)
	public static void getCustomizedSat(String filePath){
		
		sats = new ArrayList<int[]>();
		int V = 0;
		int T = 0;
		
		File file = new File(filePath);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    String[] vals = new String[3];
		    int i = 0;
		    while ((line = br.readLine()) != null) {
		       vals[i] = line; i++;
		    }
		    br.close();
		    V = Integer.parseInt(vals[0]); T = Integer.parseInt(vals[1]);
		    int[] vList = new int[2*V];
			for(i=0; i<vList.length; i++){
				vList[i] = -1;
			}
			Predicate pred = new Predicate(T);
			String[] lits = vals[2].split("\\s+");
			i=0;
			while(T>0){
				pred.clauses.get(T).setL1(Integer.parseInt(lits[i]));
				pred.clauses.get(T).setL2(Integer.parseInt(lits[i+1]));
				pred.clauses.get(T).setL3(Integer.parseInt(lits[i+2]));
				T--;
				i += 3;
			}
			isSAT(pred, vList);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
