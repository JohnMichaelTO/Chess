package echecs;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import Pieces.*;

public class Game
{
	private Plateau echiquier = null;
	private List<Coord> list = null;
	private int joueur = 1; // joueur 1 = blancs, joueur 2 = noirs
	private Scanner scanner;
	
	// Sélection menu
	public int AfficherMenu()
	{
		int valeur = 0;
		
		try
		{
			System.out.println("Echecs\n--");
			System.out.println("1. Mode solo");
			System.out.println("2. Mode deux joueurs");
			System.out.println("3. Programme de test automatique");
			System.out.println("4. Quitter");
			
			System.out.print("Choix : ");
			
			scanner = new Scanner(System.in);
			valeur = scanner.nextInt();
			scanner.nextLine();
		}
		catch(InputMismatchException e)
		{
			System.out.println("Erreur : entrez une valeur entre 1 et 4 !");
		}
		return valeur;
	}
	
	// Choix du mode
	public boolean Mode()
	{
		int valeur = this.AfficherMenu();
		boolean retour = true;
		switch(valeur)
		{
			case 1:
				System.out.println("--\nMode solo\n--");
				System.out.println("Mode non implémenté");
				break;
			case 2:
				System.out.println("--\nMode deux joueurs\n--");
				this.DeuxJoueurs();
				break;
			case 3:
				System.out.println("--\nProgramme de test automatique\n--");
				this.ProgrammeTest();
				break;
			case 4:
				retour = false;
				break;
		}
		return retour;
	}
	
	// Mode deux joueurs
	public void DeuxJoueurs()
	{
		echiquier = new Plateau();
		Coord d = null, a = null; // Coordonnées départ et arrivée
		int roque = 0;
		
		do
		{
			System.out.println("----------------------------------");
			echiquier.Afficher();
			System.out.println("----------------------------------");
			AfficheJoueur();
			
			// Roi en échec ?
			roque = 0;
			list = echiquier.getKingChecked(joueur);
			if(list != null && list.size() > 0)
			{
				if(list.size() == 1) System.out.println("Attention, le roi est en échec par la pièce suivante :");
				else System.out.println("Attention, le roi est en échec par les pièces suivantes :");
				
				AfficherList(list);
				System.out.println("----------------------------------");
			}
			else roque = echiquier.roquePossible(joueur);
			
			// Liste & Choix d'une pièce
			list = echiquier.getPiecesDisponibles(joueur);
			AfficherList(list, roque);
			if(roque != 0) d = ChoixPiece(list, "Choix d'une pièce (ou d'un roque)", roque);
			else d = ChoixPiece(list, "Choix d'une pièce", roque);
			
			// Petit roque
			if(d.getX() == -1 && d.getY() == -1)
			{
				// Blancs
				if(joueur == 1)
				{
					echiquier.Move(new Coord(7, 4), new Coord(7, 6));
					echiquier.Move(new Coord(7, 7), new Coord(7, 5));
				}
				// Noirs
				else
				{
					echiquier.Move(new Coord(0, 4), new Coord(0, 2));
					echiquier.Move(new Coord(0, 0), new Coord(0, 3));
				}
			}
			// Grand roque
			else if(d.getX() == -2 && d.getY() == -2)
			{
				// Blancs
				if(joueur == 1)
				{
					echiquier.Move(new Coord(7, 4), new Coord(7, 2));
					echiquier.Move(new Coord(7, 0), new Coord(7, 3));
				}
				// Noirs
				else
				{
					echiquier.Move(new Coord(0, 4), new Coord(0, 6));
					echiquier.Move(new Coord(0, 7), new Coord(0, 5));
				}
			}
			else
			{
				// Choix d'une case d'arrivée
				list = echiquier.deplacementPossibles(d);
				System.out.println("----------------------------------");
				echiquier.Afficher(list);
				System.out.println("----------------------------------");
				System.out.println("Pièce sélectionnée : " + echiquier.AffichePiece(d) + " " + d.toString());
				System.out.println("----------------------------------");
				AfficherList(list);
				a = ChoixPiece(list, "Choix d'une arrivée");
				
				// Mouvement d'une pièce
				echiquier.Move(d, a);
			}
			ChangeJoueur();
		} while(!echiquier.isCheckMat(joueur) && !echiquier.isNulle(joueur));
		
		if(echiquier.isCheckMat()) System.out.println("Echec & Mat : joueur " + joueur);
		
		if(echiquier.isPartieNulle()) System.out.println("Partie nulle");
	}
	
