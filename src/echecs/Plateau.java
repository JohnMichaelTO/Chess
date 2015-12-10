package echecs;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import Deplacement.*;
import Pieces.*;

public class Plateau
{
	// Attributs
	public static int longueur = 8;
	public static int largeur = 8;
	private Piece[][] plateau = new Piece[8][8]; // Plateau
	private Scanner scan;
	private Coord RB = new Coord(7, 4); // Roi blanc
	private Coord RN = new Coord(0, 4); // Roi noir
	private boolean partieNulle = false;
	private boolean CheckMat = false;
	
	// Accesseurs
	public Piece[][] getPlateau()
	{
		return plateau;
	}
	
	public boolean isPartieNulle()
	{
		return partieNulle;
	}
	
	public boolean isCheckMat()
	{
		return CheckMat;
	}
	
	public void setPlateau(Piece[][] plateau)
	{
		this.plateau = plateau;
	}

	public void setPartieNulle(boolean partieNulle)
	{
		this.partieNulle = partieNulle;
	}

	public void setCheckMat(boolean checkMat)
	{
		this.CheckMat = checkMat;
	}

	// Constructeur
	public Plateau()
	{
		this.InitPlateau();
	}
	
	public Plateau(Piece[][] plateau, Coord RB, Coord RN)
	{
		this.setPlateau(plateau);
		this.RB.set(RB);
		this.RN.set(RN);
	}
	
	// Initialisation du plateau
	public void InitPlateau()
	{
		plateau[0][0] = new Tour('N');
		plateau[0][1] = new Cavalier('N');
		plateau[0][2] = new Fou('N');
		plateau[0][3] = new Dame('N');
		plateau[0][4] = new Roi('N');
		plateau[0][5] = new Fou('N');
		plateau[0][6] = new Cavalier('N');
		plateau[0][7] = new Tour('N');
		
		plateau[7][0] = new Tour('B');
		plateau[7][1] = new Cavalier('B');
		plateau[7][2] = new Fou('B');
		plateau[7][3] = new Dame('B');
		plateau[7][4] = new Roi('B');
		plateau[7][5] = new Fou('B');
		plateau[7][6] = new Cavalier('B');
		plateau[7][7] = new Tour('B');
		
		for(int i = 1; i < (Plateau.longueur - 1); i++)
		{
			for(int j = 0; j < Plateau.largeur; j++)
			{
				if(i == 1) plateau[i][j] = new Pion('N');
				else if(i == 6) plateau[i][j] = new Pion('B');
				else plateau[i][j] = null;
			}
		}
		
		this.RB.setXY(7, 4);
		this.RN.setXY(0, 4);
	}
	
	// Affichage du plateau
	public void Afficher()
	{
		System.out.print(" |");
		for(int i = 0; i < Plateau.longueur; i++)
		{
			System.out.print(" " + Coord.coorHtoUser(i) + " |");
			//System.out.print(" " + i + " |");
		}
		System.out.println("");
		
		for(int i = 0; i < Plateau.longueur; i++)
		{
			System.out.print(Coord.coorVtoUser(i) + "|");
			//System.out.print(i + "|");
			for(int j = 0; j < Plateau.largeur; j++)
			{
				if(plateau[i][j] != null) System.out.print(plateau[i][j].getName() + " |");
				else System.out.print("   |");
			}
			System.out.println("");
		}
	}
	
	// Affichage du plateau
	public void Afficher(List<Coord> list)
	{
		if(list != null)
		{
			System.out.print(" |");
			for(int i = 0; i < Plateau.longueur; i++)
			{
				System.out.print(" " + Coord.coorHtoUser(i) + " |");
				//System.out.print(" " + i + " |");
			}
			System.out.println("");
			
			for(int i = 0; i < Plateau.longueur; i++)
			{
				System.out.print(Coord.coorVtoUser(i) + "|");
				//System.out.print(i + "|");
				for(int j = 0; j < Plateau.largeur; j++)
				{
					Iterator<Coord> it = list.iterator();
					boolean blank = true;
					while(it.hasNext())
					{
						Coord temp = it.next();
						if(temp.getX() == i && temp.getY() == j)
						{
							blank = false;
							if(plateau[i][j] != null) System.out.print(plateau[i][j].getName() + "X|");
							else System.out.print("XXX|");
						}
					}
					if(plateau[i][j] != null && blank) System.out.print(plateau[i][j].getName() + " |");
					else if(blank) System.out.print("   |");
				}
				System.out.println("");
			}
		}
		else System.out.println("Liste nulle");
	}
	
