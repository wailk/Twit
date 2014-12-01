package services;

public class Operation {
	public double Calcul(double a, double b, String op){
		if(op.compareTo("add")== 0)
				return a+b; 
		else if(op.compareTo("mult")== 0)
			return a*b; 
		else if(op.compareTo("div")== 0)
			return a/b;
		else
			return 0;
		}
}
