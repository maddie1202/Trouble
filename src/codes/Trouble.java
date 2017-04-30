package codes;

public class Trouble 
{
public static int[] positions = new int [4];
	
	public static int randomNumberGenerator (int min, int max)
	{
		int number = (int) (Math.random() * (max - min + 1) + min); //Create random number
		return number;
	}
	
	public static int piecesOutsideHome() //returns the number of pieces outside home
	{
		int count = 0;
		
		for (int a = 0; a < 4; a++)
			if(positions[a] > 0 && positions[a] < 32)
				count++;
		
		return count;
	}
	
	public static boolean moveNextPieceOutOfHome()//moves the next piece out of home if possible
	//returns true if the move was sucessfull and returns false if it wasn't
	{
		for(int a = 0; a < 4; a++)
			if(positions[a] == 0 && positionConflict(a,1) == false)
			{
				positions[a]++;
				return true;
			}
		
		return false;
	}
	
	public static boolean positionConflict(int a, int roll) //checks if there will be a position conflict if the prposed move is executed
	{
		for (int b = 0; b < 4; b++)
		{
			if(positions[b] == 32)
				continue;
			else
				if(positions[a] + roll == positions[b])
					return true;
		}
			
		return false;
	}
	
	public static int availableMoves(int roll)//calculates how many pieces can be moves
	{
		int am = 0;
		
		for (int a = 0; a < 4; a++)
			if(positionConflict(a,roll) == false && positions[a]!= 0 && positions[a] + roll <= 32)
				am++;
		
		//System.out.println("Available Moves: " + am);
		
		return am;
	}
	
	
	public static void movePiece (int roll, int move) //moves a piece
	{
		int count = 0;
		
		if (move == 1 || piecesOutsideHome() == 1) //If the stragegy is to move the first avilable pice or if there's only one piece to move
		{
			for(int a = 0; a < 4; a++)//Moves the first available piece
				if(positions[a] > 0 && positions[a] < 33 && positions[a] + roll < 33 && positionConflict(a, roll) == false)
				{
					positions[a] += roll;
					break;
				}
		}
		
		else //If the strategy is to alternates which piece is moved
		{
			for(int a = 0; a < 4; a++)
				if(positions[a] > 0 && positions[a] < 33 && positions[a] + roll < 33 && positionConflict(a, roll) == false)
				{
					count ++;//adds to the counter every time there is a possible move
					
					if(count == move)//moves the piece if it's (move)th available piece
					{
						positions[a] += roll;
						break;
					}
					
				}
		}
		
	}
	
	public static void displayPositions()
	{
		for (int a = 0; a < 4; a++)
			System.out.print(positions[a] + " ");
		
		System.out.println();
	}
	
	public static boolean win() //checks to see the the game is one by returning false if it finds a piece that isn;t in the finish position(32)
	{
		for(int a = 0; a < 4; a++)
			if (positions[a] != 32)
				return false;
						
		return true;
	}
	
	public static void reset() //resets the game for mulitiple tests
	{
		for(int a = 0; a < 4; a++)
			positions[a] = 0;
	}
	
	public static void displayS(int[] manta, int size)
	{
		for(int a = 0; a < size; a++)
			System.out.print(manta[a] + " ");
		
		System.out.println();
	}
	
	public static int average(int[]manta, int size)//averages out the number of turns taken to complete the game
	{
		int average = 0;
		
		for(int a = 0; a < size; a++)
			average += manta[a];
		
		average = average / size;
		
		return average;
	}
	
	public static int s1() //Strategy 1 moves the first available piece and takes pieces out of home every time a 6 is rolled
	{
		int turn = 0;

		do
		{
			turn++;
			
			int roll = randomNumberGenerator(1,6);
			//System.out.println("Roll: " + roll);
			
			if(piecesOutsideHome() == 0) //If there aren't any pieces outside, a 6 has to be rolled to make a move
			{
				if (roll == 6)
					moveNextPieceOutOfHome();
				
				//displayPositions();
			}
			
			else if (piecesOutsideHome() < 4 && roll == 6)
			{
				boolean sucess = moveNextPieceOutOfHome();
				
				if (sucess == false)//if moving a piece outside home isn't sucessfull, move annother piece that's already on the board 
					movePiece(roll,1); //don't alternate
				
				//displayPositions();
			}
			
			else if (piecesOutsideHome() > 0)
			{
				movePiece(roll,1);//don't alternate
				//displayPositions();
			}
					
		}while(win() == false);
		
		//System.out.println();
		//System.out.println("Turn: " + turn);
		
		return turn;
	}
	
