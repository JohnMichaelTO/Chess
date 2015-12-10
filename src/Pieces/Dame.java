package Pieces;

import Deplacement.*;

public class Dame extends Piece
{
	// Constructeur
	public Dame(char couleur)
	{
		super(couleur, typePiece.DAME, new DeplacementDame());
	}
}
