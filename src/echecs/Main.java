package echecs;

public class Main
{
	public static void main(String[] args)
	{
		boolean mode;
		do
		{
			Game game = new Game();
			mode = game.Mode();
			game = null;
		} while(mode);
	}
}
