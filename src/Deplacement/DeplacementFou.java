package Deplacement;

import java.util.ArrayList;
import java.util.List;

import Pieces.Piece;
import echecs.Coord;

public class DeplacementFou implements Deplacement
{
	// Renvoie les mouvements possibles d'une pièce
	public List<Coord> generatePossibleMoves(Piece[][] plateau, Coord c)
	{
		List<Coord> list = new ArrayList<Coord>();
		char couleur = plateau[c.getX()][c.getY()].getCouleur();
		// Direction haut gauche
		int x = c.getX() - 1;
		int y = c.getY() - 1;
		boolean firstPiece = true;
		while(Coord.verifBornes(x, y) && firstPiece && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
			if(plateau[x][y] != null && plateau[x][y].getCouleur() != couleur) firstPiece = false;
			x--;
			y--;
		}
		
		// Direction haut droite
		x = c.getX() - 1;
		y = c.getY() + 1;
		firstPiece = true;
		while(Coord.verifBornes(x, y) && firstPiece && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
			if(plateau[x][y] != null && plateau[x][y].getCouleur() != couleur) firstPiece = false;
			x--;
			y++;
		}
		
		// Direction bas gauche
		x = c.getX() + 1;
		y = c.getY() - 1;
		firstPiece = true;
		while(Coord.verifBornes(x, y) && firstPiece && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
			if(plateau[x][y] != null && plateau[x][y].getCouleur() != couleur) firstPiece = false;
			x++;
			y--;
		}
		
		// Direction bas droite
		x = c.getX() + 1;
		y = c.getY() + 1;
		firstPiece = true;
		while(Coord.verifBornes(x, y) && firstPiece && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
			if(plateau[x][y] != null && plateau[x][y].getCouleur() != couleur) firstPiece = false;
			x++;
			y++;
		}
		
		return list;
	}
	
	// Renvoie les mouvements possibles d'une pièce pour en manger une autre
	public List<Coord> generatePossibleCaptureMoves(Piece[][] plateau, Coord c)
	{
		return generatePossibleMoves(plateau, c);
	}
}