package Deplacement;

import java.util.ArrayList;
import java.util.List;

import Pieces.Piece;
import echecs.Coord;

public class DeplacementCavalier implements Deplacement
{
	// Renvoie les mouvements possibles d'une pièce
	public List<Coord> generatePossibleMoves(Piece[][] plateau, Coord c)
	{
		List<Coord> list = new ArrayList<Coord>();
		char couleur = plateau[c.getX()][c.getY()].getCouleur();
		
		// Direction haut
		int x = c.getX() - 2;
		int y = c.getY() - 1;
		if(Coord.verifBornes(x, y) && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
		}
		y = c.getY() + 1;
		if(Coord.verifBornes(x, y) && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
		}
		// Direction gauche
		x = c.getX() - 1;
		y = c.getY() - 2;
		if(Coord.verifBornes(x, y) && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
		}
		x = c.getX() + 1;
		if(Coord.verifBornes(x, y) && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
		}
		// Direction droite
		x = c.getX() - 1;
		y = c.getY() + 2;
		if(Coord.verifBornes(x, y) && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
		}
		x = c.getX() + 1;
		if(Coord.verifBornes(x, y) && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
		}
		// Direction bas
		x = c.getX() + 2;
		y = c.getY() - 1;
		if(Coord.verifBornes(x, y) && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
		}
		y = c.getY() + 1;
		if(Coord.verifBornes(x, y) && (plateau[x][y] == null || (plateau[x][y] != null && plateau[x][y].getCouleur() != couleur)))
		{
			list.add(new Coord(x, y));
		}
		
		return list;
	}
	
	// Renvoie les mouvements possibles d'une pièce pour en manger une autre
	public List<Coord> generatePossibleCaptureMoves(Piece[][] plateau, Coord c)
	{
		return generatePossibleMoves(plateau, c);
	}
}
