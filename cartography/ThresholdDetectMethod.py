import csv
import matplotlib.pyplot as plt
import numpy as np
from PIL import Image
from astropy.stats import mad_std
from sklearn.preprocessing import MinMaxScaler
from collections import deque
import time  # Importation du module time pour gérer la temporisation
import sys

file_path = "cartography/image_aTraiter/output.txt"  # Fichier contenant le chemin de l'image

#  Inverse les coordonnées verticalement pour corriger l'orientation
def inverse_cor(coordonnees):
    for i in range(len(coordonnees) // 2):
        for j in range(len(coordonnees[0])):
            nv_coordonnee = coordonnees[i][j]
            coordonnees[i][j] = coordonnees[len(coordonnees) - 1 - i][j]
            coordonnees[len(coordonnees) - 1 - i][j] = nv_coordonnee
    return coordonnees

#  Détermine les points adjacents (voisinage 8) pour l'exploration
def determine_points_adjacents(point, max_i, max_j):
    i, j = point
    adjacents = []
    for di, dj in [(-1, 0), (1, 0), (0, -1), (0, 1), (-1, -1), (-1, 1), (1, -1), (1, 1)]:
        ni, nj = i + di, j + dj
        if 0 <= ni < max_i and 0 <= nj < max_j:
            adjacents.append((ni, nj))
    return adjacents

#  Explore les pixels connectés à partir d'un point de départ pour former une "forme"
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

#  Identifie toutes les formes (groupes de pixels connectés) dans l'image seuillée
def determine_formes(threshold_mask):
    explored = np.zeros_like(threshold_mask, dtype=bool)  # Matrice pour marquer les pixels explorés
    formes = []
    max_i, max_j = threshold_mask.shape
    for i in range(max_i):
        for j in range(max_j):
            if threshold_mask[i, j] and not explored[i, j]:
                formes.append(cree_une_forme((i, j), threshold_mask, explored))
    return formes

#  Calcule les barycentres des formes détectées pour déterminer les coordonnées des étoiles
def determine_coordonnees_etoiles(formes):
    cor = []
    for forme in formes:
        points = np.array(forme)
        barycentre = points.mean(axis=0)  # Moyenne des coordonnées pour trouver le centre
        cor.append(barycentre)
    return cor

#  Enregistre les coordonnées des étoiles dans un fichier CSV
def enregistre_les_etoiles(coordonneesdesetoiles, image_array):
    coordonnees = []
    for k in range(len(coordonneesdesetoiles)):
        i = float(coordonneesdesetoiles[k][0])
        j = float(coordonneesdesetoiles[k][1])
        coordonnees.append([j, i, image_array[int(i)][int(j)]])  # Inverse les indices pour le format CSV
    with open("recognition/src/tsp/polaris/cor_Points/liste_etoiles.csv", "w", newline="", encoding="utf-8") as fichier:
        writer = csv.writer(fichier)
        writer.writerows(coordonnees)
    print("Fichier liste_etoiles.csv créé avec succès!")

#  Vide le fichier output.txt après traitement pour éviter les relectures inutiles
def erase_txt():
    with open(file_path, "w") as f:
        f.write("")  # Vide le fichier
    print("Fichier output.txt réécrit")

#  Boucle principale qui surveille les changements du fichier output.txt
while True:
    try:
        # Lit le chemin de l'image à traiter depuis output.txt
        with open(file_path, "r") as f:
            image_path = f.read().strip()  # Relit le fichier à chaque itération

        # Si un chemin est trouvé, traite l'image
        if image_path != "":
            print(f"Traitement de l'image : {image_path}")
            try:
                #  Chargement et normalisation de l'image
                image = Image.open(image_path).convert('L')  # Conversion en niveaux de gris
                image_array = np.array(image, dtype=float)
                image_array /= image_array.max()  # Normalisation des valeurs

                #  Détection des étoiles avec un seuil basé sur l'écart-type robuste (MAD)
                sigma = mad_std(image_array)  # Calcul du bruit de fond
                image_final = inverse_cor(image_array)  # Correction d'orientation
                threshold_mask = image_final > (5.0 * sigma)  # Seuil pour isoler les étoiles

                #  Extraction des formes et calcul des coordonnées des étoiles
                formes = determine_formes(threshold_mask)
                coordonneesdesetoiles = determine_coordonnees_etoiles(formes)

                #  Affichage de l'image seuillée avec les étoiles détectées
                plt.imshow(threshold_mask, cmap='gray', origin='lower')
                for star in coordonneesdesetoiles:
                    plt.plot(star[1], star[0], 'ro')  # Marque les étoiles en rouge
                plt.title('Thresholded Image With Stars')
                plt.show(block=True)  # ✅ Attend la fermeture de la fenêtre

                plt.close()  # Ferme la figure

                sys.exit()  # ✅ Termine le script Python

                #  Sauvegarde des coordonnées des étoiles et réinitialisation du fichier
                enregistre_les_etoiles(coordonneesdesetoiles, image_array)
                erase_txt()  # Vide le fichier après traitement

            except FileNotFoundError:
                print(f"Erreur : L'image {image_path} n'existe pas.")
            except Exception as e:
                print(f"Erreur lors du traitement de l'image : {e}")

    except FileNotFoundError:
        print("Le fichier output.txt n'existe pas encore...")

    #  Pause d'une seconde pour éviter de surcharger le CPU
    time.sleep(1)
