package Deplacement;

import java.util.ArrayList;
import java.util.List;
import Pieces.Piece;
import echecs.Coord;

public class DeplacementPion implements Deplacement
{
	public List<Coord> generatePossibleMoves(Piece[][] plateau, Coord c) 
	{
		List<Coord> list = this.generatePossibleCaptureMoves(plateau, c);
		int x = c.getX();
		int y = c.getY();
		char couleur = plateau[x][y].getCouleur();
		boolean isFirstMove = plateau[x][y].isFirstMove();
		
		// Pion blanc
		if(couleur == 'B')
		{
			// Cas du premier déplacement
			x = c.getX() - 1;
			y = c.getY();
			if(Coord.verifBornes(x - 1, y) && plateau[x][y] == null && plateau[x - 1][x] == null && !isFirstMove)
			{
				list.add(new Coord(x, y));
				list.add(new Coord(x - 1, y));
			}
			// Haut
			else if(Coord.verifBornes(x, y) && plateau[x][y] == null)
			{
				list.add(new Coord(x, y));
			}
		}
		// Pion noir
		if(couleur == 'N')
		{
			// Cas du premier déplacement
			x = c.getX() + 1;
			y = c.getY();
			if(Coord.verifBornes(x + 1, y) && plateau[x][y] == null && plateau[x + 1][y] == null && !isFirstMove)
			{
				list.add(new Coord(x, y));
				list.add(new Coord(x + 1, y));
			}
			// Bas
			else if(Coord.verifBornes(x, y) && plateau[x][y] == null)
			{
				list.add(new Coord(x, y));
			}
		}
		return list;
	}
	
	public List<Coord> generatePossibleCaptureMoves(Piece[][] plateau, Coord c)
	{
		List<Coord> list = new ArrayList<Coord>();
		int x = c.getX();
		int y = c.getY();
		char couleur = plateau[x][y].getCouleur();
		// Pion blanc
		if(couleur == 'B')
		{
			// Haut à gauche
			x = c.getX() - 1;
			y = c.getY() - 1;
			if(Coord.verifBornes(x, y) && plateau[x][y] != null && plateau[x][y].getCouleur() == 'N')
			{
				list.add(new Coord(x, y));
			}
			
			// Haut à droite
			x = c.getX() - 1;
			y = c.getY() + 1;
			if(Coord.verifBornes(x, y) && plateau[x][y] != null && plateau[x][y].getCouleur() == 'N')
			{
				list.add(new Coord(x, y));
			}
		}
		// Pion noir
		if(couleur == 'N')
		{
			// Bas à gauche
			x = c.getX() + 1;
			y = c.getY() - 1;
			if(Coord.verifBornes(x, y) && plateau[x][y] != null && plateau[x][y].getCouleur() == 'B')
			{
				list.add(new Coord(x, y));
			}
			
			// Bas à droite
			x = c.getX() + 1;
			y = c.getY() + 1;
			if(Coord.verifBornes(x, y) && plateau[x][y] != null && plateau[x][y].getCouleur() == 'B')
			{
				list.add(new Coord(x, y));
			}
		}
		return list;
	}
}
