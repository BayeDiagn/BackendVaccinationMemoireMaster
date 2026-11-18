# VaccinApp — Guide de déploiement rapide

## Prérequis

Installer **Docker Desktop** (gratuit) :
👉 https://www.docker.com/products/docker-desktop

Vérifier que Docker fonctionne :
```bash
docker --version
```

---

## Démarrer tout le backend en une commande

```bash
# 1. Cloner le projet
git clone https://github.com/fallmourtada/BackendVaccinationMemoireMaster
cd BackendVaccinationMemoireMaster

# 2. Lancer tous les services
docker compose -f docker-compose-complet.yml up --build
```

> **Premier lancement** : ~15 minutes (téléchargement + compilation)  
> **Lancements suivants** : ~3 minutes (cache Docker)

---

## Services démarrés automatiquement

| Service            | Port  | Rôle                        |
|--------------------|-------|-----------------------------|
| MySQL              | 3306  | Base de données              |
| phpMyAdmin         | 8080  | Interface base de données    |
| Eureka (Discovery) | 8761  | Registre des microservices   |
| Auth Service       | 8081  | Authentification             |
| Auth Service 1     | 8089  | Gestion des tokens           |
| User Service       | 8083  | Gestion des utilisateurs     |
| Appointment Service| 8085  | Rendez-vous vaccination      |
| Vaccination Service| 8088  | Suivi des vaccinations       |
| Vaccine Service    | 8087  | Catalogue des vaccins        |
| Location Service   | 9090  | Centres de vaccination       |
| **API Gateway**    | **9999** | **Point d'entrée principal** |

---

## Vérifier que tout fonctionne

Ouvrir dans un navigateur :
- Eureka (liste des services) → http://localhost:8761
- phpMyAdmin (bases de données) → http://localhost:8080
- Gateway (API principale) → http://localhost:9999

---

## Commandes utiles

```bash
# Arrêter tous les services
docker compose -f docker-compose-complet.yml down

# Redémarrer sans recompiler
docker compose -f docker-compose-complet.yml up

# Voir les logs d'un service
docker logs gateway-service
docker logs user-service

# Voir l'état de tous les conteneurs
docker ps
```

---

## Pour connecter l'application mobile

L'application Flutter se connecte via le gateway sur le port **9999**.

- En local (même machine) : `http://localhost:9999`
- En réseau local : `http://IP_DU_SERVEUR:9999`
- Avec ngrok (accès public) : `ngrok http 9999` → utiliser l'URL fournie
