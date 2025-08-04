# MCER Infrastructure

Este repositório contém toda a configuração de infraestrutura Kubernetes para o projeto **MCER** (Microservices Clean Energy Recommendation), utilizando **Kustomize** para organização e **manifests YAML** para todos os serviços.

## 📦 Estrutura do Repositório

mcer-infrastructure/
│
├── base/ # Manifests genéricos reutilizáveis para todos os ambientes
│ ├── namespace.yaml
│ ├── common-configmap.yaml
│ ├── common-secret.yaml
│ ├── auth-service/
│ │ ├── deployment.yaml
│ │ ├── service.yaml
│ │ ├── hpa.yaml
│ │ └── serviceaccount.yaml
│ ├── user-service/
│ ├── product-service/
│ ├── company-service/
│ ├── geoenergy-service/
│ ├── contract-service/
│ ├── api-gateway/
│ ├── kafka/
│ │ ├── zookeeper.yaml
│ │ └── kafka.yaml
│ ├── ingress.yaml
│ └── kustomization.yaml
│
├── overlays/ # Customizações por ambiente
│ ├── dev/
│ │ └── kustomization.yaml
│ ├── staging/
│ │ └── kustomization.yaml
│ └── prod/
│ └── kustomization.yaml
│
├── monitoring/
│ ├── prometheus-servicemonitor.yaml
│ └── grafana-dashboard-configmap.yaml
│
└── README.md
