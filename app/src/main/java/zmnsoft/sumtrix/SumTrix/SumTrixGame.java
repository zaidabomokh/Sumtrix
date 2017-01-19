package zmnsoft.sumtrix.SumTrix;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SumTrixGame
{
	private myInteger[][] myBoard;
	private myInteger[] myStore;
	private final int size;
	private int timesUses;
	private Date start;
	private Date finish;
	private int explodes;
	private ArrayList<myInteger> emptiesButtons;
	private ArrayList<myInteger> occupiedButtons;

	public SumTrixGame(int size)
	{
		this.setExplodes(2 * size);
		this.size = size;
		this.myBoard = new myInteger[size][size];
		this.myStore = new myInteger[size];
		this.timesUses = 0;
		start = new Date();
		this.emptiesButtons = new ArrayList<myInteger>();
		this.occupiedButtons = new ArrayList<myInteger>();
		buildBoard();
	}

	public int[] streakHelp()
	{
		if(explodes == 0)
			return null;

		int[] res = new int[2];
		int index = 0;
		index = tryHelpStreak(res, index, myStore[0].getInteger());
		if(index == -1)
			return null;

		else
		{
			int x = res[0];
			int y = res[1];
			int count = 0;
			ArrayList<int[]> places = new ArrayList<int[]>();

			for (int i = x-1; i <= x+1; i++)
				for (int j = y-1; j <= y+1; j++)
					if(i >= 0 && i < myBoard.length && j >= 0 && j < myBoard.length)
						if(myBoard[i][j].getInteger() == -1)
						{
							int[] coordinates = {i, j};
							places.add(coordinates);
							count ++;
						}

			if(count < index)
				return null;

			int[] indexes = new int[index];
			for (int i = 0; i < indexes.length; i++)
				indexes[i] = -1;

			findPlaces(places, indexes, x, y, 0, 0);
			return indexes;
		}
	}

	private boolean findPlaces(ArrayList<int[]> places, int[] indexes, int x, int y, int i, int j) {
		return false;
	}

	public int tryHelpStreak(int[] res, int index, int acc)
	{
		if(index > size - 1)
			return -1;

		for (myInteger integer : emptiesButtons)
		{
			if(check(acc, integer.getX(), integer.getY()))
			{
				res[0] = integer.getX();
				res[1] = integer.getY();
				return index + 1;
			}
		}

		return tryHelpStreak(res, index + 1, (acc + myStore[index + 1].getInteger()) % 10);
	}

	private void buildBoard()
	{
		Random rand = new Random();
		myStore[0] = new myInteger(rand.nextInt(10));

		for (int i = 0; i < myBoard.length; i++)
		{
			myStore[i] = new myInteger(rand.nextInt(10));

			for (int j = 0; j < myBoard.length; j++)
			{

				if(i == 0 || i == myBoard.length - 1 || j == 0 || j == myBoard.length - 1) {
					myBoard[i][j] = new myInteger(-1, i, j);
					emptiesButtons.add(myBoard[i][j]);
				}

				else {
					myBoard[i][j] = new myInteger(rand.nextInt(10), i, j);
					occupiedButtons.add(myBoard[i][j]);
				}
			}
		}

		myStore[size - 1] = new myInteger(rand.nextInt(10));
	}

	public boolean InsertNumber(int x, int y)
	{
		int number = getNext();
		myBoard[x][y].setInteger(number);
		occupiedButtons.add(myBoard[x][y]);
		emptiesButtons.remove(myBoard[x][y]);

		if(check(number, x, y))
			delete(x, y);

		if(emptiesButtons.isEmpty())
			finish = new Date();

		return emptiesButtons.size() == size * size;
	}

	private void delete(int x, int y)
	{
		for (int i = x-1; i <= x+1; i++)
			for (int j = y-1; j <= y+1; j++)
				if(i >= 0 && i < myBoard.length && j >= 0 && j < myBoard.length) {
					if(myBoard[i][j].getInteger() != -1) {
						myBoard[i][j].setInteger(-1);
						occupiedButtons.remove(myBoard[i][j]);
						emptiesButtons.add(myBoard[i][j]);
					}
				}
	}

	private boolean check(int number, int x, int y)
	{
		int sum = 0;
		int occupied = -1;
		int empties = 0;

		for (int i = x-1; i <= x+1; i++)
			for (int j = y-1; j <= y+1; j++)
				if(i >= 0 && i < myBoard.length && j >= 0 && j < myBoard.length)
				{
					if(myBoard[i][j].getInteger() != -1)
					{
						sum += myBoard[i][j].getInteger();
						occupied ++;
					}
					else empties ++;
				}

		if(myBoard[x][y].getInteger() != -1)
			sum -= myBoard[x][y].getInteger();

		if(sum % 10 == number)
			if(number != 0)
				return true;
			else if(empties < occupied) return true;

		return false;
	}

	public int getNext()
	{
		timesUses ++;
		int returnedNumber = this.myStore[0].getInteger();

		for (int i = 0; i < myStore.length - 1; i++)
			myStore[i] = myStore[i+1];

		Random rand = new Random();
		myStore[myStore.length - 1] = new myInteger(rand.nextInt(10));

		return returnedNumber;
	}

	public void PrintBoard()
	{
		for (int i = 0; i < myBoard.length; i++)
		{
			for (int j = 0; j < myBoard.length; j++)
			{
				if(myBoard[i][j].getInteger() == -1)
					System.out.print("[ ] ");
				else System.out.print("[" + myBoard[i][j] + "] ");
			}

			System.out.println();
		}

		System.out.println("////////////////////////");
	}

	public void PrintNumbersStore()
	{
		for (int i = 0; i < myStore.length - 1; i++)
			System.out.print(myStore[i] + ", ");
		System.out.println(myStore[myStore.length - 1]);
	}

	public void printEmpties() {
		for (myInteger integer : emptiesButtons) {
			System.out.print(integer + ",");
		}
		System.out.println();
	}

	public void printOccupied() {
		for (myInteger integer : occupiedButtons) {
			System.out.print(integer + ",");
		}
		System.out.println();
	}

	public long getTimeElapsed() {
		//return (long) Math.abs((finishTime - startTime) / 1000);
		long duration  = finish.getTime() - start.getTime();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		return diffInSeconds;
	}

	public int getScore() {
		if(finish == null)
			finish = new Date();
		return (int) ((Math.pow(size, 2*size)) / (timesUses * getTimeElapsed()));
	}

	public int getTimesUses() {
		return timesUses;
	}

	public myInteger[][] getMyMatrix() {
		return myBoard;
	}

	public int getSize() {
		return size;
	}

	public myInteger[] getNumbersStore() {
		return myStore;
	}

	public boolean isStuck() {
		return emptiesButtons.isEmpty();
	}

	public ArrayList<myInteger> getEmptiesButtons() {
		return emptiesButtons;
	}

	public void setEmptiesButtons(ArrayList<myInteger> emptiesButtons) {
		this.emptiesButtons = emptiesButtons;
	}

	public ArrayList<myInteger> getOccupiedButtons() {
		return occupiedButtons;
	}

	public void setOccupiedButtons(ArrayList<myInteger> occupiedButtons) {
		this.occupiedButtons = occupiedButtons;
	}

	public int getExplodes() {
		return explodes;
	}

	public void setExplodes(int explodes) {
		this.explodes = explodes;
	}
}