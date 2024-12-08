from flask import Flask, json, flash, Blueprint, url_for, jsonify, make_response, request, render_template, redirect, abort
import requests
import registrar_historial
import datetime
from urllib.parse import quote

router = Blueprint('router', __name__)

@router.route('/admin')
def admin():
    return render_template('index.html')

@router.route('/admin/inversionista/list')
def list_inversionista():
    r = requests.get("http://localhost:8020/api/inversionista/list")
    data = r.json()
    return render_template('fragmento/inversionista/lista.html', list=data["data"])

@router.route('/admin/inversionista/edit/<int:id>')
def view_edit_inversionista(id):
    r = requests.get("http://localhost:8020/api/inversionista/list")
    data = r.json()
    r1 = requests.get(f"http://localhost:8020/api/inversionista/get/{id}")
    
    if r1.status_code == 200:
        data1 = r1.json()
        return render_template('fragmento/inversionista/actualizar.html', list=data["data"], inversionista=data1["data"])
    else:
        flash("Inversionista no encontrado", category='error')
        return redirect("/admin/inversionista/list")

@router.route('/admin/inversionista/register')
def view_register_inversionista():
    r = requests.get("http://localhost:8020/api/inversionista/list")
    data = r.json()
    return render_template('fragmento/inversionista/nwProyecto.html', list=data["data"])

@router.route('/admin/inversionista/save', methods=["POST"])
def save_inversionista():
    headers = {'Content-type': 'application/json'}
    form = request.form
    dataF = {
        "apellido": form["ape"],
        "nombre": form["nom"],
        "dni": form["dni"],
    }
    
    r = requests.post("http://localhost:8020/api/inversionista/save", data=json.dumps(dataF), headers=headers)
    dat = r.json()
    
    if r.status_code == 200:
        flash("Inversionista guardado correctamente", category='info')
        registrar_historial("crear", "inversionista", f"Inversionista {form['nom']} {form['ape']} creado")
    else:
        flash(str(dat["data"]), category='error')

    return redirect("/admin/inversionista/list")

@router.route('/admin/inversionista/delete/<int:id>', methods=["POST"])
def delete_inversionista(id):
    r = requests.delete(f"http://localhost:8020/api/inversionista/delete/{id}")
    
    if r.status_code == 200:
        flash("Inversionista eliminado exitosamente.", category='info')
        registrar_historial("eliminar", "inversionista", f"Inversionista con ID {id} eliminado")
    else:
        flash("No se pudo eliminar el inversionista.", category='error')
    
    return redirect(url_for('router.list_inversionista'))

@router.route('/admin/inversionista/update', methods=["POST"])
def update_inversionista():
    headers = {'Content-type': 'application/json'}
    form = request.form
    dataF = {
        "idInversionista": form["id"],
        "apellido": form["ape"],
        "nombre": form["nom"],
        "dni": form["dni"],
    }
    
    r = requests.post("http://localhost:8020/api/inversionista/update", data=json.dumps(dataF), headers=headers)
    dat = r.json()
    
    if r.status_code == 200:
        flash("Inversionista actualizado correctamente", category='info')
        registrar_historial("actualizar", "inversionista", f"Inversionista {form['nom']} {form['ape']} actualizado")
    else:
        flash(str(dat["data"]), category='error')

    return redirect("/admin/inversionista/list")

@router.route('/admin/proyecto/list')
def list_proyecto():
    r = requests.get("http://localhost:8020/api/ProyectoEnergia/list")
    data = r.json()
    return render_template('fragmento/proyecto/lista.html', list=data["data"])

@router.route('/admin/proyecto/edit/<int:id>')
def view_edit_proyecto(id):
    r = requests.get("http://localhost:8020/api/ProyectoEnergia/list")
    data = r.json()
    r1 = requests.get(f"http://localhost:8020/api/ProyectoEnergia/get/{id}")
    data1 = r1.json()
    
    if r1.status_code == 200:
        return render_template('fragmento/proyecto/actualizar.html', list=data["data"], proyecto=data1["data"])
    else:
        flash("Proyecto no encontrado", category='error')
        return redirect("/admin/proyecto/list")

@router.route('/admin/proyecto/register')
def view_register_proyecto():
    r = requests.get("http://localhost:8020/api/ProyectoEnergia/list")
    data = r.json()
    return render_template('fragmento/proyecto/actualizar.html', list=data["data"])

@router.route('/admin/proyecto/delete/<int:id>', methods=['DELETE'])
def delete_proyecto(id):
    try:
        response = requests.delete(f"http://localhost:8020/api/ProyectoEnergia/delete/{id}")
        if response.status_code == 200:
            flash("Proyecto eliminado exitosamente", "info")
            registrar_historial("eliminar", "proyecto", f"Proyecto con ID {id} eliminado")
            return jsonify({"message": "Proyecto eliminado exitosamente."}), 200 
        else:
            flash("Error al eliminar el proyecto.", "danger")
            return jsonify({"error": "No se pudo eliminar el proyecto."}), response.status_code
    except Exception as e:
        flash(f"Hubo un error: {str(e)}", "danger")
        return jsonify({"error": str(e)}), 500