	public static int s2() //Strategy 2 moves the first available piece and only keeps 2 pieces outside of home/finish
	{
		int turn = 0;
		
		do
		{
			//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			turn++;
			
			int roll = randomNumberGenerator(1,6);
			//System.out.println("Roll: " + roll);
			
			if(piecesOutsideHome() == 0)//If there aren't any pieces outside, a 6 has to be rolled to make a move
			{
				if (roll == 6)
					moveNextPieceOutOfHome();

				//displayPositions();
				//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			}
			
			else if (piecesOutsideHome() == 1 && roll == 6)
			{	
				boolean sucess = moveNextPieceOutOfHome();
				
				if (sucess == false)//if moving a piece outside home isn't sucessfull, move annother piece that's already on the board
					movePiece(roll,1);//don't alternate
				
				//displayPositions();
				//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			}
			
			else if (piecesOutsideHome() > 0)
			{	
				movePiece(roll,1);//don't alternate
				//displayPositions();
				//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			}
					
		}while(win() == false);
		
		//System.out.println();
		//System.out.println("Turn: " + turn);
		
		return turn;
	}
	
	public static int s3() //Strategy 3 alternates the pieces it moves and takes pieces out of home every time a 6 is rolled
	{
		int turn = 0;
		
		do
		{
			//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			turn++;
			
			int roll = randomNumberGenerator(1,6);//If there aren't any pieces outside, a 6 has to be rolled to make a move
			//System.out.println("Roll: " + roll);
			
			if(piecesOutsideHome() == 0)
			{
				if (roll == 6)
					moveNextPieceOutOfHome();

				//displayPositions();
				//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			}
			
			else if (piecesOutsideHome() < 4 && roll == 6)
			{	
				boolean sucess = moveNextPieceOutOfHome();
				
				if (sucess == false)//if moving a piece outside home isn't sucessfull, move annother piece that's already on the board
				{
					int move = 0;
					
					try
					{
						move = (turn % availableMoves(roll)) + 1;//alterneates which piece is moved
					}
					
					catch (Exception e) //If there aren't any possible moves, continue to the next roll
					{
						continue;
					}
					
					//System.out.println("Move:" +move);
					movePiece(roll,move);//alternate
				}
				
				//displayPositions();
				//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			}
			
			else if (piecesOutsideHome() > 0)
			{	
				
				int move = 0;
				
				try
				{
					move = (turn % availableMoves(roll)) + 1;//alterneates which piece is moved
				}
				
				catch (Exception e)//If there aren't any possible moves, continue to the next roll
				{
					continue;
				}
				
				//System.out.println("Move:" +move);
				movePiece(roll,move); //alternate
				//displayPositions();
				//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			}
					
		}while(win() == false);
		
		//System.out.println();
		//System.out.println("Turn: " + turn);
		
		return turn;
	}
	
	public static int s4() //Strategy 4 alternates the pieces it moves and only keeps 2 pieces outside of home/finish
	{
		int turn = 0;
		
		do
		{
			//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			turn++;
			
			int roll = randomNumberGenerator(1,6);
			//System.out.println("Roll: " + roll);
			
			if(piecesOutsideHome() == 0)//If there aren't any pieces outside, a 6 has to be rolled to make a move
			{
				if (roll == 6)
					moveNextPieceOutOfHome();

				//displayPositions();
				//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			}
			
			else if (piecesOutsideHome() == 1 && roll == 6)
			{	
				boolean sucess = moveNextPieceOutOfHome();
				
				if (sucess == false)//if moving a piece outside home isn't sucessfull, move annother piece that's already on the board
				{
					int move = 0;
					
					try
					{
						move = (turn % availableMoves(roll)) + 1;//alterneates which piece is moved
					}
					
					catch (Exception e)//If there aren't any possible moves, continue to the next roll
					{
						continue;
					}
					
					//System.out.println("Move:" +move);
					movePiece(roll,move);//alternate
				}
				
				//displayPositions();
				//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			}
			
			else if (piecesOutsideHome() > 0)
			{	
				
				int move = 0;
				
				try
				{
					move = (turn % availableMoves(roll)) + 1;//alterneates which piece is moved
				}
				
				catch (Exception e)//If there aren't any possible moves, continue to the next roll
				{
					continue;
				}
				
				//System.out.println("Move:" +move);
				movePiece(roll,move); //alternate
				//displayPositions();
				//System.out.println("Pieces Outside Home: " + piecesOutsideHome());
			}
					
		}while(win() == false);
		
		//System.out.println();
		//System.out.println("Turn: " + turn);
		
		return turn;
	}

	public static void main(String[] args) 
	{
		final int SIZE = 100000; //This variable controls how many times each strtegy is testef
		
		//The following stements test each strategy(SIZE times) and calcultes the average number of turn each one takes
		int[] s1 = new int [SIZE];
		
		for(int a = 0; a < SIZE; a++)
		{
			reset();
			s1[a] = s1();
		}
		
		//displayS(s1, SIZE);
		int average1 = average(s1, SIZE);
		System.out.println("Strategy 1 took an average of " + average1 + " turns.");
		
		
		
		int[] s2 = new int [SIZE];
		
		for(int a = 0; a < SIZE; a++)
		{
			reset();
			s2[a] = s2();
		}
		
		//displayS(s2, SIZE);
		int average2 = average(s2, SIZE);
		System.out.println("Strategy 2 took an average of " + average2 + " turns.");
		
		
		
		int[] s3 = new int [SIZE];
		
		for(int a = 0; a < SIZE; a++)
		{
			reset();
			s3[a] = s3();
		}
		
		//displayS(s3, SIZE);
		int average3 = average(s3, SIZE);
		System.out.println("Strategy 3 took an average of " + average3 + " turns.");
		
		
		
		int[] s4 = new int [SIZE];
		
		for(int a = 0; a < SIZE; a++)
		{
			reset();
			s4[a] = s4();
		}
		
		//displayS(s4, SIZE);
		int average4 = average(s4, SIZE);
		System.out.println("Strategy 4 took an average of " + average4 + " turns.");
		
		System.out.println();
		
		if (average1 < average2 && average1 < average3 && average1 < average4)
		{
			System.out.println("The best way to play is to move one piece and to take pieces out of home whenever possible.");
		}
		
		else if (average2 < average1 && average2 < average3 && average2 < average4)
		{
			System.out.println("The best way to play is to move one piece and to only keep 2 pieces out of home/finish.");
		}
		
		else if (average3 < average1 && average3 < average2 && average3 < average4)
		{
			System.out.println("The best way to play is to alternate the pice you move and to take pieces out of home whenever possible.");
		}
		
		else if (average4 < average2 && average4 < average2 && average4 < average3)
		{
			System.out.println("The best way to play is to alternate the pice you move and to only keep 2 pieces out of home/finish.");
		}
		
	}
}
