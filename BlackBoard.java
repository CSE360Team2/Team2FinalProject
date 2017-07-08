package CSE360;

import java.util.Observable;
import java.util.Observer;

/*
CSE360 Summer 2017
Kyle Sun
Jingyi Li
Lin Sun
*/
public class BlackBoard extends Observable implements Observer {
	private boolean[] correctorNot= new boolean[10];
	private long[] time = new long[10];
	private int count;
	private int numcorrect;
	/*getter and setter for passing value to CompanionBrain*/
	public boolean[] getCorrectorNot(){
		return correctorNot;
	}
	public void setCorrectorNot(boolean[] correctorNot){
		this.correctorNot=correctorNot;
		setChanged();
	}
	public long[] getTime(){
		return time;
	}
	public void setTime(long[] time){
		this.time=time;
		setChanged();
	}

	//get how many question answered
	public int getQuesAnswered() {
		return count;
	}
	public int getNumcorrect() {
		return numcorrect;
	}
	public void setNumcorrect() {
		for(int i=0; i<correctorNot.length;i++){
			if(correctorNot[i]==true){
				numcorrect++;
			}
		}
		setChanged();
	}
	@Override
	/*Update time,correctness from ExamBrain*/
	public void update(Observable o, Object arg) {
		time = ((ExamBrain)o).getlastElaspsedTime();
		int[] selectionList=((ExamBrain)o).getSelectionList();
		int[] answerList=((ExamBrain)o).getCorrectAnswer();
		for(int i = 0; i<correctorNot.length;i++){
			if(selectionList[i]==answerList[i]){
				correctorNot[i]=true;
			}
			count++;//increase the # of question answered. 
		}
		
	}
}
