# StageConnect CI/CD Pipeline

## Overview
This document explains the GitLab CI/CD pipeline configuration for the StageConnect project, which automates testing, linting, and deployment of both frontend and backend services.

## Pipeline Stages

### 1. Test Stage
- **backend-test**
  - Runs Maven tests for the Spring Boot backend
  - Caches Maven dependencies using `pom.xml` as the cache key
  - Uses the `stage` Maven profile

### 2. Lint Stage
- **frontend-lint**
  - Installs Node.js dependencies
  - Runs ESLint for code quality checks
  - Caches `node_modules` using `package-lock.json` as the cache key

### 3. Publish Stage
- Uses Kaniko for secure, Docker-in-Docker free image building
- **backend-publish**
  - Builds backend Docker image from [Backend/stage/Dockerfile](cci:7://file:///c:/Users/Usuario/OneDrive/Desktop/Stageconnect/Backend/stage/Dockerfile:0:0-0:0)
  - Pushes to `$CI_REGISTRY_IMAGE/backend:$CI_COMMIT_SHORT_SHA`
  - Tags as `latest` on default branch

- **frontend-publish**
  - Builds frontend Docker image from [Frontend/Dockerfile](cci:7://file:///c:/Users/Usuario/OneDrive/Desktop/Stageconnect/Frontend/Dockerfile:0:0-0:0)
  - Pushes to `$CI_REGISTRY_IMAGE/frontend:$CI_COMMIT_SHORT_SHA`
  - Tags as `latest` on default branch

## Cache Strategy
- Backend: Caches Maven dependencies in `Backend/stage/.m2/repository`
- Frontend: Caches Node.js dependencies in [Frontend/node_modules/](cci:7://file:///c:/Users/Usuario/OneDrive/Desktop/Stageconnect/Frontend/node_modules:0:0-0:0)

## Required Environment Variables
- `CI_REGISTRY`: GitLab Container Registry URL
- `CI_REGISTRY_IMAGE`: Container registry image path
- `CI_REGISTRY_USER`: Container registry username
- `CI_REGISTRY_PASSWORD`: Container registry password

## Best Practices
1. Caches are automatically invalidated when dependency files change
2. Uses Kaniko for secure image building
3. Runs jobs in parallel when possible
4. Follows GitLab's recommended practices for container registry usage

## Local Development
```bash
# Run backend tests
cd Backend/stage
./mvnw test

# Run frontend linting
cd Frontend
npm install
npm run lint