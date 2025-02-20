package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
		
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	private static class Marche{
		private Etal[] etals;
		
		private Marche(int nb) {
			etals = new Etal[nb];
			for(int i = 0; i < nb ; i++)
				etals[i] = new Etal();
		}
		
		private void utiliserEtal(int indiceEtal,Gaulois vendeur,String produit, int nbProduit) {
			if(indiceEtal>= 0 && indiceEtal <= etals.length) {
				etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
			} else {
				System.out.println("Indice Invalide");
			}
		}
		
		private int trouverEtalLibre() {
			int indice = 0;
			while(etals[indice].isEtalOccupe()  && indice != etals.length) {
				indice ++;
			}
			if(indice == etals.length) {
				System.out.println("Aucun etal libre");
				return -1;
			}
			return indice;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtal = 0;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					nbEtal ++;
				}
			}
			Etal[] liste = new Etal[nbEtal];
			int indice = 0;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					liste[indice] = etals[i];
					indice ++;
				}
			}
			return liste;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			int indice = 0;
			while(etals[indice].getVendeur() != gaulois && indice != etals.length) {
				indice ++;
			}
			return etals[indice];
		}
		
		public String afficherMarche() {
			int indice = 0;
			String chaine = "";
			int nblibre = 0;
			while(indice < etals.length) {
				if(etals[indice].isEtalOccupe()) {
					etals[indice].afficherEtal();
					indice ++;
				} else {
					nblibre ++;
				}
			}
			chaine = "Il reste" + nblibre + "étals non utilisés dans le marché";
			return chaine;
		}
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		System.out.println(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit);
        int etalLibre = marche.trouverEtalLibre();
        if (etalLibre == -1) {
            return "Aucun étal disponible pour " + vendeur.getNom();
        }
        marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
        return "Le vendeur " + vendeur.getNom() + " vend " + nbProduit + " " + produit + " à l'étal n°" + (etalLibre + 1) + "\n";
    }

    public String rechercherVendeursProduit(String produit) {
        Etal[] etals = marche.trouverEtals(produit);
        if (etals.length == 0) {
            return "Il n'y a pas de vendeur qui propose des " + produit + " au marché. \n";
        }
        StringBuilder resultat = new StringBuilder("Les vendeurs qui proposent des " + produit + " sont :\n");
        for (Etal etal : etals) {
            resultat.append("- ").append(etal.getVendeur().getNom()).append("\n");
        }
        return resultat.toString();
    }

    public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }
    
    public String afficherMarche() {
        return marche.afficherMarche();
    }
    
}