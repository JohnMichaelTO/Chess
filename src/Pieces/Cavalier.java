package Pieces;

import Deplacement.*;

public class Cavalier extends Piece
{
	// Constructeur
	public Cavalier(char couleur)
	{
		super(couleur, typePiece.CAVALIER, new DeplacementCavalier());
	}
}