package zmnsoft.sumtrix.SumTrix;

import android.widget.Button;

import java.util.Random;
import java.util.ArrayList;

public class SumTrixGame{

	private final myButton[][] board;
	private final myButton[] store;
	private myInteger[][] myBoard;
	private myInteger[] myStore;
	private final int size;
	private int timesUses;
	private long startTime;
	private long finishTime;
	private int explodes;
	private ArrayList<myInteger> emptiesButtons;
	private ArrayList<myInteger> occupiedButtons;

	public SumTrixGame(int size, myButton[][] board, myButton[] store)
	{
		this.setExplodes(2 * size);
		this.size = size;
		this.myBoard = new myInteger[size][size];
		this.myStore = new myInteger[size];
		this.timesUses = 0;
		this.startTime = System.currentTimeMillis();
		this.emptiesButtons = new ArrayList<myInteger>();
		this.occupiedButtons = new ArrayList<myInteger>();
		this.board = board;
		this.store = store;
		buildBoard();
	}

	public int[] streakHelp()
	{
		if(explodes == 0)
			return null;

		int[] res = new int[2];
		myInteger index = new myInteger(0);

		if(!tryHelpStreak(res, index, myStore[0].getInteger()))
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

			if(count < index.getInteger())
				return null;

			return findPlaces(places, x, y, 0);
		}
	}

	private int[] findPlaces(ArrayList<int[]> places, int x, int y, int i)
	{
		return null;
	}

	public boolean tryHelpStreak(int[] res, myInteger index, int acc)
	{
		if(index.getInteger() > size - 1)
			return false;

		for (myInteger integer : emptiesButtons)
		{
			if(check(acc, integer.getX(), integer.getY()))
			{
				res[0] = integer.getX();
				res[1] = integer.getY();
				return true;
			}
		}

		index.increement();

		return tryHelpStreak(res, index, (acc + myStore[index.getInteger()].getInteger()) % 10);
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
		board[x][y].setText(String.valueOf(number));
		occupiedButtons.add(myBoard[x][y]);
		emptiesButtons.remove(myBoard[x][y]);

		if(check(number, x, y))
			delete(x, y);

		if(emptiesButtons.isEmpty())
			finishTime = System.currentTimeMillis();

		return emptiesButtons.isEmpty();
	}

	private void delete(int x, int y)
	{
		for (int i = x-1; i <= x+1; i++)
			for (int j = y-1; j <= y+1; j++)
				if(i >= 0 && i < myBoard.length && j >= 0 && j < myBoard.length) {
					if(myBoard[i][j].getInteger() != -1) {
						myBoard[i][j].setInteger(-1);
						board[i][j].setText(" ");
						occupiedButtons.remove(myBoard[i][j]);
						emptiesButtons.add(myBoard[i][j]);
					}
				}
	}

	private boolean check(int number, int x, int y)
	{
		int sum = 0;

		for (int i = x-1; i <= x+1; i++)
			for (int j = y-1; j <= y+1; j++)
				if(i >= 0 && i < myBoard.length && j >= 0 && j < myBoard.length && myBoard[i][j].getInteger() != -1)
					sum += myBoard[i][j].getInteger();

		if(myBoard[x][y].getInteger() != -1)
			sum -= myBoard[x][y].getInteger();

		if(sum % 10 == number)
			return true;

		return false;
	}

	public int getNext()
	{
		timesUses ++;
		int returnedNumber = this.myStore[0].getInteger();

		for (int i = 0; i < myStore.length - 1; i++) {
			myStore[i] = myStore[i + 1];
			store[i].setText(store[i + 1].getText());
		}

		Random rand = new Random();
		myStore[myStore.length - 1] = new myInteger(rand.nextInt(10));
		store[store.length - 1].setText(String.valueOf(myStore[myStore.length - 1].getInteger()));

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
		return (long) Math.abs((finishTime - startTime) / 1000);
	}

	public int getScore() {
		return (int) ((size * 100) * (( 1 / timesUses) * (1 / getTimeElapsed())));
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