@router.route('/admin/proyecto/update', methods=["POST"])
def update_proyecto():
    headers = {'Content-type': 'application/json'}
    form = request.form
    
    dataF = {
        "idProyecto": form["id"],
        "nombre": form["nombre"],
        "inversion": float(form["inversion"]),
        "inversionistas": form["inversionistas"],
        "tiempoVida": int(form["tiempoVida"]),
        "tiempoInicioConstruccion": form["tiempoInicioConstruccion"],
        "tiempoFinConstruccion": form["tiempoFinConstruccion"],
        "capacidadGeneracionDiaria": float(form["capacidadGeneracionDiaria"]),
        "montoInversion": float(form["montoInversion"]),
        "ubicacion": form["ubicacion"],
    }
    r = requests.post("http://localhost:8020/api/ProyectoEnergia/update", data=json.dumps(dataF), headers=headers)
    dat = r.json()
    
    if r.status_code == 200:
        flash("Proyecto actualizado correctamente", category='info')
        registrar_historial("actualizar", "proyecto", f"Proyecto {form['nombre']} actualizado")
    else:
        flash(str(dat["data"]), category='error')

    return redirect("/admin/proyecto/list")

@router.route("/admin/proyecto/historial")
def ver_historial():
    try:
        with open("historial.json", "r") as archivo:
            historial = json.load(archivo)
    except FileNotFoundError:
        historial = []
    
    return render_template("fragmento/proyecto/registroOperaciones.html", historial=historial)

@router.route("/admin/inversionista/historial")
def ver_historssial():
    try:
        with open("historial.json", "r") as archivo:
            historial = json.load(archivo)
    except FileNotFoundError:
        historial = []
    
    return render_template("fragmento/inversionista/registroOperaciones.html", historial=historial)

@router.route('/admin/proyecto/search/<criterio>/<texto>')
def view_buscar_person(criterio, texto):
    url = "http://localhost:8020/api/ProyectoEnergia/list/search/"
    
    texto_codificado = quote(texto)
    print(f"Texto recibido para búsqueda de {criterio}: {texto_codificado}")  
    
    r = None  
    if criterio == "nombre":
        r = requests.get(url + "nombre/" + texto_codificado)
    elif criterio == "inversionistas":
        print(f"Buscando inversionista con el texto: {texto_codificado}")  
        r = requests.get(url + "inversionistas/" + texto_codificado)

    if r:
        if r.status_code == 200:
            try:
                data1 = r.json() 
                print(f"Datos recibidos: {data1}")
                if type(data1["data"]) is dict:
                    lista = [data1["data"]]
                    return render_template('fragmento/proyecto/lista.html', list=lista)
                else:
                    return render_template('fragmento/proyecto/lista.html', list=data1["data"])
            except requests.exceptions.JSONDecodeError:
                return render_template('fragmento/proyecto/lista.html', list=[], message="Error")
        else:
            return render_template('fragmento/proyecto/lista.html', list=[], message="No existe elemento")
    else:
        return render_template('fragmento/proyecto/lista.html', list=[], message="Criterio no valido")
    
@router.route('/admin/proyecto/list/<atributo>/<tipo>/<metodo>')
def view_order_person(atributo, tipo, metodo):
    try:
        print(f"Recibido: atributo={atributo}, tipo={tipo}, metodo={metodo}")
 
        if metodo == "quick":
            url = f"http://localhost:8020/api/ProyectoEnergia/list/quick/{atributo}/{tipo}"
        elif metodo == "merge":
            url = f"http://localhost:8020/api/ProyectoEnergia/list/merge/{atributo}/{tipo}"
        elif metodo == "shell":
            url = f"http://localhost:8020/api/ProyectoEnergia/list/shell/{atributo}/{tipo}"
        else:
            flash("Método de ordenación no válido", category='error')
            return render_template('fragmento/proyecto/lista.html', list=[])

        r = requests.get(url)

        if r.status_code == 200:
            data = r.json()
            return render_template('fragmento/proyecto/lista.html', list=data["data"])
        else:
            flash("Error al ordenar los datos", category='error')
            return render_template('fragmento/proyecto/lista.html', list=[], message='Error al ordenar los datos')

    except requests.RequestException as e:
        flash(f"Error de conexión: {str(e)}", category='error')
        return redirect(url_for('router.list'))

@router.route('/admin/proyecto/list/search/<categoria>/<texto>')
def view_buscar_proyecto(categoria, texto):
    try:
        base_url = "http://localhost:8020/api/ProyectoEnergia/list/search"

        if categoria not in ["nombre", "inversionistas"]: 
            flash("Categoría de búsqueda no válida", category='error')
            return redirect(url_for('router.list'))

        criterio = "binario" if len(texto) <= 10 else "lineal"
        api_url = f"{base_url}/{criterio}/{categoria}/{texto}"

        r = requests.get(api_url)
        data = r.json()

        if r.status_code == 200:
            proyectos = data.get("data", [])
            return render_template('fragmento/proyecto/lista.html', list=proyectos)
        else:
            flash("No se encontraron resultados", category='info')
            return render_template('fragmento/proyecto/lista.html', list=[])

    except requests.RequestException as e:
        flash(f"Error de conexión: {str(e)}", category='error')
        return redirect(url_for('router.list'))

def registrar_historial(operacion, tipo, mensaje):
    try:
        with open("historial.json", "r") as archivo:
            historial = json.load(archivo)
    except FileNotFoundError:
        historial = []

    nueva_entrada = {
        "fecha": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
        "operacion": operacion,
        "tipo": tipo,
        "mensaje": mensaje
    }
    historial.append(nueva_entrada)

    with open("historial.json", "w") as archivo:
        json.dump(historial, archivo, indent=4)