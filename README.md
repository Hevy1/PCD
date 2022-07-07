# Application TelecomNancy Hiking

TelecomNancy Hiking est une application qui permet de préparer et de partager ses plus belles
promenades et ses parcours de randonnées. 

## Fonctionnalités

* Création de randonnées:<br/>
L'application permet de créer des randonnées deux manières différentes: <br/>
-En important des données situées depuis un fichier _.gpx_ <br/>
-En cliquant directement sur une carte interactive pour placer soit même les différents points du parcours de la randonnée <br/>
<br/>Après cela l'utilisateur peut créer/modifier le titre ainsi que la description de sa randonnée, lui ajouter des mots-clés, choisir la difficulté et ajouter des images liées à cette randonnée. 

* Gestion des randonnées:<br/>
TelecomNancy Hiking propose une interface listant les randonnées enregistrées sur l'application avec un petit aperçu de leur description. Depuis cette interface, il est possible de rechercher des randonnées particulières en fonction des mots-clés qui leurs sont associés ou de leur titre. Des boutons sont également à disposition pour ajouter une randonnée aux favoris, pour consulter une description plus détaillée de la randonnée ou pour la modifier. Les randonnées enregistrées comme favorites sont visibles via une interface semblable où il est possible de réaliser les mêmes actions.


* Consultation des randonnées:<br/>
Les descriptions plus détaillées des randonnées contiennent leur titre, leur description, mots-clés, ainsi que les différentes données techniques associées (Durée, Coordonnées de départ, Distance de marche, Difficulté etc.), un aperçu du trajet de la randonnée sur une carte interactive ainsi qu'un graphique représentant le dénivellement du chemin de la randonnée. Une galerie d'images associées à la randonnée est également affichée.

* Modification de randonnées:<br/>
L'application permet de modifier des randonnées enregistrées dans l'application. L'utilisateur dispose alors d'une interface très semblable à celle de la création d'une randonnée. Cependant la modification entraîne automatiquement la suppression des données sur les altitudes des points (et donc du dénivellement) et il n'est pas possible de retirer des images de la description détaillée d'une randonnée.

* Gestion des données:<br/>
L'application ne dispose pas d'une base de données ou d'une intégration permettant de partager directement ses données via TelecomNancy Hiking. Cependant l'application enregistre toutes les données des randonnées localement pour que l'application puisse récupérer les données sauvegardées lors d'une précédente utilisation.<br/> Ces données sont sauvegardées dans le dossier `.TNHiking` qui se situe dans le répertoire de le dossier `user` de l'utilisateur.

Problèmes notables

* Impossible de supprimer des images de la description d'une randonnée une fois qu'elles sont associées
* Pour l'affichage et la modification de la description d'une randonnée, l'aperçu du trajet de la randonnée ne se fait que si l'utilisateur passe son curseur dessus
* Présence d'importantes fuites mémoires qui ralentissent progressivement l'application si on change trop souvent de scène

## Exécution de l'application

Avant toute chose, il faut se placer à la racine du répertoire du projet.

Ensuite, pour Java 8+, il faut avoir configuré le chemin vers JavaFX en le plaçant dans une variable d'environnement (Comme %PATH_TO_FX%="Mon chemin vers mon JavaFX, ou $PATH_TO_FX sur UNIX).

Enfin on lance dans le répertoire du projet:
_____
    java --module-path %PATH_TO_FX% --add-modules=javafx.controls,javafx.fxml -jar project-grp06.jar 
_____
pour Java 11+ sur Windows
_____
    java --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.fxml -jar project-grp06.jar
_____
pour Java 11+ sur Linux/Mac (à tester)

