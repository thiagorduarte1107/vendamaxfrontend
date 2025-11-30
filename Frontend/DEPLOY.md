# 🚀 Guia de Deploy - VendaMax

## 📋 Opções de Deploy

### 🥇 Recomendado: Vercel (Melhor para Angular)

#### Vantagens:
- ✅ Deploy automático a cada push
- ✅ Preview de PRs
- ✅ CDN global
- ✅ SSL grátis
- ✅ Domínio customizado grátis
- ✅ Zero configuração
- ✅ Builds otimizados
- ✅ Analytics integrado

#### Como Fazer:

**Opção 1: Via GitHub (Recomendado)**
1. Acesse [vercel.com](https://vercel.com)
2. Faça login com GitHub
3. Clique em "New Project"
4. Selecione o repositório `anota-z`
5. Configure:
   - Framework: Angular
   - Build Command: `npm run build`
   - Output Directory: `dist/anota-ai-angular/browser`
6. Clique em "Deploy"

**Opção 2: Via CLI**
```bash
# Instalar Vercel CLI
npm i -g vercel

# Deploy
vercel

# Deploy para produção
vercel --prod
```

**URL Final:** `https://vendamax.vercel.app`

---

### 🥈 GitHub Pages (Grátis)

#### Vantagens:
- ✅ Totalmente grátis
- ✅ Integrado ao GitHub
- ✅ SSL automático
- ✅ Domínio customizado

#### Desvantagens:
- ❌ Apenas sites estáticos
- ❌ Sem server-side rendering
- ❌ Build manual ou via Actions

#### Como Fazer:

**1. Habilitar GitHub Pages:**
- Vá em Settings → Pages
- Source: Deploy from a branch
- Branch: `gh-pages`
- Folder: `/ (root)`

**2. O workflow já está configurado!**
- Arquivo: `.github/workflows/deploy.yml`
- Deploy automático a cada push na master

**3. Aguardar build**
- Actions → Deploy VendaMax
- Aguardar conclusão

**URL Final:** `https://thiagorduarte1107.github.io/anota-z/`

---

### 🥉 Netlify (Alternativa)

#### Vantagens:
- ✅ Deploy automático
- ✅ Preview de PRs
- ✅ SSL grátis
- ✅ Formulários integrados
- ✅ Funções serverless

#### Como Fazer:

**1. Via GitHub:**
- Acesse [netlify.com](https://netlify.com)
- Login com GitHub
- New site from Git
- Selecione `anota-z`
- Configure:
  - Build command: `npm run build`
  - Publish directory: `dist/anota-ai-angular/browser`

**2. Via CLI:**
```bash
# Instalar Netlify CLI
npm i -g netlify-cli

# Login
netlify login

# Deploy
netlify deploy

# Deploy para produção
netlify deploy --prod
```

**URL Final:** `https://vendamax.netlify.app`

---

### 🏢 Outras Opções

#### Firebase Hosting
```bash
npm i -g firebase-tools
firebase login
firebase init hosting
firebase deploy
```

#### Azure Static Web Apps
- Integração direta com GitHub
- SSL grátis
- CDN global

#### AWS Amplify
- Deploy automático
- CI/CD integrado
- Domínio customizado

---

## 🎯 Comparação Rápida

| Plataforma | Grátis | Deploy Auto | SSL | CDN | Domínio Custom | Recomendação |
|------------|--------|-------------|-----|-----|----------------|--------------|
| **Vercel** | ✅ | ✅ | ✅ | ✅ | ✅ | ⭐⭐⭐⭐⭐ |
| **GitHub Pages** | ✅ | ✅ | ✅ | ✅ | ✅ | ⭐⭐⭐⭐ |
| **Netlify** | ✅ | ✅ | ✅ | ✅ | ✅ | ⭐⭐⭐⭐ |
| **Firebase** | ✅ | ⚠️ | ✅ | ✅ | ✅ | ⭐⭐⭐ |
| **Azure** | ⚠️ | ✅ | ✅ | ✅ | ✅ | ⭐⭐⭐ |
| **AWS** | ⚠️ | ✅ | ✅ | ✅ | ✅ | ⭐⭐⭐ |

---

## 🎨 Configurações Importantes

### Angular Build Otimizado

```json
// angular.json
{
  "configurations": {
    "production": {
      "optimization": true,
      "outputHashing": "all",
      "sourceMap": false,
      "namedChunks": false,
      "extractLicenses": true,
      "vendorChunk": false,
      "buildOptimizer": true,
      "budgets": [
        {
          "type": "initial",
          "maximumWarning": "2mb",
          "maximumError": "5mb"
        }
      ]
    }
  }
}
```

### Variáveis de Ambiente

```typescript
// src/environments/environment.prod.ts
export const environment = {
  production: true,
  apiUrl: 'https://api.vendamax.app',
  version: '1.0.0'
};
```

---

## 🔧 Troubleshooting

### Erro 404 ao recarregar página

**Solução para Vercel/Netlify:**
Já configurado no `vercel.json`

**Solução para GitHub Pages:**
Adicionar `404.html` copiando `index.html`

### Build falha

```bash
# Limpar cache
rm -rf node_modules package-lock.json
npm install

# Build local
npm run build -- --configuration production
```

### Tamanho do bundle muito grande

```bash
# Analisar bundle
npm run build -- --configuration production --stats-json
npx webpack-bundle-analyzer dist/anota-ai-angular/browser/stats.json
```

---

## ✅ Checklist de Deploy

- [ ] Build local funciona
- [ ] Testes passam
- [ ] Variáveis de ambiente configuradas
- [ ] SSL habilitado
- [ ] Domínio customizado (opcional)
- [ ] Analytics configurado (opcional)
- [ ] SEO otimizado
- [ ] Performance > 90 no Lighthouse

---

## 🎯 Recomendação Final

### Para VendaMax, recomendo:

**1ª Opção: Vercel** ⭐⭐⭐⭐⭐
- Melhor performance
- Deploy mais rápido
- Analytics grátis
- Preview de PRs
- Zero configuração

**2ª Opção: GitHub Pages** ⭐⭐⭐⭐
- Totalmente grátis
- Integrado ao repositório
- Workflow já configurado
- Bom para projetos open source

**3ª Opção: Netlify** ⭐⭐⭐⭐
- Similar ao Vercel
- Boas funcionalidades extras
- Ótima documentação

---

## 🚀 Deploy Rápido (Vercel)

```bash
# 1. Instalar Vercel CLI
npm i -g vercel

# 2. Login
vercel login

# 3. Deploy
vercel

# 4. Deploy produção
vercel --prod
```

**Pronto! Seu site estará no ar em menos de 2 minutos!** 🎉

---

**VendaMax Deploy Guide v1.0**  
**Última atualização:** Novembro 2025
