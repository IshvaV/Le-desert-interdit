# Le Désert Interdit - Java

Une adaptation numérique fidèle du célèbre jeu de société coopératif **"Le Désert Interdit"**, développée en Java avec une architecture MVC.

**[Télécharger le projet Java](https://github.com/IshvaV/Le-desert-interdit/archive/refs/heads/main.zip)**

## 🏜️ Présentation du Projet

Dans ce jeu, vous incarnez une équipe d'aventuriers dont l'hélicoptère s'est écrasé au milieu d'un désert aride. Votre seul espoir : retrouver les pièces d'une machine volante légendaire enfouie sous le sable avant que la tempête ne vous ensevelisse ou que vous ne mouriez de soif.  

**Diagramme de classe** : Dessiné d'abord à la main puis designé à la main. [**DIAGRAMME_CLASSE.md**](DIAGRAMME_CLASSE.md)

## 🚀 Fonctionnalités Implémentées

### 👥 Système de Rôles
Chaque joueur possède un rôle unique avec des capacités spéciales :
*   **L'Archéologue** : Retire 2 marqueurs de sable pour une seule action.
*   **Le Grimpeur** : Ne peut pas être bloqué par le sable et peut franchir des cases encombrées.
*   **L'Explorateur** : Peut se déplacer et creuser en diagonale.
*   **Le Porteur d'eau** : Peut puiser de l'eau dans les oasis et en donner aux collègues à distance.
*   **Le Navigateur** : Peut déplacer un autre joueur pour l'aider dans sa progression.

### 🌪️ Mécaniques de Jeu
*   **Gestion de la Tempête** : Un système de deck réaliste gère les vents, les vagues de chaleur et l'intensification de la tempête.
*   **Exploration dynamique** : Brouillard de guerre masquant les tuiles non explorées.
*   **Localisation des pièces** : Système d'indices croisés (ligne/colonne) pour faire apparaître les pièces de la machine.
*   **Survie** : Gestion stricte de la réserve de sable (48 marqueurs) et des gourdes d'eau.

### 💻 Interface Graphique (GUI)
*   Affichage fluide avec **Swing**.
*   Rendu visuel de l'empilement du sable.
*   Feedback textuel complet via un journal de bord intégré.
*   Scoreboard dynamique mettant en valeur le joueur actif.

## 🏗️ Architecture Technique

Le projet suit le patron de conception **MVC (Modèle-Vue-Contrôleur)** pour assurer une séparation claire entre la logique et l'interface utilisateur.

*   **Modèle** : Gère l'état du désert, les règles de déplacement et les conditions de victoire/défaite.
*   **Vue** : Rendu graphique de la grille, des joueurs et des commandes.
*   **Contrôleur** : Fait le lien entre les interactions utilisateur et le modèle.

## 🛠️ Installation et Lancement

1.  Clonez le dépôt :
    ```bash
    git clone https://github.com/IshvaV/Le-desert-interdit.git
    cd Le-desert-interdit
    ```
2.  Compilez le projet (depuis la racine) :
    ```bash
    javac -d out/production/le-desert-interdit src/**/*.java
    ```
3.  Lancez le jeu :
    ```bash
    java -cp out/production/le-desert-interdit Controleur.Main
    ```

## 📜 Licence

Ce projet a été réalisé dans un cadre pédagogique. Tous les visuels sont inspirés du jeu original de Matt Leacock.  

## 👥 Équipe
* **Manishva VIDJEACOUMAR**
* **Edam DAMMAK**
