# changer avec le nom de fichier que tu dois lire (si jamais c'est pas le même)
with open("message.txt", "r") as f:
    message = f.read()

message = message.split("\n")
message = [line.strip() for line in message if line]

# Change ce dico avec les coordonnées correctes que tu veux retrouver (x,y)
correct_dist = [(459,751), (264, 439), (534, 183), (1071, 173), (1069, 544), (723, 716)]

distances = [ [] for _ in range(len(correct_dist)) ]

for line in message:
    x, y, light = line.split(",")
    x = float(x)
    y = float(y)
    for  i in range(len(correct_dist)):
        dist = abs(x - correct_dist[i][0])**2 + abs(y - correct_dist[i][1])**2
        distances[i].append(dist)

most_probable_guesses = []
for i in range(len(distances)):
    min_key = distances[i].index(min(distances[i]))
    most_probable_guesses.append(message[min_key])

print(most_probable_guesses)
