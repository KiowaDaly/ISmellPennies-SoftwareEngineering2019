import ExcessiveCoupling.FeatureEnvy;
import utility_classes.ThreatLevel;

public class test{
	private int number;
	public test2 t2;
	public test(int number){

		this.number = number;
	}
	protected int getNumber(){
		int x = 44;
		number++;
		t2.toString();
		return number;
		//comment 1
		//comment 2
		//comment 3
	}
	private double random(){
		//1 comment
		return 0;
	}

	public boolean isPrime(int number){
		//some filler code
		if(number%2 == 0){	//check if even.
			return false;
		}
		if(number==1){
			return false;
		}

		switch(number){
			case 2:{
				int x = 5;
				int y = x*2;
				return true;}
			case 3:{return true;}
			case 5:{return true;}
			case 7:{return true;}
			case 11:{return true;}
			default:{return false;}
		}
	}

}