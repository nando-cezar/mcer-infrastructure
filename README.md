# MCER Infrastructure

Este repositório contém a configuração de **infraestrutura Kubernetes** para o projeto **MCER** (Microservices Clean Energy Recommendation).  
Organizado com **Kustomize** para permitir variações por ambiente (dev, staging, prod) e rodar via GitOps (ArgoCD / Flux).

---

## 📁 Estrutura de arquivos

```text
mcer-infrastructure/
│
├── base/                              # Manifests genéricos reutilizáveis
│   ├── namespace.yaml                # Namespace compartilhado (mcer-core)
│   ├── common-configmap.yaml         # ConfigMap com variáveis não sensíveis
│   ├── common-secret.yaml            # Secret template (JWT, DB creds, etc)
│   ├── ingress.yaml                 # Ingress central apontando para API Gateway
│   ├── kustomization.yaml           # Kustomization agregando tudo abaixo
│   │
│   ├── auth-service/
│   │   ├── deployment.yaml          # Deployment, probes, resources
│   │   ├── service.yaml             # ClusterIP interno
│   │   ├── hpa.yaml                 # HorizontalPodAutoscaler
│   │   └── serviceaccount.yaml      # ServiceAccount (RBAC)
│   │
│   ├── user-service/
│   │   ├── deployment.yaml
│   │   ├── service.yaml
│   │   ├── hpa.yaml
│   │   └── serviceaccount.yaml
│   │
│   ├── product-service/
│   │   ├── deployment.yaml
│   │   ├── service.yaml
│   │   ├── hpa.yaml
│   │   └── serviceaccount.yaml
│   │
│   ├── company-service/
│   │   ├── deployment.yaml
│   │   ├── service.yaml
│   │   ├── hpa.yaml
│   │   └── serviceaccount.yaml
│   │
│   ├── geoenergy-service/
│   │   ├── deployment.yaml
│   │   ├── service.yaml
│   │   ├── hpa.yaml
│   │   └── serviceaccount.yaml
│   │
│   ├── contract-service/
│   │   ├── deployment.yaml
│   │   ├── service.yaml
│   │   ├── hpa.yaml
│   │   └── serviceaccount.yaml
│   │
│   ├── api-gateway/
│   │   ├── deployment.yaml
│   │   ├── service.yaml
│   │   ├── hpa.yaml
│   │   └── serviceaccount.yaml
│   │
│   └── kafka/                       # Infra de event bus
│       ├── zookeeper.yaml
│       └── kafka.yaml              # StatefulSet + Service
│
├── overlays/                         # Variações por ambiente
│   ├── dev/
│   │   └── kustomization.yaml       # base + patches para dev
│   ├── staging/
│   │   └── kustomization.yaml       # base + patches para staging
│   └── prod/
│       └── kustomization.yaml       # base + patches para produção
│
├── monitoring/                      # Observabilidade
│   ├── prometheus-servicemonitor.yaml  # ServiceMonitor para Prometheus Operator
│   └── grafana-dashboard-configmap.yaml # Dashboards iniciais
│
└── README.md                        # Este arquivo

🚀 Deploy
# selecionar contexto
kubectl config use-context <cluster>

# criar namespace (uma vez)
kubectl apply -f base/namespace.yaml

# deploy ambiente de desenvolvimento
kubectl apply -k overlays/dev

# deploy staging
kubectl apply -k overlays/staging

# deploy produção
kubectl apply -k overlays/prod

🛠 Comandos úteis
kubectl get pods -n mcer-core
kubectl describe deploy auth-service -n mcer-core
kubectl logs -n mcer-core deploy/auth-service
kubectl apply -k overlays/dev
