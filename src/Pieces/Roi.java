package Pieces;

import Deplacement.*;

public class Roi extends Piece
{
	// Constructeur
	public Roi(char couleur)
	{
		super(couleur, typePiece.ROI, new DeplacementRoi());
	}
}
