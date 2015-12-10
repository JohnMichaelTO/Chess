package Pieces;

import Deplacement.*;

public class Pion extends Piece
{
	// Attributs
	private boolean promu = false;
	private typePiece typePromu = typePiece.PION;
	
	// Accesseurs
	public boolean isPromu()
	{
		return promu;
	}
	
	public typePiece getTypePromu()
	{
		return typePromu;
	}
	
	public void setPromu(boolean promu)
	{
		this.promu = promu;
	}

	public void setTypePromu(typePiece typePromu)
	{
		this.typePromu = typePromu;
	}

	// Constructeur
	public Pion(char couleur)
	{
		super(couleur, typePiece.PION, new DeplacementPion());
	}
	
	// Constructeur
	public Pion(char couleur, boolean firstMove)
	{
		super(couleur, typePiece.PION, new DeplacementPion());
		super.setFirstMove(firstMove);
	}
}