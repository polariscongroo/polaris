# changer avec le nom de fichier que tu dois lire (si jamais c'est pas le même)
with open("baseDDonnees_csv/auriga.csv", "r") as f:
    message = f.read()

message = message.split("\n")
message = [line.strip() for line in message if line]

# Change ce dico avec les coordonnées correctes que tu veux retrouver (x,y)
correct_dist = [(388,586), (126.67, 877.08), (427.6, 1000), (478.6, 433.68), (1056, 277), (532, 58.65)]

distances = {i: [] for i in range(len(correct_dist))}

for line in message:
    x, y, light = line.split(",")
    x = float(x)
    y = float(y)
    for i in range(len(correct_dist)):
        dist = abs(x - correct_dist[i][0])**2 + abs(y - correct_dist[i][1])**2
        distances[i].append((dist, line))

most_probable_guesses = []
for i in range(len(distances)):
    min_dist, min_line = min(distances[i], key=lambda t: t[0])
    most_probable_guesses.append(min_line)

print(most_probable_guesses)