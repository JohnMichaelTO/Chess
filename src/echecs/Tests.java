package echecs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Pieces.Piece;
import Pieces.typePiece;

public class Tests
{
	private Plateau echiquier = new Plateau();
	private List<Coord> listDeplacements = new ArrayList<Coord>();
	private List<Coord> listGeneree = new ArrayList<Coord>();
	
	// Vider les conteneurs
	public void Vider()
	{
		listDeplacements.clear();
		listGeneree.clear();
		echiquier.Vider();
	}
	
	// Test de déplacement
	public boolean Deplacement(Piece P, Coord c, List<Coord> list)
	{
		this.Vider();
		
		// Pièces
		echiquier.AjouterPiece(P, c);
		
		// Liste générée
		listGeneree = echiquier.deplacementPossibles(c);
		
		// Liste à comparer
		listDeplacements.addAll(list);
		list.clear();
		
		return listDeplacements.equals(listGeneree);
	}
	
	// Test de prise d'une pièce adverse
	public boolean PrisePiece(List<Piece> listePiece, List<Coord> listeCoordPieces, List<Coord> list)
	{
		this.Vider();
		
		// Pièces
		Iterator<Piece> i = listePiece.iterator();
		Iterator<Coord> j = listeCoordPieces.iterator();
		while(i.hasNext() && j.hasNext())
		{
			echiquier.AjouterPiece(i.next(), j.next());
		}
		
		// Liste générée
		i = listePiece.iterator();
		j = listeCoordPieces.iterator();
		if(i.next().getType() == typePiece.PION) listGeneree = echiquier.capturePossibles(j.next());
		else listGeneree = echiquier.deplacementPossibles(j.next());
		
		// Liste à comparer
		listDeplacements.addAll(list);
		list.clear();
		listePiece.clear();
		listeCoordPieces.clear();
		
		return listDeplacements.equals(listGeneree);
	}
	
	// Test de l'échec au roi
	public boolean EchecRoi(List<Piece> listePiece, List<Coord> listeCoordPieces, int joueur, boolean echec)
	{
		this.Vider();
		
		// Pièces
		Iterator<Piece> i = listePiece.iterator();
		Iterator<Coord> j = listeCoordPieces.iterator();
		while(i.hasNext() && j.hasNext())
		{
			echiquier.AjouterPiece(i.next(), j.next());
		}
		
		// Liste générée
		listGeneree = echiquier.getKingChecked(joueur);
		
		listePiece.clear();
		listeCoordPieces.clear();
		
		if(listGeneree.isEmpty() && !echec) return true;
		if(listGeneree.size() > 0 && echec) return true;
		return false;
	}
	
	// Test de l'échec et mat
	public boolean EchecEtMat(List<Piece> listePiece, List<Coord> listeCoordPieces, int joueur)
	{
		this.Vider();
		
		// Pièces
		Iterator<Piece> i = listePiece.iterator();
		Iterator<Coord> j = listeCoordPieces.iterator();
		while(i.hasNext() && j.hasNext())
		{
			echiquier.AjouterPiece(i.next(), j.next());
		}
		
		// Liste générée
		i = listePiece.iterator();
		j = listeCoordPieces.iterator();
		if(i.next().getType() == typePiece.PION) listGeneree = echiquier.capturePossibles(j.next());
		else listGeneree = echiquier.deplacementPossibles(j.next());
		
		listePiece.clear();
		listeCoordPieces.clear();
		
		if(listGeneree.size() <= 0 && echiquier.getKingChecked(joueur).size() > 0) return true;
		return false;
	}
	
	// Affichage (debug)
	public void Afficher(List<Coord> list)
	{
		for(Iterator<Coord> i = list.iterator(); i.hasNext();)
		{
			Coord k = i.next();
			System.out.println(k.toString2());
		}
	}
}
