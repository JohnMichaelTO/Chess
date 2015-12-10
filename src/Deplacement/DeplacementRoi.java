package Deplacement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Pieces.Piece;
import echecs.Coord;
import echecs.Plateau;

public class DeplacementRoi implements Deplacement
{
	// G�n�ration des mouvements de d�placement/capture de toutes les autres pi�ces
	private List<Coord> generateDangerMoves(Piece[][] plateau, Coord k)
	{
		List<Coord> listDanger = null, listDangerAll = null;
		for(int i = 0; i < Plateau.longueur; i++)
		{
			for(int j = 0; j < Plateau.largeur; j++)
			{
				if(plateau[i][j] != null && plateau[i][j].getCouleur() != plateau[k.getX()][k.getY()].getCouleur())
				{
					listDanger = plateau[i][j].capturePossibles(plateau, new Coord(i, j));
					if(listDangerAll == null) listDangerAll = listDanger;
					else if(listDanger != null && listDanger.size() > 0) listDangerAll.addAll(listDanger);
				}
			}
		}
		return listDangerAll;
	}
	
	// Renvoie les mouvements possibles d'une pi�ce
	public List<Coord> generatePossibleMoves(Piece[][] plateau, Coord c)
	{
		List<Coord> list = new ArrayList<Coord>();
		char couleur = plateau[c.getX()][c.getY()].getCouleur();
		// D�placements possibles
		for(int i = c.getX() - 1; i <= c.getX() + 1; i++)
		{
			for(int j = c.getY() - 1; j <= c.getY() + 1; j++)
			{
				if((c.getX() != i || c.getY() != j) && Coord.verifBornes(i, j))
				{
					if(plateau[i][j] == null || (plateau[i][j] != null && plateau[i][j].getCouleur() != couleur)) list.add(new Coord(i, j));
				}
			}
		}
		// Partie v�rifiant si les d�placements peuvent mettre le roi en �chec
		// G�n�ration des mouvements de d�placement/capture de toutes les autres pi�ces
		List<Coord> listDanger = this.generateDangerMoves(plateau, c);
		
		for(Iterator<Coord> i = list.iterator(); i.hasNext();)
		{
			Coord k = i.next();
			Piece[][] plateauTest = Plateau.moveTest(plateau, c, k);
			List<Coord> listTemp = this.generateDangerMoves(plateauTest, k);
			if(listTemp != null) listDanger.addAll(listTemp);
			Plateau.Vider(plateauTest);
			plateauTest = null;
		}
		
		if(listDanger != null)
		{
			// Comparaison et retrait des mouvements o� le roi pourrait �tre en �chec
			for(Iterator<Coord> i = list.iterator(); i.hasNext();)
			{
				Coord k = i.next();
				boolean supprimer = false;
				for(Iterator<Coord> j = listDanger.iterator(); j.hasNext();)
				{
					Coord l = j.next();
					if(k.equals(l)) supprimer = true;
					/*
					{
						i.remove();
						break;
					}
					*/
				}
				if(supprimer) i.remove();
			}
			listDanger.clear();
		}
		
		return list;
	}
	
	// Renvoie les mouvements possibles d'une pi�ce pour en manger une autre
	public List<Coord> generatePossibleCaptureMoves(Piece[][] plateau, Coord c)
	{
		List<Coord> list = new ArrayList<Coord>();
		char couleur = plateau[c.getX()][c.getY()].getCouleur();
		// D�placements possibles
		for(int i = c.getX() - 1; i <= c.getX() + 1; i++)
		{
			for(int j = c.getY() - 1; j <= c.getY() + 1; j++)
			{
				if((c.getX() != i || c.getY() != j) && Coord.verifBornes(i, j))
				{
					if(plateau[i][j] == null || (plateau[i][j] != null && plateau[i][j].getCouleur() != couleur)) list.add(new Coord(i, j));
				}
			}
		}
		return list;
		//return generatePossibleMoves(plateau, c);
	}
}