package DPLL;

public class Clause {
	private Literal L1;
	private Literal L2;
	private Literal L3;
	
	public Clause(){
		L1 = new Literal();
		L2 = new Literal();
		L3 = new Literal();
	}
	
	public void setL1(int val){
		L1.value = val;
		L1.isTrue = true;
	}
	
	public void setL2(int val){
		L2.value = val;
		L2.isTrue = true;
	}
	
	public void setL3(int val){
		L3.value = val;
		L3.isTrue = true;
	}
	
	public Literal getL1(){
		return L1;
	}
	
	public Literal getL2(){
		return L2;
	}
	
	public Literal getL3(){
		return L3;
	}
	
	// returns true if the clause is true
	public boolean isTrue(){
		if(L1.isTrue || L2.isTrue || L3.isTrue){
			return true;
		}
		else{
			return false;
		}
	}
	
	// returns true if a contains a variable
	public boolean containsValue(int val, int numOfVar){
		if(L1.value == val || L2.value == val || L3.value == val){
			return true;
		} else if(L1.value == numOfVar+val || L2.value == numOfVar+val || L3.value == numOfVar+val){
			return true;
		}
		return false;
	}
	
	// updates the literal values
	public void updateLiteral(int literal,int bool, int numOfVar){
		if(literal == L1.value){
			L1.isTrue = bool == 1 ? true : false;
		} else if(literal+numOfVar == L1.value){
			L1.isTrue = bool == 1 ? false : true;
		}
		
		if(literal == L2.value){
			L2.isTrue = bool == 1 ? true : false;
		} else if(literal+numOfVar == L2.value){
			L2.isTrue = bool == 1 ? false : true;
		}
		
		if(literal == L3.value){
			L3.isTrue = bool == 1 ? true : false;
		} else if(literal+numOfVar == L3.value){
			L3.isTrue = bool == 1 ? false : true;
		}		
	}
}
