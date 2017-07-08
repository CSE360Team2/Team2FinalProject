package CSE360;

import java.util.Observable;
import java.util.Observer;
import java.util.*;
/*
CSE360 Summer 2017
Kyle Sun
Jingyi Li
Lin Sun
*/
public class BlackBoard extends Observable implements Observer {
	private boolean[] correctorNot= new boolean[10];
	private long[] time = new long[10];
	private int[] selectionList;
	private int[] answerList;
	private int count;
	private int numcorrect;
	private static BlackBoard instance;
	CompanionBrain cb = new CompanionBrain();
	public BlackBoard(){
		this.addObserver(cb);
	}
	
	public static BlackBoard getInstance(){
		if(instance==null){
			instance=new BlackBoard();
		}
		return instance;
	}
	/*getter and setter for passing value to CompanionBrain*/
	public void setSelectionList(int[] selectionList){
		this.selectionList=selectionList;
		setChanged();
	}
	public void setAnswerList(int[] answerList){
		this.answerList=answerList;
		setChanged();
	}
	public void setTime(long[] time){
		this.time=time;
		setChanged();
	}
	public void setCorrectorNot(boolean n,int i){
		this.correctorNot[i]=n;
		setChanged();
	}
	public void setNumcorrect() {
		for(int i=0; i<correctorNot.length;i++){
			if(correctorNot[i]==true){
				numcorrect++;
			}
		}
		setChanged();
	}
	public boolean[] getCorrectorNot(){
		return correctorNot;
	}
	
	public long[] getTime(){
		return time;
	}
	

	//get how many question answered
	public int getQuesAnswered() {
		return count;
	}
	public int getNumcorrect() {
		return numcorrect;
	}
	
	@Override
	/*Update time,correctness from ExamBrain*/
	public void update(Observable o, Object arg) {
		System.out.println("update called");
		setTime(((ExamBrain)o).getlastElaspsedTime());
		setAnswerList(((ExamBrain)o).getSelectionList());
		setAnswerList(((ExamBrain)o).getCorrectAnswer());
		for(int i = 0; i<correctorNot.length;i++){
			if(selectionList[i]==answerList[i]){
				setCorrectorNot(true,i);
			}
			else{
				setCorrectorNot(false,i);
			}
			count++;//increase the # of question answered. 
			setNumcorrect();
		}
		
	}
}
