public class test{
	public int number;
	public test2 t2;

	public test(int number){

		this.number = number;
	}
	public int getNumber(){
		int x = 44;
		number++;
		random();
		isPrime(3);
		t2.toString();
		float f = t2.fl;
		int myNum = t2.integer;


		return number;
		//comment 1
		//comment 2
		//comment 3
	}
	public String testing(){
		return t2.toString();
	}
	private double random(){
		//1 comment
		test gg = t2.t;
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