package Pieces;

import Deplacement.*;

public class Tour extends Piece
{
	// Constructeur
	public Tour(char couleur)
	{
		super(couleur, typePiece.TOUR, new DeplacementTour());
	}
}