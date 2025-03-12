import csv
import numpy as np
from PIL import Image
from astropy.stats import mad_std
from collections import deque
import sys
import os


"""
\var file_path
\brief Fichier contenant le chemin de l'image.
"""
file_path = "cartography/image_aTraiter/output.txt"


def inverse_cor(coordonnees):
    """
    \brief Inverse les coordonnées de l'image verticalement pour corriger l'orientation.
    
    \param coordonnees Les coordonnées de l'image.
    \return Les coordonnées corrigées de l'image. 
    """
    for i in range(len(coordonnees) // 2):
        for j in range(len(coordonnees[0])):
            nv_coordonnee = coordonnees[i][j]
            coordonnees[i][j] = coordonnees[len(coordonnees) - 1 - i][j]
            coordonnees[len(coordonnees) - 1 - i][j] = nv_coordonnee
    return coordonnees

# Détermine les points adjacents (voisinage 8) pour l'exploration
def determine_points_adjacents(point, max_i, max_j):
    i, j = point
    adjacents = []
    for di, dj in [(-1, 0), (1, 0), (0, -1), (0, 1), (-1, -1), (-1, 1), (1, -1), (1, 1)]:
        ni, nj = i + di, j + dj
        if 0 <= ni < max_i and 0 <= nj < max_j:
            adjacents.append((ni, nj))
    return adjacents

# Explore les pixels connectés à partir d'un point de départ pour former une "forme"
def cree_une_forme(start, threshold_mask, explored):
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

# Identifie toutes les formes (groupes de pixels connectés) dans l'image seuillée
def determine_formes(threshold_mask):
    explored = np.zeros_like(threshold_mask, dtype=bool)  # Matrice pour marquer les pixels explorés
    formes = []
    max_i, max_j = threshold_mask.shape
    for i in range(max_i):
        for j in range(max_j):
            if threshold_mask[i, j] and not explored[i, j]:
                formes.append(cree_une_forme((i, j), threshold_mask, explored))
    return formes

# Calcule les barycentres des formes détectées pour déterminer les coordonnées des étoiles
def determine_coordonnees_etoiles(formes):
    cor = []
    for forme in formes:
        points = np.array(forme)
        barycentre = points.mean(axis=0)  # Moyenne des coordonnées pour trouver le centre
        cor.append(barycentre)
    return cor

# Enregistre les coordonnées des étoiles dans un fichier CSV
def enregistre_les_etoiles(coordonneesdesetoiles, image_array):
    coordonnees = []
    for k in range(len(coordonneesdesetoiles)):
        i = float(coordonneesdesetoiles[k][0])
        j = float(coordonneesdesetoiles[k][1])
        coordonnees.append([j, i, image_array[int(i)][int(j)]])  # Inverse les indices pour le format CSV
    with open("recognition/coorPoints/liste_etoiles.csv", "w", newline="", encoding="utf-8") as fichier:
        writer = csv.writer(fichier)
        writer.writerows(coordonnees)

# Vide le fichier output.txt après traitement pour éviter les relectures inutiles
def erase_txt():
    with open(file_path, "w") as f:
        f.write("")  # Vide le fichier
        

class OutputNotFound(FileNotFoundError):
    """
    \class OutputNotFound
    \brief Exception à lever si output.txt n'existe pas.
    """
    pass


class EmptyFile(Exception):
    """
    \class EmptyFile
    \brief Exception à lever si output.txt est vide.
    """
    pass


class ImageNotFound(FileNotFoundError):
    """
    \class ImageNotFound
    \brief Exception à lever si le chemin de l'image n'existe pas.
    """
    pass


def main():
    """
    \brief Fonction principale du scipt.
    Lecture du chemin de l'image à traiter depuis output.txt.

    \exception OutputNotFound levée si output.txt n'existe pas.
    \exception EmptyFile levée si output.txt est vide.
    \exception ImageNotFound levée si l'image n'existe pas.
    \exception Exception levée si le fichier selectionné par l'utilisateur n'est pas une image.
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
                    sigma = mad_std(image_array)  # Calcul du bruit de fond
                    image_final = inverse_cor(image_array)  # Correction d'orientation
                    threshold_mask = image_final > (21.0 * sigma)  # Seuil pour isoler les étoiles

                    # Extraction des formes et calcul des coordonnées des étoiles
                    formes = determine_formes(threshold_mask)
                    coordonneesdesetoiles = determine_coordonnees_etoiles(formes)

                    print("8. Affichage lancé")
                    sys.stdout.flush()

                    '''
                    # Affichage de l'image seuillée avec les étoiles détectées
                    plt.imshow(threshold_mask, cmap='gray', origin='lower')
                    for star in coordonneesdesetoiles:
                        plt.plot(star[1], star[0], 'ro')  # Marque les étoiles en rouge
                    plt.title('Thresholded Image With Stars')
                    plt.show(block=False)  # Ne bloque pas l'exécution du script
                    '''

                    # Sauvegarde des coordonnées des étoiles et réinitialisation des fichiers
                    enregistre_les_etoiles(coordonneesdesetoiles, image_array)
                    print("9. fichier csv créé et rempli")
                    sys.stdout.flush()

                    '''
                    # Boucle pour maintenir la fenêtre ouverte
                    while plt.get_fignums():
                        plt.pause(0.1)  # Pause courte pour éviter de surcharger le CPU
                    '''

                    erase_txt()  # Vide le fichier txt après traitement
                    print("9. fichier txt vidé")
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
    except OutputNotFound as o:
        print(str(o))

if __name__ == '__main__':
    main()