	// Retourne les pièces disponibles
	public List<Coord> getPiecesDisponibles(int joueur)
	{
		List<Coord> list = new ArrayList<Coord>();
		List<Coord> listTemp = null;
		char couleur = this.JoueurToCouleur(joueur);
		
		for(int i = 0; i < Plateau.longueur; i++)
		{
			for(int j = 0; j < Plateau.largeur; j++)
			{
				if(plateau[i][j] != null && plateau[i][j].getCouleur() == couleur)
				{
					// On insère que les coordonnées où les pièces ont un mouvement possible
					listTemp = this.deplacementPossibles(new Coord(i, j));
					if(listTemp != null && listTemp.size() > 0)
					{
						list.add(new Coord(i, j));
						listTemp.clear();
					}
				}
			}
		}
		return list;
	}
	
	// Vider le plateau
	public static void Vider(Piece[][] plateau)
	{
		if(plateau != null)
		{
			for(int i = 0; i < Plateau.longueur; i++)
			{
				for(int j = 0; j < Plateau.largeur; j++)
				{
					plateau[i][j] = null;
				}
			}
		}
	}
	
	// Vider le plateau
	public void Vider()
	{
		Plateau.Vider(plateau);
	}
	
	// Déplacement fictif
	public static Piece[][] moveTest(Piece[][] plateau, Coord d, Coord a)
	{
		Piece[][] plateauTest = new Piece[8][8];
		
		for(int i = 0; i < Plateau.longueur; i++)
		{
			for(int j = 0; j < Plateau.largeur; j++)
			{
				plateauTest[i][j] = plateau[i][j];
			}
		}
		
		plateauTest[a.getX()][a.getY()] = plateauTest[d.getX()][d.getY()];
		plateauTest[d.getX()][d.getY()] = null;
		
		return plateauTest;
	}
	
	// Déplace une pièce
	public void Move(Coord d, Coord a)
	{
		// Mouvement
		plateau[a.getX()][a.getY()] = plateau[d.getX()][d.getY()];
		if(!plateau[a.getX()][a.getY()].isFirstMove()) plateau[a.getX()][a.getY()].setFirstMove(true);
		
		// Promotion d'un pion
		if(plateau[a.getX()][a.getY()].getType() == typePiece.PION && !((Pion)plateau[a.getX()][a.getY()]).isPromu() && (a.getX() == 0 || a.getX() == 7))
		{
			plateau[a.getX()][a.getY()].setDeplacement(PromotionPion(a));
			((Pion)plateau[a.getX()][a.getY()]).setPromu(true);
		}
		
		// MaJ Coordonnées du roi
		if(plateau[a.getX()][a.getY()].getType() == typePiece.ROI)
		{
			if(plateau[a.getX()][a.getY()].getCouleur() == 'N') this.RN.setXY(a.getX(), a.getY());
			if(plateau[a.getX()][a.getY()].getCouleur() == 'B') this.RB.setXY(a.getX(), a.getY());
		}
		
		plateau[d.getX()][d.getY()] = null;
	}
	
	// Promotion d'un pion
	private Deplacement PromotionPion(Coord k)
	{
		int valeur = 0;
		while(valeur <= 0 || valeur > 4)
		{
			try
			{
				System.out.println("1. Cavalier");
				System.out.println("2. Fou");
				System.out.println("3. Tour");
				System.out.println("4. Dame");
				System.out.print("Votre pion doit être promu, choississez sa promotion : ");
				scan = new Scanner(System.in);
				valeur = scan.nextInt();
				scan.nextLine();
			}
			catch(InputMismatchException e)
			{
				System.out.println("Erreur : Entrez une valeur entre 1 et 4 !");
			}
		}
		
		scan = null;
		
		switch(valeur)
		{
			case 1:
				((Pion)plateau[k.getX()][k.getY()]).setTypePromu(typePiece.CAVALIER);
				return new DeplacementCavalier();
			case 2:
				((Pion)plateau[k.getX()][k.getY()]).setTypePromu(typePiece.FOU);
				return new DeplacementFou();
			case 3:
				((Pion)plateau[k.getX()][k.getY()]).setTypePromu(typePiece.TOUR);
				return new DeplacementTour();
			case 4:
				((Pion)plateau[k.getX()][k.getY()]).setTypePromu(typePiece.DAME);
				return new DeplacementDame();
			default: return null;
		}
	}
	
	// Renvoie les mouvements possibles d'une pièce à une coordonnée k
	public List<Coord> deplacementPossibles(Coord k)
	{
		List<Coord> list = plateau[k.getX()][k.getY()].deplacementPossibles(plateau, k);

		char couleur = plateau[k.getX()][k.getY()].getCouleur();
		Coord roi;
		if(couleur == 'B') roi = this.RB;
		else roi = this.RN;
		
		// Retrait des mouvements où le roi sera en échec
		for(Iterator<Coord> l = list.iterator(); l.hasNext();)
		{
			Coord m = l.next();
			Piece[][] plateauTest = Plateau.moveTest(plateau, k, m);
			
			if(plateauTest[m.getX()][m.getY()].getType() == typePiece.ROI) roi = m;
			
			List<Coord> listTemp = this.getKingChecked(plateauTest, plateau[k.getX()][k.getY()].getCouleur(), roi);

			if(listTemp != null && listTemp.size() > 0) l.remove();
			
			plateauTest = null;
			listTemp.clear();
		}
		return list;
	}
	
