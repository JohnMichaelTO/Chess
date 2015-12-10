package Pieces;

import java.util.List;

import Deplacement.*;
import echecs.Coord;

public abstract class Piece //implements Deplacement
{
	// Attributs
	protected char couleur; // N = noir, B = blanc
	protected typePiece type;
	protected boolean firstMove; // true = pièce déplacée, false = pièce non déplacée (pour le premier déplacement de la pièce)
	protected Deplacement deplacement;
	
	// Accesseurs
	public char getCouleur()
	{
		return couleur;
	}

	public typePiece getType()
	{
		return type;
	}

	public boolean isFirstMove()
	{
		return firstMove;
	}

	public void setCouleur(char couleur)
	{
		this.couleur = couleur;
	}

	public void setType(typePiece type)
	{
		this.type = type;
	}

	public void setFirstMove(boolean firstMove)
	{
		this.firstMove = firstMove;
	}

	public void setDeplacement(Deplacement deplacement)
	{
		this.deplacement = deplacement;
	}

	// Constructeur
	public Piece(char couleur, typePiece type, Deplacement deplacement)
	{
		this.setCouleur(couleur);
		this.setType(type);
		this.setFirstMove(false);
		this.setDeplacement(deplacement);
	}
	
	// Methods
	public String getName()
	{
		String out = "";
		switch(this.type)
		{
			case ROI: out = "R";
				break;
			case DAME: out = "D";
				break;
			case TOUR: out = "T";
				break;
			case FOU: out = "F";
				break;
			case CAVALIER: out = "C";
				break;
			case PION: out = "P";
				break;
		}
		return out + this.couleur;
	}
	
	// Renvoie les mouvements possibles d'une pièce
	public List<Coord> deplacementPossibles(Piece[][] plateau, Coord c)
	{
		return this.deplacement.generatePossibleMoves(plateau, c);
	}
	
	// Renvoie les mouvements possibles d'une pièce
	public List<Coord> capturePossibles(Piece[][] plateau, Coord c)
	{
		return this.deplacement.generatePossibleCaptureMoves(plateau, c);
	}
}