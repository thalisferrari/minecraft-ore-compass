# 🎨 Como Gerar as Texturas do Ore Compass

Este arquivo contém as instruções para executar o script `generate_textures.py` que cria automaticamente as 3 texturas do mod a partir do arquivo `compass.piskel`.

---

## 📋 Pré-requisitos

### 1. Python Instalado
Verifique se o Python está instalado:
```bash
python --version
```

Se não estiver instalado, instale via:
```bash
winget install Python.Python.3.12
```

### 2. Biblioteca Pillow
Instale a biblioteca de manipulação de imagens:
```bash
python -m pip install Pillow
```

Ou se `python` não funcionar, tente:
```bash
py -m pip install Pillow
```

---

## 🚀 Executar o Script

### Opção 1: Comando Direto
Abra o terminal na pasta do projeto e execute:

```bash
cd D:\Work\projects\minecraft\ore-compass
python generate_textures.py
```

### Opção 2: Usando py launcher (Windows)
```bash
cd D:\Work\projects\minecraft\ore-compass
py generate_textures.py
```

### Opção 3: PowerShell
```powershell
cd D:\Work\projects\minecraft\ore-compass
python.exe .\generate_textures.py
```

---

## ✅ Output Esperado

Quando o script rodar com sucesso, você verá:

```
============================================================
🎨 GERADOR DE TEXTURAS - ORE COMPASS
============================================================

📖 Lendo arquivo: compass.piskel
🖼️  Extraindo imagem PNG do Piskel...
   ✅ Imagem extraída: 16x16 pixels, modo: RGBA

🔄 Gerando variações...

🪨 BASIC (Cobre/Bronze)
   💾 Salvo: src/main/resources/assets/ore_compass/textures/item/basic_ore_compass.png
   ✅ Validação OK: 16x16 pixels, modo RGBA

💎 ADVANCED (Ouro/Diamante)
   💾 Salvo: src/main/resources/assets/ore_compass/textures/item/advanced_ore_compass.png
   ✅ Validação OK: 16x16 pixels, modo RGBA

🌟 MASTER (Netherite/Ametista)
   💾 Salvo: src/main/resources/assets/ore_compass/textures/item/master_ore_compass.png
   ✅ Validação OK: 16x16 pixels, modo RGBA

============================================================
✅ CONCLUÍDO! 3 texturas geradas com sucesso!
============================================================

📁 Localização: src/main/resources/assets/ore_compass/textures/item

Próximos passos:
1. Execute: ./gradlew build
2. Execute: ./gradlew runClient
3. Teste no Minecraft!
```

---

## 📁 Arquivos Gerados

Após a execução, 3 arquivos PNG serão criados em:
```
src/main/resources/assets/ore_compass/textures/item/
├── basic_ore_compass.png       (Tema Cobre/Bronze)
├── advanced_ore_compass.png    (Tema Ouro/Diamante)
└── master_ore_compass.png      (Tema Netherite/Ametista)
```

---

## 🐛 Solução de Problemas

### Erro: "Python não foi encontrado"

**Causa:** Python não está no PATH do sistema

**Soluções:**

#### Opção A: Usar caminho completo
Encontre onde o Python foi instalado:
```bash
where python
```

Depois execute com caminho completo:
```bash
"C:\Users\SEU_USUARIO\AppData\Local\Programs\Python\Python312\python.exe" generate_textures.py
```

#### Opção B: Adicionar ao PATH manualmente
1. Procure "Variáveis de Ambiente" no Windows
2. Edite a variável PATH
3. Adicione: `C:\Users\SEU_USUARIO\AppData\Local\Programs\Python\Python312`
4. Reinicie o terminal

#### Opção C: Reinstalar Python marcando "Add to PATH"
1. Desinstale Python atual
2. Baixe do site oficial: https://www.python.org/downloads/
3. Marque ☑️ "Add Python to PATH" durante instalação
4. Instale normalmente

---

### Erro: "No module named 'PIL'"

**Causa:** Biblioteca Pillow não está instalada

**Solução:**
```bash
python -m pip install Pillow
```

Ou:
```bash
py -m pip install Pillow
```

---

### Erro: "compass.piskel não encontrado"

**Causa:** Script não está sendo executado no diretório correto

**Solução:**
```bash
cd D:\Work\projects\minecraft\ore-compass
python generate_textures.py
```

---

### Texturas não aparecem no Minecraft

**Causa:** Build não foi atualizado após gerar texturas

**Solução:**
```bash
./gradlew clean build
./gradlew runClient
```

---

## 🎨 Personalizar Cores

Se quiser modificar as paletas de cores, edite o arquivo `generate_textures.py`:

```python
COLOR_MAPS = {
    'basic': {
        # Modificar cores aqui
        (192, 192, 192): (184, 115, 51),  # Prata -> Cobre
        # ...
    },
    'advanced': {
        # Modificar cores aqui
    },
    'master': {
        # Modificar cores aqui
    }
}
```

**Formato:** `(R, G, B): (R_novo, G_novo, B_novo)`

---

## 📝 Notas

- O script lê o arquivo `compass.piskel` na raiz do projeto
- Extrai a imagem PNG embutida no arquivo
- Substitui cores pixel por pixel baseado nos mapeamentos definidos
- Salva as 3 variações no diretório correto do mod
- Valida automaticamente tamanho (16x16) e transparência

---

## 🔄 Re-gerar Texturas

Para re-gerar as texturas (por exemplo, após modificar o `compass.piskel`):

1. Edite `compass.piskel` no Piskel Editor
2. Salve o arquivo
3. Execute o script novamente:
   ```bash
   python generate_textures.py
   ```
4. Rebuild o mod:
   ```bash
   ./gradlew clean build
   ```

---

## ✨ Pronto!

Após gerar as texturas e fazer o build, as 3 variações de Ore Compass aparecerão no jogo com suas cores distintas!

**Dúvidas?** Consulte o arquivo `TEXTURE_GUIDE.md` para mais informações sobre criação de texturas.
