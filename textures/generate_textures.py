#!/usr/bin/env python3
"""
Script para gerar texturas do Ore Compass a partir do compass.piskel
Cria 3 variações: Basic (Cobre), Advanced (Ouro), Master (Netherite)
"""

import json
import base64
from io import BytesIO
from PIL import Image
import os
import sys

# Fix para Windows console encoding
if sys.platform == 'win32':
    import io
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')

# Mapeamento de cores: Original (Vanilla Prateada) -> Variações
COLOR_MAPS = {
    'basic': {
        # Aro externo prateado -> Cobre
        (192, 192, 192): (184, 115, 51),   # #C0C0C0 -> #B87333
        (168, 168, 168): (205, 127, 50),   # #A8A8A8 -> #CD7F32

        # Sombras cinza -> Marrom
        (96, 96, 96): (139, 90, 43),       # #606060 -> #8B5A2B
        (64, 64, 64): (101, 67, 33),       # #404040 -> #654321

        # Agulha vermelha -> Vermelho escuro
        (220, 20, 60): (139, 0, 0),        # #DC143C -> #8B0000
        (255, 0, 0): (165, 42, 42),        # #FF0000 -> #A52A2A

        # Centro branco -> Prata
        (224, 224, 224): (192, 192, 192),  # #E0E0E0 -> #C0C0C0
        (255, 255, 255): (220, 220, 220),  # #FFFFFF -> #DCDCDC
    },

    'advanced': {
        # Aro externo prateado -> Ouro
        (192, 192, 192): (255, 215, 0),    # #C0C0C0 -> #FFD700
        (168, 168, 168): (255, 165, 0),    # #A8A8A8 -> #FFA500

        # Sombras cinza -> Ouro escuro
        (96, 96, 96): (184, 134, 11),      # #606060 -> #B8860B
        (64, 64, 64): (139, 105, 20),      # #404040 -> #8B6914

        # Agulha vermelha -> Ciano (diamante)
        (220, 20, 60): (0, 206, 209),      # #DC143C -> #00CED1
        (255, 0, 0): (64, 224, 208),       # #FF0000 -> #40E0D0

        # Centro branco -> Azul diamante
        (224, 224, 224): (65, 105, 225),   # #E0E0E0 -> #4169E1
        (255, 255, 255): (135, 206, 235),  # #FFFFFF -> #87CEEB
    },

    'master': {
        # Aro externo prateado -> Netherite
        (192, 192, 192): (58, 58, 58),     # #C0C0C0 -> #3A3A3A
        (168, 168, 168): (47, 47, 47),     # #A8A8A8 -> #2F2F2F

        # Sombras cinza -> Preto
        (96, 96, 96): (31, 31, 31),        # #606060 -> #1F1F1F
        (64, 64, 64): (10, 10, 10),        # #404040 -> #0A0A0A

        # Agulha vermelha -> Roxo neon
        (220, 20, 60): (217, 70, 239),     # #DC143C -> #D946EF
        (255, 0, 0): (232, 218, 239),      # #FF0000 -> #E8DAEF

        # Centro branco -> Roxo ametista
        (224, 224, 224): (155, 89, 182),   # #E0E0E0 -> #9B59B6
        (255, 255, 255): (187, 143, 206),  # #FFFFFF -> #BB8FCE
    }
}

def read_piskel_file(filepath):
    """Lê o arquivo .piskel e retorna o objeto JSON"""
    print(f"📖 Lendo arquivo: {filepath}")
    with open(filepath, 'r', encoding='utf-8') as f:
        data = json.load(f)
    return data

def extract_image_from_piskel(piskel_data):
    """Extrai a imagem PNG do base64 no arquivo Piskel"""
    print("🖼️  Extraindo imagem PNG do Piskel...")

    # Navegar na estrutura JSON
    # Nota: layers pode ser uma string JSON que precisa ser parseada
    layers = piskel_data['piskel']['layers'][0]
    if isinstance(layers, str):
        layers = json.loads(layers)

    base64_str = layers['chunks'][0]['base64PNG']

    # Remover o prefixo "data:image/png;base64,"
    base64_str = base64_str.split(',')[1]

    # Decodificar base64 para bytes
    image_bytes = base64.b64decode(base64_str)

    # Criar imagem PIL
    image = Image.open(BytesIO(image_bytes))

    print(f"   ✅ Imagem extraída: {image.size[0]}x{image.size[1]} pixels, modo: {image.mode}")
    return image

