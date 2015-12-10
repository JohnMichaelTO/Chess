package echecs;

public class Coord
{
	// Attributs
	private int x;
	private int y;
	
	// Accesseurs
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setXY(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	public void set(Coord c)
	{
		this.setX(c.x);
		this.setY(c.y);
	}
	
	// Constructeurs
	public Coord(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	// Conversion des coordonnées verticales (affichage/saisie)
	public static int coorVtoUser(int a)
	{
		return Plateau.largeur - a;
	}
	
	// Conversion des coordonnées horizontales (affichage/saisie)
	public static char coorHtoUser(int a)
	{
		return (char) ('a' + a);
	}
	
	// Conversion des coordonnées verticales (tableau)
	public static int coorVtoTab(int a)
	{
		return Plateau.largeur - a;
	}
	
	// Conversion des coordonnées horizontales (tableau)
	public static int coorHtoTab(char a)
	{
		return (int) (a - 'a');
	}
	
	// Vérifie les bornes du tableau
	public static boolean verifBornes(int x, int y)
	{
		if(x >= 0 && x < Plateau.longueur && y >= 0 && y < Plateau.largeur) return true;
		else return false;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Coord other = (Coord) obj;
		if (x != other.x) return false;
		if (y != other.y) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "(" + Coord.coorVtoUser(x) + ", " + Coord.coorHtoUser(y) + ")";
	}
	
	public String toString2()
	{
		return "(" + x + ", " + y + ")";
	}
}
