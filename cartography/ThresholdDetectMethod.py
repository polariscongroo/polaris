import csv
import numpy as np
from PIL import Image
from astropy.stats import mad_std
from collections import deque
import matplotlib.pyplot as plt
import sys
import os

"""
@file ThresholdDetectMethod.py
@brief Script python qui traite l'image et écrit les coordonnées des étoiles dans un csv pour la partie reconnaissance
@author Beryl S., Ryane S., Chadi A.
"""

"""
@var file_path
@brief Variable contenant le chemin du fichier où est stocké le path de l'image.
"""
file_path = "cartography/image_aTraiter/output.txt"



def inverse_cor(coordonnees):
    """
    @brief Inverse les coordonnées de l'image verticalement pour corriger l'orientation.
    
    @param coordonnees Les coordonnées de l'image.
    
    @return coordonnees Les coordonnées corrigées de l'image. 
    """
    for i in range(len(coordonnees) // 2):
        for j in range(len(coordonnees[0])):
            nv_coordonnee = coordonnees[i][j]
            coordonnees[i][j] = coordonnees[len(coordonnees) - 1 - i][j]
            coordonnees[len(coordonnees) - 1 - i][j] = nv_coordonnee
    return coordonnees


def determine_points_adjacents(point, max_i, max_j):
    """
    @brief Détermine les points adjacents (voisinage 8) pour l'exploration.

    @param point Le point dont on veut déterminer les voisins.   
    @param max_i La hauteur de l'image.
    @param max_j La largeur de l'image.

    @return adjacents Liste des points adjacents au point donné en argument.
    """
    i, j = point
    adjacents = []
    for di, dj in [(-1, 0), (1, 0), (0, -1), (0, 1), (-1, -1), (-1, 1), (1, -1), (1, 1)]:
        ni, nj = i + di, j + dj
        if 0 <= ni < max_i and 0 <= nj < max_j:
            adjacents.append((ni, nj))
    return adjacents


def cree_une_forme(start, threshold_mask, explored):
    """
    @brief Explore les pixels connectés à partir d'un point de départ pour former une "forme".
    
    @param start Le point de départ de la forme.  
    @param threshold_mask L'image obtenue après seuillage et boolean indexing.
    @param explored La matrice de pointage des points explorés.
    
    @return forme L'ensemble des points adjacents constitutifs d'une forme (étoile).
    """
    max_i, max_j = threshold_mask.shape
    queue = deque([start])  # File pour l'exploration en largeur (BFS)
    forme = []
    while queue:
        point = queue.popleft()
        if explored[point]:
            continue
        explored[point] = True
        forme.append(point)
        for adj in determine_points_adjacents(point, max_i, max_j):
            if not explored[adj] and threshold_mask[adj]:
                queue.append(adj)
    return forme

def determine_formes(threshold_mask):
    """
    @brief Identifie toutes les formes (groupes de pixels connectés) dans l'image seuillée.

    @param threshold_mask L'image obtenue après seuillage et boolean indexing.

    @return formes L'ensemble des formes (étoiles/ensemble de points lumineux) repéré sur l'image. 
    """
    explored = np.zeros_like(threshold_mask, dtype=bool)  # Matrice pour marquer les pixels explorés
    formes = []
    max_i, max_j = threshold_mask.shape
    for i in range(max_i):
        for j in range(max_j):
            if threshold_mask[i, j] and not explored[i, j]:
                formes.append(cree_une_forme((i, j), threshold_mask, explored))
    return formes

def determine_coordonnees_etoiles(formes):
    """
    @brief Calcule les barycentres des formes détectées pour déterminer les coordonnées des étoiles.

    @param formes L'ensemble des formes (étoiles/ensemble de points lumineux) repéré sur l'image. 

    @return cor L'ensemble des coordonnées exactes des étoiles détectées.
    """
    cor = []
    for i in range(len(formes)):
        points = np.array(formes[i])
        barycentre = points.mean(axis=0)
        cor.append([barycentre, len(formes[i])])
    return cor

def attribue_luminosite(coordonnees_des_etoiles, image_array):
    """
    @brief Rajoute les luminosités des étoiles.

    @param coordonnées_des_etoiles L'ensemble des coordonnées exactes des étoiles détectées.
    @param image_array La matrice des intensités lumineuses générées avec Pillow (Notre Image).

    @return etoiles La liste des étoiles mise à jour.
    """
    etoiles = []
    for k in range(len(coordonnees_des_etoiles)):
            i = float(coordonnees_des_etoiles[k][0][0])
            j = float(coordonnees_des_etoiles[k][0][1])
            taille = int(coordonnees_des_etoiles[k][1])
            etoiles.append([i, j, taille, image_array[int(i),int(j)]])
    return etoiles

def classe_les_etoiles(etoiles):
    """
    @brief Classe les étoiles selon le produit de leur taille (de forme) par leur luminosité.

    @param etoiles La liste des étoiles.

    @return etoiles Liste triée des étoiles par ordre décroissant du produit taille * luminosité.
    """
    for k in range(len(etoiles)):
        produit = etoiles[k][2] * etoiles[k][3] 
        etoiles[k].append(produit)
    etoiles.sort(key=lambda x: x[4], reverse=True)
    return etoiles

def enregistre_les_etoiles(etoiles_classees):
    """
    @brief Enregistre les coordonnées des étoiles dans un fichier CSV.
    Une ligne contient 5 éléments:
    1- L'abscisse de l'étoile
    2- L'ordonnée de l'étoile
    3- La taille de la forme à laquelle l'étoile appartient
    4- La luminosité de l'étoile
    5- Produit taille * luminosité de l'étoile

    @param etoiles_classees L'ensemble des étoiles détectées et classées.
    """
    etoiles_a_enregistrer = []
    for k in range(len(etoiles_classees)):
        i = float(etoiles_classees[k][0])
        j = float(etoiles_classees[k][1])
        taille_forme = int(etoiles_classees[k][2])
        lum = float(etoiles_classees[k][3])
        produit_taille_lum = float(etoiles_classees[k][4])
        etoiles_a_enregistrer.append([j, i, taille_forme, lum, produit_taille_lum])  # Inverse les indices pour le format CSV
    with open("recognition/coorPoints/liste_etoiles.csv", "w", newline="", encoding="utf-8") as fichier:
        writer = csv.writer(fichier)
        writer.writerows(etoiles_a_enregistrer)

def erase_txt():
    """
    @brief Vide le fichier output.txt après traitement pour éviter les relectures inutiles.
    """
    with open(file_path, "w") as f:
        f.write("")  # Vide le fichier
        

class OutputNotFound(FileNotFoundError):
    """
    @class OutputNotFound
    @brief Exception à lever si output.txt n'existe pas.
    """
    pass


class EmptyFile(Exception):
    """
    @class EmptyFile
    @brief Exception à lever si output.txt est vide.
    """
    pass


class ImageNotFound(FileNotFoundError):
    """
    @class ImageNotFound
    @brief Exception à lever si le chemin de l'image n'existe pas.
    """
    pass


def main():
    """
    @brief Fonction principale du scipt.
    Lecture du chemin de l'image à traiter depuis output.txt.

    @exception OutputNotFound levée si output.txt n'existe pas.
    @exception EmptyFile levée si output.txt est vide.
    @exception ImageNotFound levée si l'image n'existe pas.
    @exception Exception levée si le fichier selectionné par l'utilisateur n'est pas une image.
    """
    try:
        # Vérifie l'existence de output.txt
        if not os.path.exists(file_path):
            raise(OutputNotFound("Le fichier output.txt n'existe pas encore..."))
        
        # Lecture de output.txt
        else:
            with open(file_path, "r") as f:
                image_path = f.read().strip()  # Relit le fichier à chaque itération
            print("6. Dans la boucle du python. Lecture de output.txt réalisée")
            sys.stdout.flush()

            # Vérifie que output.txt contient le chemin de l'image
            if image_path == "":
                raise(EmptyFile("Le fichier output.txt est vide"))

            # Vérifie l'existence du chemin de l'image
            elif not os.path.exists(image_path):
                raise(ImageNotFound(f"L'image {image_path} n'existe pas."))

            # Traitement de l'image
            else:
                print(f"Traitement de l'image : {image_path}")
                try:
                    print("7. Image bien reçu par python, traitement en cours")
                    sys.stdout.flush()

                    # Chargement et normalisation de l'image
                    image = Image.open(image_path).convert('L')  # Conversion en niveaux de gris
                    image_array = np.array(image, dtype=float)
                    image_array /= image_array.max()  # Normalisation des valeurs

                    # Détection des étoiles avec un seuil basé sur l'écart-type robuste (MAD)
                    # sigma = mad_std(image_array)  # Calcul du bruit de fond
                    image_final = inverse_cor(image_array)  # Correction d'orientation
                    threshold_mask = image_final >= 0.7  # Seuil pour isoler les étoiles (à régler manuellement)

                    # Extraction des formes et calcul des coordonnées des étoiles
                    formes = determine_formes(threshold_mask)
                    formes.sort(key=len, reverse=True) # La forme contenant le plus de points lumineux est placée en tête de liste
                    coordonnees_des_etoiles = determine_coordonnees_etoiles(formes)
                    coordonnees_et_lum_des_etoiles = attribue_luminosite(coordonnees_des_etoiles, image_array)
                    etoiles_classees = classe_les_etoiles(coordonnees_et_lum_des_etoiles)

                    print("8. Affichage lancé")
                    sys.stdout.flush()

                    '''
                    # Affichage de l'image seuillée avec les étoiles détectées
                    plt.imshow(threshold_mask, cmap='gray', origin='lower')
                    for star in coordonnees_des_etoiles:
                        plt.plot(star[0][1], star[0][0], 'ro')  # Marque les étoiles en rouge
                    plt.title('Thresholded Image With Stars')
                    plt.show(block=True)  # Ne bloque pas l'exécution du script
                    '''

                    # Sauvegarde des coordonnées des étoiles et réinitialisation des fichiers
                    enregistre_les_etoiles(etoiles_classees)
                    print("9. fichier csv créé et rempli")
                    sys.stdout.flush()
                    
                    '''
                    # Boucle pour maintenir la fenêtre ouverte
                    while plt.get_fignums():
                        plt.pause(0.1)  # Pause courte pour éviter de surcharger le CPU
                    '''

                    erase_txt()  # Vide le fichier txt après traitement
                    print("10. fichier txt vidé")
                    sys.stdout.flush()
                    
                    '''
                    plt.close()  # Ferme la figure
                    '''

                    sys.exit()  # Termine le script Python

                except EmptyFile as e:
                    print(str(e))
                except ImageNotFound as i:
                    print(str(i))
                except Exception as e:
                    print(f"Erreur lors du traitement de l'image {image_path}")
                    print(e)
    except OutputNotFound as o:
        print(str(o))

if __name__ == '__main__':
    main()