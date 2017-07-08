package CSE360;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;
/*
 * Lin Sun
 * Kyle Sun
 * Jingyi Li
 */
public class ExamBrain extends Observable {
	private ArrayList<Question> questionList;
	private ArrayList<Answer> answerListA; // all the A answers
	private ArrayList<Answer> answerListB; // all the B answers
	private ArrayList<Answer> answerListC; // all the C answers
	private ArrayList<Answer> answerListD; // all the D answers
	private int[] selectionList; //answer of the user
	private int[] correctAnswer; //correct answer; 1-A, 2-B, 3-C, 4-D
	private int count;
	private long[] lastElaspsedTime;  // keep track of the last action time.
	private boolean[] correctorNot;
	private static ExamBrain instance;
	
	public static ExamBrain getInstance() throws FileNotFoundException{
		if (instance == null) {
		       instance = new ExamBrain();
		     }
		     return instance;
	}
	public ExamBrain() throws FileNotFoundException
	{
		BlackBoard bb = new BlackBoard();
		this.addObserver(bb);
		//selectionList while it is zero, no attempt. 1,2,3,4, in representative of A,B,C,D
		correctAnswer = new int[10];
		selectionList = new int[10]; 
		lastElaspsedTime = new long[10];
		correctorNot = new boolean[10];
		
		//instantiate 
		questionList = new ArrayList<Question>();
		answerListA = new ArrayList<Answer>();
		answerListB = new ArrayList<Answer>();
		answerListC = new ArrayList<Answer>();
		answerListD = new ArrayList<Answer>();
		int answerIndex = -1;
		correctAnswer = new int[10];

		int index1 = 0;
		//read all the questions into the questionList ArrayList
		Scanner scanner = new Scanner(new File("src/CSE360/QandA/CSE360QuizData.csv"));
        scanner.useDelimiter(",");
        String temp = new String("Test");
        if (scanner.hasNext())
        	temp = scanner.next();
        if (scanner.hasNext())
        	temp = scanner.next();
        
        // to skip the first two values 
        //to do-- add icon in to the question and answer object
        while(scanner.hasNext()){
        	index1 ++;
        	temp = scanner.next();
        	
        	if(index1 == 1)
        	{
        		if (!questionList.isEmpty())
            	questionList.get(questionList.size()-1).setIcon(temp);

        	}
        	
        	if (index1 == 2) // A txt
        	{
            	Answer tempAns = new Answer(temp);
            	answerListA.add(tempAns);
        	}
        	
        	if (index1 == 3) // A img
        	{
            	answerListA.get(answerListA.size()-1).setIcon(temp);
        	}
        	
        	if (index1 == 4)// B txt
        	{
            	Answer tempAns = new Answer(temp);
            	answerListB.add(tempAns);
        	}
        	
        	if (index1 == 5)// B img
        	{
            	answerListB.get(answerListB.size()-1).setIcon(temp);
        	}
        	
        	if (index1 == 6)// C txt
        	{
            	Answer tempAns = new Answer(temp);
            	answerListC.add(tempAns);
        	}
        	
        	if (index1 == 7)// C img
        	{
            	answerListC.get(answerListC.size()-1).setIcon(temp);

        	}
        	
        	if (index1 == 8)// D txt
        	{
            	Answer tempAns = new Answer(temp);
            	answerListD.add(tempAns);
        	}
        	
        	if (index1 == 9)// D img
        	{
            	answerListD.get(answerListD.size()-1).setIcon(temp);

        	}
        	
        	if (index1 == 10)// correct ansewr
        	{
        		if(temp.equals("A"))
        			correctAnswer[++answerIndex] = 1; 
        		if(temp.equals("B"))
        			correctAnswer[++answerIndex] = 2; 
        		if(temp.equals("C"))
        			correctAnswer[++answerIndex] = 3; 
        		if(temp.equals("D"))
        			correctAnswer[++answerIndex] = 4; 
        	}
        	
        	if (index1 == 12 )
        	{
        		Question newQuestion = new Question(temp);
        		//temp = scanner.next();
        		//newQuestion.setIcon(temp);
        		questionList.add(newQuestion);
        		index1 = 0;
        	}
        }
        scanner.close();	
	}
	//return all the questions in an ArrayList
	public ArrayList<Question> getQuestionList()
	{
		return questionList;
	}
	
	public ArrayList<Answer> getAnswerList(int a)
	{
		ArrayList<Answer> result = new ArrayList<Answer>();
		switch (a){
		case 1:
		result = answerListA;
		result.remove(0); // since the first values is not useful 
		break;
		case 2:
		result = answerListB;
		result.remove(0); // since the first values is not useful 
		break;
		case 3:
			result = answerListC;
			result.remove(0); // since the first values is not useful 
			break;
		case 4:
			result = answerListD;
			result.remove(0); // since the first values is not useful 
			break;
		}
		return result;
	}
	public void setSelectionList(int i,int selected){
		selectionList[i]=selected;
		setChanged();
	}
	public void setCorrectAnswer(int[] correctAnswer){
		this.correctAnswer=correctAnswer;
		setChanged();
	}
	public void setlastElaspsedTime(int i,long lastElaspsedTime){
		this.lastElaspsedTime[i]=lastElaspsedTime;
		setChanged();
	}
	
	public void setCorrectorNot(int[]ansList,int selected, int num) {
		if(ansList[num]==selected){
			correctorNot[num]=true;
		}
		else{
			correctorNot[num]=false;
		}
		setChanged();
	}
	public boolean[] getCorrectorNot() {
		return correctorNot;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
		setChanged();
	}
	public int[] getSelectionList(){
		return selectionList;
	}
	
	public int[] getCorrectAnswer(){
		return correctAnswer;
	}
	public long[] getlastElaspsedTime(){
		return lastElaspsedTime;
	}
	//set answered question
	public void updateResult(int num, int selected, long tm)
	{
        System.out.println("New selection made");
		//selectionList[num-1] = selected;
        setSelectionList(num-1,selected);
		//lastElaspsedTime[num-1] = tm/1000;
        setlastElaspsedTime(num-1,tm/1000);
        setCorrectorNot(correctAnswer,selected,num-1);
        for (int i =0; i< selectionList.length; i++ )
        {
        	System.out.println("Answer: " + selectionList[i]);
        	System.out.println("Last log Time: " + lastElaspsedTime[i]);

        }
        count++;
        this.notifyObservers();
	}
	
}
