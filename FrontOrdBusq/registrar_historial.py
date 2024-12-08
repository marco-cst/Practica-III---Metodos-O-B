import json
from datetime import datetime
import os

def registrar_historial(operacion, entidad, detalle):
    archivo_path = os.path.join("data", "historial.json")
    
    os.makedirs("data", exist_ok=True)

    registro = {
        "operacion": operacion,
        "entidad": entidad,
        "detalle": detalle,
        "fecha_hora": datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    }

    try:
        if os.path.exists(archivo_path):
            with open(archivo_path, "r") as archivo:
                historial = json.load(archivo)
                print("Historial cargado exitosamente.")
        else:
            print("Archivo de historial no encontrado, creando nuevo historial.")
            historial = []
    except (json.JSONDecodeError, IOError) as e:
        print(f"Error al leer el archivo JSON: {e}")
        historial = []
    historial.append(registro)

    try:
        with open(archivo_path, "w") as archivo:
            json.dump(historial, archivo, indent=4)
            print("Historial guardado exitosamente en data/historial.json.")
    except IOError as e:
        print(f"Error al guardar el archivo JSON: {e}")

