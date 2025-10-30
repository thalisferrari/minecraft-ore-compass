# 🎨 Guia de Criação de Texturas - Ore Compass

Este guia explica como criar as texturas para os 3 tiers de Ore Compass.

---

## 📋 Arquivos Necessários

Você precisa criar 3 arquivos PNG de 16x16 pixels:

```
src/main/resources/assets/ore_compass/textures/item/
├── basic_ore_compass.png
├── advanced_ore_compass.png
└── master_ore_compass.png
```

---

## 🛠️ Ferramentas Recomendadas

### Opção 1: Editores Online (Grátis)
- **Piskel** - https://www.piskelapp.com/
- **Pixilart** - https://www.pixilart.com/draw

### Opção 2: Software Instalável
- **Aseprite** (Pago - $19.99) - https://www.aseprite.org/
- **GIMP** (Grátis) - https://www.gimp.org/
- **Paint.NET** (Grátis - Windows) - https://www.getpaint.net/

---

## 🎯 Especificações Técnicas

**Para cada textura:**
- **Dimensões:** 16x16 pixels (EXATO)
- **Formato:** PNG com canal alpha (transparência)
- **Profundidade:** 32-bit (RGBA)
- **Fundo:** Transparente
- **Estilo:** Pixel art (estilo Minecraft)

---

## 📖 Passo a Passo Detalhado

### **Passo 1: Obter a Textura Base da Bússola**

#### Opção A - Extrair do Minecraft (Recomendado)

1. Navegue até a pasta de versões do Minecraft:
   ```
   C:\Users\[SEU_USUARIO]\AppData\Roaming\.minecraft\versions\1.20.1\
   ```

2. Localize o arquivo `1.20.1.jar`

3. Abra com WinRAR/7-Zip (clique direito → Abrir com...)

4. Navegue até: `assets\minecraft\textures\item\`

5. Extraia o arquivo `compass_00.png` para sua área de trabalho

#### Opção B - Baixar da Internet

1. Acesse: https://minecraft.wiki/
2. Procure por "Compass"
3. Baixe a imagem da bússola
4. Redimensione para 16x16 pixels se necessário

---

### **Passo 2: Criar Basic Ore Compass (Tier 1)**

**Tema:** Bronze/Cobre - Aparência básica e rústica

#### Usando Piskel (Online):

1. Acesse https://www.piskelapp.com/
2. Clique em **"Create Sprite"**
3. Configure: 16x16 pixels
4. Importe a textura da bússola: `File → Import`
5. Modifique as cores:
   - **Aro externo:** Mude de dourado para bronze/cobre
     - Use cores: `#CD7F32` (bronze) ou `#B87333` (cobre)
   - **Agulha:** Mantenha vermelha ou deixe mais escura
   - **Centro:** Adicione um leve tom acinzentado (ferro)
6. Exporte: `File → Export → PNG`
7. Salve como: `basic_ore_compass.png`

#### Paleta de Cores Sugerida:
```
Aro externo:  #B87333 (Cobre)
Sombras:      #8B5A2B (Marrom escuro)
Centro:       #C0C0C0 (Prata/Ferro)
Agulha:       #8B0000 (Vermelho escuro)
```

---

### **Passo 3: Criar Advanced Ore Compass (Tier 2)**

**Tema:** Ouro/Diamante - Aparência refinada e valiosa

#### Modificações:

1. Abra a textura da bússola novamente
2. Modifique as cores:
   - **Aro externo:** Dourado brilhante
     - Use cores: `#FFD700` (ouro) ou `#FFA500` (dourado)
   - **Centro:** Adicione cristal azul (lapis/diamante)
     - Use cores: `#4169E1` ou `#5DADE2` (azul brilhante)
   - **Agulha:** Diamante/branca brilhante
     - Use cores: `#B0E0E6` (azul claro) ou `#FFFFFF` (branco)
   - Adicione brilhos: Pixels brancos nos cantos (efeito de brilho)

#### Paleta de Cores Sugerida:
```
Aro externo:  #FFD700 (Ouro)
Sombras:      #B8860B (Dourado escuro)
Centro:       #4169E1 (Azul royal - Diamante)
Agulha:       #00CED1 (Ciano - Diamante)
Brilhos:      #FFFFFF (Branco)
```

---

### **Passo 4: Criar Master Ore Compass (Tier 3)**

**Tema:** Netherite/Ametista - Aparência épica e poderosa

#### Modificações:

