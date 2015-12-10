package Deplacement;

import java.util.List;
import Pieces.Piece;
import echecs.Coord;

public interface Deplacement 
{
	// Renvoie les mouvements possibles d'une pi�ce
	public List<Coord> generatePossibleMoves(Piece[][] plateau, Coord c);
	// Renvoie les mouvements possibles d'une pi�ce pour en manger une autre
	public List<Coord> generatePossibleCaptureMoves(Piece[][] plateau, Coord c);
}