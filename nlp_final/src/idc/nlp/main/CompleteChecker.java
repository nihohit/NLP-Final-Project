package idc.nlp.main;

public class CompleteChecker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=1 ; i <= 10 ; i++)
		{
			Trainer.setConstraints(i);
			Evaluator.main(args);
		}
	}

}