	// Renvoie les captures possibles d'une pièce à une coordonnée k
	public List<Coord> capturePossibles(Coord k)
	{
		return plateau[k.getX()][k.getY()].capturePossibles(plateau, k);
	}
	
	// Renvoie les captures possibles d'une pièce à une coordonnée k
	public List<Coord> capturePossibles(Piece[][] plateau, Coord k)
	{
		return plateau[k.getX()][k.getY()].capturePossibles(plateau, k);
	}
	
	// Retourne la couleur d'un joueur
	private char JoueurToCouleur(int joueur)
	{
		if(joueur == 1) return 'B';
		return 'N';
	}
	
	// Liste des pièces qui mettent le roi (k) est en échec
	public List<Coord> getKingChecked(Piece[][] plateau, char couleur, Coord k)
	{
		// Liste des coordonnées des pièces qui mettent le roi en échec
		List<Coord> list = new ArrayList<Coord>();
		List<Coord> listTemp = null;
		for(int i = 0; i < Plateau.longueur; i++)
		{
			for(int j = 0; j < Plateau.largeur; j++)
			{
				if(plateau[i][j] != null && plateau[i][j].getCouleur() != couleur)
				{
					// Liste des déplacements de chaque pièce adverse
					listTemp = this.capturePossibles(plateau, new Coord(i, j));
					// On compare si un de ses déplacements a une coordonnée similaire au roi
					for(Iterator<Coord> l = listTemp.iterator(); l.hasNext();)
					{
						Coord m = l.next();
						if(m.equals(k))
						{
							// En cas de coordonnée similaire, on ajoute les coordonnées de la pièce qui met en échec le roi
							list.add(new Coord(i, j));
						}
					}
					listTemp = null;
				}
			}
		}
		return list;
	}
	
	// Liste des pièces qui mettent le roi est en échec
	public List<Coord> getKingChecked(int joueur)
	{
		Coord k = null;
		char couleur = this.JoueurToCouleur(joueur);
		// Coordonnées du roi
		if(couleur == 'B') k = this.RB;
		else k = this.RN;
		return this.getKingChecked(plateau, couleur, k);
	}
	
	// Roque
	// 0 = pas de roque, 1 = petit roque, 2 = grand roque, 3 = petit & grand roque
	public int roquePossible(int joueur)
	{
		char couleur = this.JoueurToCouleur(joueur);
		boolean petit = true, grand = true;
		if(couleur == 'B')
		{
			// Roi blanc
			if(plateau[7][4] != null && (plateau[7][0] != null || plateau[7][7] != null))
			{
				if(plateau[7][4].getType() == typePiece.ROI && plateau[7][4].getCouleur() == couleur && !plateau[7][4].isFirstMove())
				{
					List<Coord> list = null;
					// Grand roque
					if(plateau[7][0] != null && plateau[7][0].getType() == typePiece.TOUR && plateau[7][0].getCouleur() == couleur && !plateau[7][0].isFirstMove())
					{
						// Test si chaque case peuvent mettre en échec une des deux pièces
						for(int i = 0; i < 4; i++)
						{
							if(i != 0 && plateau[7][i] != null) grand = false;
							list = this.getKingChecked(plateau, couleur, new Coord(7, i));
							if(list != null && list.size() > 0) grand = false;
						}
					}
					else grand = false;
					
					// Petit roque
					if(plateau[7][7] != null && plateau[7][7].getType() == typePiece.TOUR && plateau[7][7].getCouleur() == couleur && !plateau[7][7].isFirstMove())
					{
						// Test si chaque case peuvent mettre en échec une des deux pièces
						for(int i = 5; i <= 7; i++)
						{
							if(i != 7 && plateau[7][i] != null) petit = false;
							list = this.getKingChecked(plateau, couleur, new Coord(7, i));
							if(list != null && list.size() > 0) petit = false;
						}
					}
					else petit = false;
				}
				else { petit = false; grand = false; }
			}
			else { petit = false; grand = false; }
		}
		else
		{
			// Roi noir
			if(plateau[0][4] != null && (plateau[0][0] != null || plateau[0][7] != null))
			{
				if(plateau[0][4].getType() == typePiece.ROI && plateau[0][4].getCouleur() == couleur && !plateau[0][4].isFirstMove())
				{
					List<Coord> list = null;
					// Grand roque
					if(plateau[0][0] != null && plateau[0][0].getType() == typePiece.TOUR && plateau[0][0].getCouleur() == couleur && !plateau[0][0].isFirstMove())
					{
						// Test si chaque case peuvent mettre en échec une des deux pièces
						for(int i = 0; i < 4; i++)
						{
							if(i != 0 && plateau[0][i] != null) grand = false;
							list = this.getKingChecked(plateau, couleur, new Coord(0, i));
							if(list != null && list.size() > 0) grand = false;
						}
					}
					else grand = false;
					
					// Petit roque
					if(plateau[0][7] != null && plateau[0][7].getType() == typePiece.TOUR && plateau[0][7].getCouleur() == couleur && !plateau[0][7].isFirstMove())
					{
						// Test si chaque case peuvent mettre en échec une des deux pièces
						for(int i = 5; i <= 7; i++)
						{
							if(i != 7 && plateau[0][i] != null) petit = false;
							list = this.getKingChecked(plateau, couleur, new Coord(0, i));
							if(list != null && list.size() > 0) petit = false;
						}
					}
					else petit = false;
				}
				else { petit = false; grand = false; }
			}
			else { petit = false; grand = false; }
		}
		
		if(petit && grand) return 3;
		if(!petit && grand) return 2;
		if(petit && !grand) return 1;
		return 0;
	}
	
