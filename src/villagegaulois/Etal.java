package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
        if (!etalOccupe) {
            throw new IllegalStateException("Impossible de lib�rer un �tal qui n'a jamais �t� occup� !");
        }
        etalOccupe = false;
        StringBuilder chaine = new StringBuilder(
                "Le vendeur " + vendeur.getNom() + " quitte son �tal, ");
        int produitVendu = quantiteDebutMarche - quantite;
        if (produitVendu > 0) {
            chaine.append("il a vendu " + produitVendu + " " + produit + ".\n");
        } else {
            chaine.append("il n'a malheureusement rien vendu.\n");
        }
        return chaine.toString();
    }

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
        if (acheteur == null) {
            throw new NullPointerException("L'acheteur ne peut pas �tre null !");
        }
        if (quantiteAcheter <= 0) {
            throw new IllegalArgumentException("La quantit� achet�e doit �tre sup�rieure � 0 !");
        }
        if (!etalOccupe) {
            throw new IllegalStateException("Impossible d'acheter sur un �tal non occup� !");
        }

        StringBuilder chaine = new StringBuilder();
        chaine.append(acheteur.getNom()).append(" veut acheter ").append(quantiteAcheter)
                .append(" ").append(produit).append(" � ").append(vendeur.getNom());

        if (quantite == 0) {
            chaine.append(", malheureusement il n'y en a plus !");
            return chaine.toString();
        }
        if (quantiteAcheter > quantite) {
            chaine.append(", comme il n'y en a plus que ").append(quantite).append(", ")
                    .append(acheteur.getNom()).append(" vide l'�tal de ")
                    .append(vendeur.getNom()).append(".\n");
            quantiteAcheter = quantite;
            quantite = 0;
        } else {
            quantite -= quantiteAcheter;
            chaine.append(". ").append(acheteur.getNom())
                    .append(" est ravi de tout trouver sur l'�tal de ")
                    .append(vendeur.getNom()).append("\n");
        }

        return chaine.toString();
    }

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}

}
