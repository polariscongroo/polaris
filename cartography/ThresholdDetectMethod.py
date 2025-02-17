import csv
import matplotlib.pyplot as plt
import numpy as np
from PIL import Image
from astropy.stats import mad_std
from sklearn.preprocessing import MinMaxScaler
from collections import deque
import time  # Importation du module time pour gerer la temporisation

file_path = "cartography/image_aTraiter/output.txt"  # Fichier contenant le chemin de l'image
f = open(file_path, "r")
image_path = f.read().strip()
# Supprime les espaces et sauts de ligne inutiles
print("on lit bien le path" + image_path)

while True:
    try:
        # Lire le chemin de l'image depuis le fichier
        if image_path:  # Verifie que le fichier contient bien un chemin valide
            print(f"Traitement de l'image : {image_path}")

            try:
                # Charger l'image
                image = Image.open(image_path).convert('L')  # Convertir en niveau de gris
                image_array = np.array(image, dtype=float)  # Convertir en tableau numpy
                image_array /= image_array.max()  # Normaliser entre 0 et 1

                # L'image etant inversee, on "inverse" la matrice des coordonnees
                def inverse_cor(coordonnees):
                    for i in range(len(coordonnees) // 2):  # Parcourt la moitie des lignes
                        for j in range(len(coordonnees[0])):  # Parcourt les colonnes
                        # echange des elements
                           nv_coordonnee = coordonnees[i][j]
                           coordonnees[i][j] = coordonnees[len(coordonnees) - 1 - i][j]
                           coordonnees[len(coordonnees) - 1 - i][j] = nv_coordonnee
                    return coordonnees

                # Detection des etoiles avec un seuil
                sigma = mad_std(image_array)
                image_final = inverse_cor(image_array)
                threshold_mask = image_final > (13.0 * sigma)
                
                plt.imshow(threshold_mask, cmap='gray', origin='lower')
                plt.title('Thresholded Image With Stars (Red Points)')
                plt.show()
                
                # Fonction pour trouver les points adjacents
                def determine_points_adjacents(point, max_i, max_j):
                    i, j = point
                    adjacents = []
                    for di, dj in [(-1, 0), (1, 0), (0, -1), (0, 1), (-1, -1), (-1, 1), (1, -1), (1, 1)]:
                        ni, nj = i + di, j + dj
                        if 0 <= ni < max_i and 0 <= nj < max_j:
                            adjacents.append((ni, nj))
                    return adjacents

                # Fonction pour creer une forme
                def cree_une_forme(start, threshold_mask, explored):
                    max_i, max_j = threshold_mask.shape
                    queue = deque([start])
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

                # Fonction pour trouver toutes les formes
                def determine_formes(threshold_mask):
                    explored = np.zeros_like(threshold_mask, dtype=bool)
                    formes = []
                    max_i, max_j = threshold_mask.shape
                    for i in range(max_i):
                        for j in range(max_j):
                            if threshold_mask[i, j] and not explored[i, j]:
                                formes.append(cree_une_forme((i, j), threshold_mask, explored))
                    return formes

                # Calculer les coordonnees des etoiles
                def determine_coordonnees_etoiles(formes):
                    cor = []
                    for forme in formes:
                        points = np.array(forme)
                        barycentre = points.mean(axis=0)
                        cor.append(barycentre)
                    return cor
                
                # Afficher les etoiles detectees
                def affiche_les_etoiles():
                    formes = determine_formes(threshold_mask)
                    coordonneesdesetoiles = determine_coordonnees_etoiles(formes)
                    plt.imshow(threshold_mask, cmap='gray', origin='lower')
                    plt.title('Thresholded Image With Stars (Red Points)')
                    for star in coordonneesdesetoiles:
                        plt.plot(star[1], star[0], 'ro')  # Inverser les indices pour matplotlib
                    plt.show()
                
                def enregistre_les_etoiles():
                    formes = determine_formes(threshold_mask)
                    coordonneesdesetoiles = determine_coordonnees_etoiles(formes)
                    coordonnees=[]
                    for k in range(len(coordonneesdesetoiles)):
                        i = float(coordonneesdesetoiles[k][0])
                        j = float(coordonneesdesetoiles[k][1])
                        coordonnees.append([j, i, image_array[int(i)][int(j)]]) # On inverse les indices
                    # On enregistre les coordonnees des etoiles dans un fichier csv
                    with open("baseDDonnees_csv/ara.csv", "w", newline="", encoding="utf-8") as fichier:
                             writer = csv.writer(fichier)
                             writer.writerows(coordonnees)
                    print("Fichier liste_etoiles.csv cree avec succès!")
                    return coordonnees
                '''
                renvoie une liste de triplets [abscisse, ordonnee, luminosite]
                '''

                # Fonction test sur la constellation Cassiopee
                '''
                def trie_simple(liste):
                    max = liste[0]
                    indice = 0
                    for i in range(len(liste)-1):
                        if liste[i+1][2] > max[2]:
                            max = liste[i+1]
                            indice = i+1
                    return indice

                def extraire_etoiles_cassio():
                    formes = determine_formes(threshold_mask)
                    coordonneesdesetoiles = determine_coordonnees_etoiles(formes)
                    coordonnees = []
                    for k in range(len(coordonneesdesetoiles)):
                        i = float(coordonneesdesetoiles[k][0])
                        j = float(coordonneesdesetoiles[k][1])
                        coordonnees.append([j, i, image_array[int(i)][int(j)]]) # On inverse les indices
                    luminosite_max = []
                    for i in range(5):
                        indice = trie_simple(coordonnees)
                        luminosite_max.append(coordonnees[indice])
                        coordonnees.pop(indice)
                    with open("liste_etoiles.csv", "w", newline="", encoding="utf-8") as fichier:
                            writer = csv.writer(fichier)
                            writer.writerow(["Abscisse", "Ordonnee", "Luminosite"])  # En-tête CSV
                            writer.writerows(luminosite_max)

                    print("Fichier liste_etoiles.csv cree avec succès!")
                '''    

                def erase_txt():
                    f = open(file_path,"w")
                    f.close()
                    print("Fichier output.txt reecris")

                enregistre_les_etoiles()
                affiche_les_etoiles()
                erase_txt()
                break  # Quitte la boucle après avoir traite l’image
            except FileNotFoundError:
                print(f"Erreur : L'image {image_path} n'existe pas.")
            except Exception as e:
                print(f"Erreur lors du traitement de l'image : {e}")
    except FileNotFoundError:
        print("Le fichier contenant le chemin de l'image n'existe pas encore...")
    time.sleep(1)  # Attendre 1 seconde avant de reessayer pour eviter de surcharger le CPU