	// Affiche la liste des pièces/coordonnées
	public void AfficherList(List<Coord> list)
	{
		this.AfficherList(list, 0);
	}
	
	// Affiche la liste des pièces/coordonnées + roque
	public void AfficherList(List<Coord> list, int roque)
	{
		Piece[][] plateau = echiquier.getPlateau();
		int j = 0;
		for(Iterator<Coord> i = list.iterator(); i.hasNext();)
		{
			Coord k = i.next();
			System.out.print(j + "." + k.toString());
			if(plateau[k.getX()][k.getY()] != null) System.out.println(" " + plateau[k.getX()][k.getY()].getName());
			else System.out.println("");
			j++;
		}
		plateau = null;
		
		if(roque == 1 || roque == 3) { System.out.println(j + ". Petit roque"); j++; }
		if(roque == 2 || roque == 3) { System.out.println(j + ". Grand roque"); j++; }
	}
	
	// Sélectionne une pièce dans la liste
	public Coord ChoixPiece(List<Coord> list, String message)
	{
		return this.ChoixPiece(list, message, 0);
	}
	
	// Sélectionne une pièce dans la liste
	public Coord ChoixPiece(List<Coord> list, String message, int roque)
	{
		Coord c = null;
		boolean valeurOK = false;
		while(!valeurOK)
		{
			try
			{
				System.out.print(message + " : ");
				scanner = new Scanner(System.in);
				int valeur = scanner.nextInt();
				scanner.nextLine();
				if(roque == 1 && valeur == list.size()) return new Coord(-1, -1);
				else if(roque == 2 && valeur == list.size()) return new Coord(-2, -2);
				else if(roque == 3)
				{
					if(valeur == list.size()) return new Coord(-1, -1);
					if(valeur == list.size() + 1) return new Coord(-2, -2);
				}
				c = list.get(valeur);
				valeurOK = true;
			}
			catch(IndexOutOfBoundsException | InputMismatchException e)
			{
				System.out.println("Erreur : entrez une valeur correcte !");
			}
		}
		return c;
	}
	
	// Affiche le joueur au trait
	public void AfficheJoueur()
	{
		if(joueur == 1) System.out.println("--------Blancs au trait !---------");
		else System.out.println("--------Noirs au trait !----------");
	}
	
	// Change de joueur
	public void ChangeJoueur()
	{
		if(joueur == 1) joueur = 2;
		else joueur = 1;
	}
	
