import matplotlib.pyplot as plt
import numpy as np
import json
from PIL import Image
from astropy.stats import mad_std
from sklearn.preprocessing import MinMaxScaler
from collections import deque
import time  # Importation du module time pour gérer la temporisation

file_path = "/Users/chadiaitekioui/Coding/Polaris/polaris/output.txt"  # Fichier contenant le chemin de l'image
f = open(file_path, "r")
image_path = f.read().strip()
# Supprime les espaces et sauts de ligne inutiles
print("on lit bien le path" + image_path)

while True:
    try:
        # Lire le chemin de l'image depuis le fichier
        if image_path:  # Vérifie que le fichier contient bien un chemin valide
            print(f"Traitement de l'image : {image_path}")

            try:
                # Charger l'image
                image = Image.open(image_path).convert('L')  # Convertir en niveau de gris
                image_array = np.array(image, dtype=float)  # Convertir en tableau numpy
                image_array /= image_array.max()  # Normaliser entre 0 et 1

                # Détection des étoiles avec un seuil
                sigma = mad_std(image_array)
                threshold_mask = image_array > (23.0 * sigma)
                plt.imshow(threshold_mask, cmap='gray', origin='lower')
                plt.title('Thresholded Image With Stars (Red Points)')
                plt.show()
                
                # Fonction pour trouver les points adjacents
                def détermine_points_adjacents(point, max_i, max_j):
                    i, j = point
                    adjacents = []
                    for di, dj in [(-1, 0), (1, 0), (0, -1), (0, 1), (-1, -1), (-1, 1), (1, -1), (1, 1)]:
                        ni, nj = i + di, j + dj
                        if 0 <= ni < max_i and 0 <= nj < max_j:
                            adjacents.append((ni, nj))
                    return adjacents

                # Fonction pour créer une forme
                def crée_une_forme(start, threshold_mask, explored):
                    max_i, max_j = threshold_mask.shape
                    queue = deque([start])
                    forme = []
                    while queue:
                        point = queue.popleft()
                        if explored[point]:
                            continue
                        explored[point] = True
                        forme.append(point)
                        for adj in détermine_points_adjacents(point, max_i, max_j):
                            if not explored[adj] and threshold_mask[adj]:
                                queue.append(adj)
                    return forme

                # Fonction pour trouver toutes les formes
                def détermine_formes(threshold_mask):
                    explored = np.zeros_like(threshold_mask, dtype=bool)
                    formes = []
                    max_i, max_j = threshold_mask.shape
                    for i in range(max_i):
                        for j in range(max_j):
                            if threshold_mask[i, j] and not explored[i, j]:
                                formes.append(crée_une_forme((i, j), threshold_mask, explored))
                    return formes

                # Calculer les coordonnées des étoiles
                def détermine_coordonnées_étoiles(formes):
                    cor = []
                    for forme in formes:
                        points = np.array(forme)
                        barycentre = points.mean(axis=0)
                        cor.append(barycentre)
                    return cor

                # Afficher les étoiles détectées
                def affiche_les_étoiles():
                    formes = détermine_formes(threshold_mask)
                    coordonnéesdesétoiles = détermine_coordonnées_étoiles(formes)
                    plt.imshow(threshold_mask, cmap='gray', origin='lower')
                    plt.title('Thresholded Image With Stars (Red Points)')
                    for star in coordonnéesdesétoiles:
                        plt.plot(star[1], star[0], 'ro')  # Inverser les indices pour matplotlib
                    plt.show()


                # Enregistre les coordonnées des étoiles dans un format json
                def enregistre_les_étoiles():
                    formes = détermine_formes(threshold_mask)
                    coordonnéesdesétoiles = détermine_coordonnées_étoiles(formes)
                    coordonnées=[]
                    for k in range(len(coordonnéesdesétoiles)):
                        i = float(coordonnéesdesétoiles[k][0])
                        j = float(coordonnéesdesétoiles[k][1])
                        coordonnées.append([j, i, image_array[int(i)][int(j)]]) # On inverse les indices
                    with open("liste_étoiles.json", "w", encoding="utf-8") as fichier:
                        json.dump(coordonnées, fichier, ensure_ascii=False, indent=1)
                    return coordonnées
                '''
                renvoie une liste de triplets [abscisse, ordonnée, luminosité]
                '''

                # Fonction test sur la constellation Cassiopée
                def trie_simple(liste):
                    max = liste[0]
                    indice = 0
                    for i in range(len(liste)-1):
                        if liste[i+1][2] > max[2]:
                            max = liste[i+1]
                            indice = i+1
                    return indice

                def extraire_étoiles_cassio():
                    formes = détermine_formes(threshold_mask)
                    coordonnéesdesétoiles = détermine_coordonnées_étoiles(formes)
                    coordonnées = []
                    for k in range(len(coordonnéesdesétoiles)):
                        i = float(coordonnéesdesétoiles[k][0])
                        j = float(coordonnéesdesétoiles[k][1])
                        coordonnées.append([j, i, image_array[int(i)][int(j)]]) # On inverse les indices
                    luminosité_max = []
                    for i in range(5):
                        indice = trie_simple(coordonnées)
                        luminosité_max.append(coordonnées[indice])
                        coordonnées.pop(indice)
                    with open("liste_étoiles.json", "w", encoding="utf-8") as fichier:
                        json.dump(luminosité_max, fichier, ensure_ascii=False, indent=1)

                def erase_txt():
                    f = open(file_path,"w")
                    f.close()
                    print("Fichier output.txt réécris")

                extraire_étoiles_cassio()
                affiche_les_étoiles()
                erase_txt()
                break  # Quitte la boucle après avoir traité l’image
            except FileNotFoundError:
                print(f"Erreur : L'image {image_path} n'existe pas.")
            except Exception as e:
                print(f"Erreur lors du traitement de l'image : {e}")
    except FileNotFoundError:
        print("Le fichier contenant le chemin de l'image n'existe pas encore...")
    time.sleep(1)  # Attendre 1 seconde avant de réessayer pour éviter de surcharger le CPU