def replace_colors(image, color_map):
    """Substitui cores da imagem baseado no mapeamento fornecido"""
    # Converter para RGBA se necessário
    if image.mode != 'RGBA':
        image = image.convert('RGBA')

    # Criar nova imagem
    pixels = image.load()
    new_image = Image.new('RGBA', image.size)
    new_pixels = new_image.load()

    # Substituir cores pixel por pixel
    for y in range(image.size[1]):
        for x in range(image.size[0]):
            r, g, b, a = pixels[x, y]

            # Se pixel é transparente, manter transparente
            if a == 0:
                new_pixels[x, y] = (0, 0, 0, 0)
                continue

            # Procurar cor no mapeamento (ignorar alpha)
            original_color = (r, g, b)

            if original_color in color_map:
                new_r, new_g, new_b = color_map[original_color]
                new_pixels[x, y] = (new_r, new_g, new_b, a)
            else:
                # Se cor não está no mapeamento, manter original
                new_pixels[x, y] = (r, g, b, a)

    return new_image

def save_texture(image, filename, output_dir):
    """Salva a textura no diretório de destino"""
    os.makedirs(output_dir, exist_ok=True)
    filepath = os.path.join(output_dir, filename)
    image.save(filepath, 'PNG')
    print(f"   💾 Salvo: {filepath}")
    return filepath

def validate_texture(filepath):
    """Valida se a textura está correta"""
    img = Image.open(filepath)

    # Verificar tamanho
    if img.size != (16, 16):
        print(f"   ⚠️  AVISO: Tamanho incorreto {img.size}, deveria ser 16x16")
        return False

    # Verificar modo (deve ter canal alpha)
    if img.mode != 'RGBA':
        print(f"   ⚠️  AVISO: Modo incorreto {img.mode}, deveria ser RGBA")
        return False

    print(f"   ✅ Validação OK: 16x16 pixels, modo RGBA")
    return True

def main():
    print("=" * 60)
    print("🎨 GERADOR DE TEXTURAS - ORE COMPASS")
    print("=" * 60)
    print()

    # Caminhos
    piskel_file = "compass.piskel"
    output_dir = "src/main/resources/assets/ore_compass/textures/item"

    # Verificar se arquivo existe
    if not os.path.exists(piskel_file):
        print(f"❌ ERRO: Arquivo {piskel_file} não encontrado!")
        return

    # Ler arquivo Piskel
    piskel_data = read_piskel_file(piskel_file)

    # Extrair imagem original
    original_image = extract_image_from_piskel(piskel_data)

    print()
    print("🔄 Gerando variações...")
    print()

    # Gerar cada variação
    textures = {
        'basic_ore_compass.png': ('basic', '🪨 BASIC (Cobre/Bronze)'),
        'advanced_ore_compass.png': ('advanced', '💎 ADVANCED (Ouro/Diamante)'),
        'master_ore_compass.png': ('master', '🌟 MASTER (Netherite/Ametista)')
    }

    for filename, (color_key, description) in textures.items():
        print(f"{description}")

        # Aplicar mapeamento de cores
        color_map = COLOR_MAPS[color_key]
        new_image = replace_colors(original_image, color_map)

        # Salvar
        filepath = save_texture(new_image, filename, output_dir)

        # Validar
        validate_texture(filepath)
        print()

    print("=" * 60)
    print("✅ CONCLUÍDO! 3 texturas geradas com sucesso!")
    print("=" * 60)
    print()
    print(f"📁 Localização: {output_dir}")
    print()
    print("Próximos passos:")
    print("1. Execute: ./gradlew build")
    print("2. Execute: ./gradlew runClient")
    print("3. Teste no Minecraft!")

if __name__ == "__main__":
    main()