	// Programme de test
	public void ProgrammeTest()
	{
		Tests test = new Tests();
		
		List<Coord> listeTest = new ArrayList<Coord>();
		List<Piece> listePiece = new ArrayList<Piece>();
		List<Coord> listeCoordPieces = new ArrayList<Coord>();
		
		System.out.println("Tests Pions\n--");
		listeTest.add(new Coord(2, 3));
		System.out.println("Test déplacement normal pion blanc : \t" + test.Deplacement(new Pion('B', true), new Coord(3, 3), listeTest));
		listeTest.add(new Coord(4, 3));
		System.out.println("Test déplacement normal pion noir : \t" + test.Deplacement(new Pion('N', true), new Coord(3, 3), listeTest));
		listeTest.add(new Coord(2, 3));
		listeTest.add(new Coord(1, 3));
		System.out.println("Test premier déplacement pion blanc : \t" + test.Deplacement(new Pion('B'), new Coord(3, 3), listeTest));
		listeTest.add(new Coord(4, 3));
		listeTest.add(new Coord(5, 3));
		System.out.println("Test premier déplacement pion noir : \t" + test.Deplacement(new Pion('N'), new Coord(3, 3), listeTest));
		listePiece.add(new Pion('B', true));
		listeCoordPieces.add(new Coord(3, 3));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(2, 2));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(2, 4));
		listeTest.add(new Coord(2, 2));
		listeTest.add(new Coord(2, 4));
		System.out.println("Test prise par pion blanc : \t\t" + test.PrisePiece(listePiece, listeCoordPieces, listeTest));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(3, 3));
		listePiece.add(new Pion('B', true));
		listeCoordPieces.add(new Coord(4, 2));
		listePiece.add(new Pion('B', true));
		listeCoordPieces.add(new Coord(4, 4));
		listeTest.add(new Coord(4, 2));
		listeTest.add(new Coord(4, 4));
		System.out.println("Test prise par pion noir : \t\t" + test.PrisePiece(listePiece, listeCoordPieces, listeTest));
		
		System.out.println("\nTests Fou\n--");
		listeTest.add(new Coord(6, 1));
		listeTest.add(new Coord(5, 2));
		listeTest.add(new Coord(4, 3));
		listeTest.add(new Coord(3, 4));
		listeTest.add(new Coord(2, 5));
		listeTest.add(new Coord(1, 6));
		listeTest.add(new Coord(0, 7));
		System.out.println("Test déplacement Fou : \t\t\t" + test.Deplacement(new Fou('B'), new Coord(7, 0), listeTest));
		listePiece.add(new Fou('B'));
		listeCoordPieces.add(new Coord(7, 0));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(5, 2));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(5, 0));
		listeTest.add(new Coord(6, 1));
		listeTest.add(new Coord(5, 2));
		System.out.println("Test prise par fou : \t\t\t" + test.PrisePiece(listePiece, listeCoordPieces, listeTest));
		
		System.out.println("\nTests Cavalier\n--");
		listeTest.add(new Coord(5, 1));
		listeTest.add(new Coord(6, 2));
		System.out.println("Test déplacement Cavalier : \t\t" + test.Deplacement(new Cavalier('B'), new Coord(7, 0), listeTest));
		listePiece.add(new Cavalier('B'));
		listeCoordPieces.add(new Coord(7, 0));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(5, 1));
		listeTest.add(new Coord(5, 1));
		listeTest.add(new Coord(6, 2));
		System.out.println("Test prise par cavalier : \t\t" + test.PrisePiece(listePiece, listeCoordPieces, listeTest));
		
		System.out.println("\nTests Tour\n--");
		listeTest.add(new Coord(6, 0));
		listeTest.add(new Coord(5, 0));
		listeTest.add(new Coord(4, 0));
		listeTest.add(new Coord(3, 0));
		listeTest.add(new Coord(2, 0));
		listeTest.add(new Coord(1, 0));
		listeTest.add(new Coord(0, 0));
		listeTest.add(new Coord(7, 1));
		listeTest.add(new Coord(7, 2));
		listeTest.add(new Coord(7, 3));
		listeTest.add(new Coord(7, 4));
		listeTest.add(new Coord(7, 5));
		listeTest.add(new Coord(7, 6));
		listeTest.add(new Coord(7, 7));
		System.out.println("Test déplacement Tour : \t\t" + test.Deplacement(new Tour('B'), new Coord(7, 0), listeTest));
		listePiece.add(new Tour('B'));
		listeCoordPieces.add(new Coord(7, 0));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(5, 2));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(5, 0));
		listeTest.add(new Coord(6, 0));
		listeTest.add(new Coord(5, 0));
		listeTest.add(new Coord(7, 1));
		listeTest.add(new Coord(7, 2));
		listeTest.add(new Coord(7, 3));
		listeTest.add(new Coord(7, 4));
		listeTest.add(new Coord(7, 5));
		listeTest.add(new Coord(7, 6));
		listeTest.add(new Coord(7, 7));
		System.out.println("Test prise par tour : \t\t\t" + test.PrisePiece(listePiece, listeCoordPieces, listeTest));
		
		System.out.println("\nTests Dame\n--");
		listeTest.add(new Coord(6, 0));
		listeTest.add(new Coord(5, 0));
		listeTest.add(new Coord(4, 0));
		listeTest.add(new Coord(3, 0));
		listeTest.add(new Coord(2, 0));
		listeTest.add(new Coord(1, 0));
		listeTest.add(new Coord(0, 0));
		listeTest.add(new Coord(7, 1));
		listeTest.add(new Coord(7, 2));
		listeTest.add(new Coord(7, 3));
		listeTest.add(new Coord(7, 4));
		listeTest.add(new Coord(7, 5));
		listeTest.add(new Coord(7, 6));
		listeTest.add(new Coord(7, 7));
		listeTest.add(new Coord(6, 1));
		listeTest.add(new Coord(5, 2));
		listeTest.add(new Coord(4, 3));
		listeTest.add(new Coord(3, 4));
		listeTest.add(new Coord(2, 5));
		listeTest.add(new Coord(1, 6));
		listeTest.add(new Coord(0, 7));
		System.out.println("Test déplacement Dame : \t\t" + test.Deplacement(new Dame('B'), new Coord(7, 0), listeTest));
		listePiece.add(new Dame('B'));
		listeCoordPieces.add(new Coord(7, 0));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(5, 2));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(5, 0));
		listeTest.add(new Coord(6, 0));
		listeTest.add(new Coord(5, 0));
		listeTest.add(new Coord(7, 1));
		listeTest.add(new Coord(7, 2));
		listeTest.add(new Coord(7, 3));
		listeTest.add(new Coord(7, 4));
		listeTest.add(new Coord(7, 5));
		listeTest.add(new Coord(7, 6));
		listeTest.add(new Coord(7, 7));
		listeTest.add(new Coord(6, 1));
		listeTest.add(new Coord(5, 2));
		System.out.println("Test prise par dame : \t\t\t" + test.PrisePiece(listePiece, listeCoordPieces, listeTest));
		
		System.out.println("\nTests Roi\n--");
		listeTest.add(new Coord(6, 0));
		listeTest.add(new Coord(6, 1));
		listeTest.add(new Coord(7, 1));
		System.out.println("Test déplacement Roi : \t\t\t" + test.Deplacement(new Roi('B'), new Coord(7, 0), listeTest));
		listePiece.add(new Roi('B'));
		listeCoordPieces.add(new Coord(4, 4));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(3, 3));
		listePiece.add(new Pion('B', true));
		listeCoordPieces.add(new Coord(5, 3));
		listePiece.add(new Tour('N'));
		listeCoordPieces.add(new Coord(0, 5));
		listePiece.add(new Fou('N'));
		listeCoordPieces.add(new Coord(1, 6));
		listeTest.add(new Coord(3, 3));
		listeTest.add(new Coord(5, 4));
		System.out.println("Test prise par roi : \t\t\t" + test.PrisePiece(listePiece, listeCoordPieces, listeTest));
		listePiece.add(new Roi('B'));
		listeCoordPieces.add(new Coord(4, 4));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(3, 3));
		listePiece.add(new Pion('B', true));
		listeCoordPieces.add(new Coord(5, 3));
		listePiece.add(new Tour('N'));
		listeCoordPieces.add(new Coord(0, 5));
		listePiece.add(new Fou('N'));
		listeCoordPieces.add(new Coord(1, 6));
		System.out.println("Test échec au roi : \t\t\t" + test.EchecRoi(listePiece, listeCoordPieces, 1, true));
		listePiece.add(new Roi('B'));
		listeCoordPieces.add(new Coord(4, 4));
		listePiece.add(new Pion('N', true));
		listeCoordPieces.add(new Coord(3, 3));
		listePiece.add(new Pion('B', true));
		listeCoordPieces.add(new Coord(5, 3));
		listePiece.add(new Tour('N'));
		listeCoordPieces.add(new Coord(0, 5));
		listePiece.add(new Fou('N'));
		listeCoordPieces.add(new Coord(1, 6));
		listePiece.add(new Dame('N'));
		listeCoordPieces.add(new Coord(2, 4));
		System.out.println("Test échec et mat : \t\t\t" + test.EchecEtMat(listePiece, listeCoordPieces, 1));
		
		System.out.println("\n");
	}
}