	// Vérifie un échec et mat
	public boolean isCheckMat(int joueur)
	{
		boolean retour = true;
		List<Coord> list = this.getKingChecked(joueur);
		List<Coord> list2 = this.getPiecesDisponibles(joueur);
		
		if(list == null || list2 == null) retour = false;
		if(list.size() <= 0) retour = false;
		if(list2.size() > 0) retour = false;
		
		list.clear();
		list2.clear();
		list = null;
		list2 = null;
		
		this.setCheckMat(retour);
		
		return retour;
	}
	
	// Vérifie si une partie est nulle
	public boolean isNulle(int joueur)
	{
		boolean retour = false;
		List<Coord> list = this.getKingChecked(joueur);
		List<Coord> list2 = this.getPiecesDisponibles(joueur);
		
		// Cas du pat
		if(list == null && list2 == null) retour = true;
		if(list.size() <= 0 && list2.size() <= 0) retour = true;
		
		list.clear();
		list2.clear();
		list = null;
		list2 = null;
		
		// Impossibilité matérielle
		int piece1 = 0;
		int piece2 = 0;
		int fou1 = 0;
		int fou2 = 0;
		int cavalier1 = 0;
		int cavalier2 = 0;
		
		for(int i = 0; i < Plateau.longueur; i++)
		{
			for(int j = 0; j < Plateau.largeur; j++)
			{
				if(plateau[i][j] != null && plateau[i][j].getType() != typePiece.ROI)
				{
					if(plateau[i][j].getType() == typePiece.FOU || (plateau[i][j].getType() == typePiece.PION && ((Pion)plateau[i][j]).getTypePromu() == typePiece.FOU))
					{
						if(plateau[i][j].getCouleur() == 'B') fou1++;
						else fou2++;
					}
					else if(plateau[i][j].getType() == typePiece.CAVALIER || (plateau[i][j].getType() == typePiece.PION && ((Pion)plateau[i][j]).getTypePromu() == typePiece.CAVALIER))
					{
						if(plateau[i][j].getCouleur() == 'B') cavalier1++;
						else cavalier2++;
					}
					else
					{
						if(plateau[i][j].getCouleur() == 'B') piece1++;
						else piece2++;
					}
				}
			}
		}
		
		if(piece1 == 0 && piece2 == 0)
		{
			if(fou1 == 0 && fou2 == 0 && cavalier1 == 0 && cavalier2 == 0) retour = true;
			if(fou1 == 1 && fou2 == 0 && cavalier1 == 0 && cavalier2 == 0) retour = true;
			if(fou1 == 0 && fou2 == 1 && cavalier1 == 0 && cavalier2 == 0) retour = true;
			if(fou1 == 0 && fou2 == 0 && cavalier1 == 1 && cavalier2 == 0) retour = true;
			if(fou1 == 0 && fou2 == 0 && cavalier1 == 0 && cavalier2 == 1) retour = true;
			if(fou1 == 1 && fou2 == 1 && cavalier1 == 0 && cavalier2 == 0) retour = true;
		}
		
		this.setPartieNulle(retour);
		
		return retour;
	}
	
	// Afficher la pièce aux coordonnées (x, y)
	public String AffichePiece(Coord i)
	{
		return plateau[i.getX()][i.getY()].getName();
	}
	
	// Ajoute une pièce à une coordonnée
	public void AjouterPiece(Piece e, Coord i)
	{
		if(e.getType() == typePiece.ROI && e.getCouleur() == 'B') this.RB.set(i);
		if(e.getType() == typePiece.ROI && e.getCouleur() == 'N') this.RN.set(i);
		plateau[i.getX()][i.getY()] = e;
	}
}
