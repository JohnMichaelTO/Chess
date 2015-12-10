package Pieces;

import Deplacement.*;

public class Fou extends Piece
{
	// Constructeur
	public Fou(char couleur)
	{
		super(couleur, typePiece.FOU, new DeplacementFou());
	}
}