1. Abra a textura da bússola novamente
2. Modifique as cores:
   - **Aro externo:** Netherite (cinza escuro quase preto)
     - Use cores: `#4A4A4A` (cinza escuro) ou `#2F2F2F` (quase preto)
   - **Centro:** Cristal roxo brilhante (ametista/echo shard)
     - Use cores: `#9B59B6` (roxo) ou `#8E44AD` (roxo escuro)
   - **Agulha:** Roxo vibrante com brilho
     - Use cores: `#D946EF` (roxo neon)
   - Adicione partículas roxas: Pixels roxos claros ao redor
   - Efeito de brilho: Pixels brancos/roxos claros estratégicos

#### Paleta de Cores Sugerida:
```
Aro externo:  #3A3A3A (Netherite)
Detalhes:     #1F1F1F (Preto quase)
Centro:       #9B59B6 (Roxo ametista)
Agulha:       #D946EF (Roxo neon)
Brilhos:      #E8DAEF (Roxo clarinho)
Partículas:   #BB8FCE (Roxo claro)
```

---

### **Passo 5: Ajustes Finais**

Para cada textura criada:

1. **Verifique o tamanho:**
   - Deve ser EXATAMENTE 16x16 pixels
   - Use: `Image → Canvas Size` (GIMP/Paint.NET)

2. **Verifique a transparência:**
   - O fundo deve ser transparente (padrão xadrez)
   - Salve como PNG-32 (com canal alpha)

3. **Teste de contraste:**
   - Coloque em fundo preto e branco
   - Certifique-se de que é visível em ambos

4. **Adicione anti-aliasing mínimo:**
   - Use apenas nos contornos principais
   - Não exagere (estilo Minecraft é pixelado)

---

### **Passo 6: Salvar os Arquivos**

1. Salve cada textura com o nome correto:
   ```
   basic_ore_compass.png
   advanced_ore_compass.png
   master_ore_compass.png
   ```

2. Copie para o diretório do mod:
   ```
   D:\Work\projects\minecraft\ore-compass\src\main\resources\assets\ore_compass\textures\item\
   ```

3. Verifique que os arquivos estão no local correto

---

## 🎨 Dicas de Design

### **Princípios de Pixel Art para Minecraft:**

1. **Menos é mais:** Não tente adicionar muitos detalhes
2. **Contraste claro:** Use cores que contrastem bem
3. **Simetria:** Mantenha simetria quando possível
4. **Palette limitada:** Use 4-8 cores por textura
5. **Anti-aliasing sutil:** Use apenas 1-2 tons intermediários

### **Efeitos Visuais:**

#### Brilho (Highlight):
- Adicione pixels brancos (#FFFFFF) nos cantos superiores esquerdos
- Use com moderação (2-3 pixels no máximo)

#### Sombra (Shadow):
- Adicione pixels escuros nos cantos inferiores direitos
- Use versão 50% mais escura da cor principal

#### Borda (Outline):
- Use preto (#000000) ou cinza muito escuro (#1A1A1A)
- Ajuda a definir a forma

---

## ✅ Checklist Final

Antes de testar no jogo:

- [ ] Todos os 3 arquivos PNG foram criados
- [ ] Cada arquivo tem exatamente 16x16 pixels
- [ ] Formato PNG com transparência
- [ ] Nomes dos arquivos estão corretos (minúsculas, underscore)
- [ ] Arquivos estão no diretório correto
- [ ] Cada tier tem visual distinto (cobre/ouro/netherite)
- [ ] Texturas são visíveis em fundos claros e escuros

---

## 🧪 Testando no Minecraft

Após criar as texturas:

1. Rebuild o mod:
   ```bash
   ./gradlew build
   ```

2. Execute o cliente:
   ```bash
   ./gradlew runClient
   ```

3. Entre no modo criativo

4. Abra a aba "Ore Compass"

5. Verifique se as texturas aparecem corretamente

---

## 🚨 Solução de Problemas

### Textura aparece como quadrado roxo/preto:

- **Causa:** Arquivo não foi encontrado
- **Solução:** Verifique o nome do arquivo e caminho

### Textura aparece borrada:

- **Causa:** Arquivo não é 16x16 pixels
- **Solução:** Redimensione para exatamente 16x16

### Textura tem fundo branco:

- **Causa:** PNG sem transparência
- **Solução:** Salve como PNG-32 com canal alpha

### Textura não carrega após rebuild:

- **Causa:** Cache do Gradle
- **Solução:** Execute `./gradlew clean build`

---

## 📚 Recursos Adicionais

### Tutoriais de Pixel Art:
- https://lospec.com/pixel-art-tutorials
- https://blog.studiominiboss.com/pixelart

### Geradores de Paletas:
- https://lospec.com/palette-list
- https://colorhunt.co/

### Referências do Minecraft:
- https://minecraft.wiki/
- https://www.planetminecraft.com/

---

## 🎉 Pronto!

Após seguir este guia, você terá 3 texturas únicas e profissionais para o seu mod Ore Compass!

**Boa sorte e divirta-se criando! 🎮⛏️**
