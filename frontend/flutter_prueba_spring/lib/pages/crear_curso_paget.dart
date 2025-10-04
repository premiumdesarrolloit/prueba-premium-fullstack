import 'package:flutter/material.dart';
import '../services/curso_service.dart';

class CrearCursoPage extends StatefulWidget {
  const CrearCursoPage({Key? key}) : super(key: key);

  @override
  _CrearCursoPageState createState() => _CrearCursoPageState();
}

class _CrearCursoPageState extends State<CrearCursoPage> {
  final _formKey = GlobalKey<FormState>();
  final nombreCtrl = TextEditingController();
  final inscritosCtrl = TextEditingController();
  final categoriaCtrl = TextEditingController();
  final evaluacionesTotalesCtrl = TextEditingController();
  final puntuacionPromedioCtrl = TextEditingController();
  final cursoService = CursoService();

  Future<void> guardarCurso() async {
    if (_formKey.currentState!.validate()) {
      final nuevoCurso = {
        "nombre": nombreCtrl.text,
        "puntuacionPromedio":
            double.tryParse(puntuacionPromedioCtrl.text) ?? 0.0,
        "evaluacionesTotales":
            double.tryParse(evaluacionesTotalesCtrl.text) ?? 0.0,
        "categoria": categoriaCtrl.text,
        "inscritos": int.tryParse(inscritosCtrl.text) ?? 0,
      };

      final creado = await cursoService.crearCurso(nuevoCurso);

      if (mounted) {
        Navigator.pop(context, creado); // Devuelve el curso creado
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Crear Curso")),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: SingleChildScrollView(
            child: Column(
              children: [
                TextFormField(
                  controller: nombreCtrl,
                  decoration: const InputDecoration(labelText: "Nombre"),
                  validator: (value) =>
                      value!.isEmpty ? "Ingrese un nombre" : null,
                ),
                TextFormField(
                  controller: puntuacionPromedioCtrl,
                  decoration: const InputDecoration(
                      labelText: "Puntuación promedio"),
                  keyboardType: TextInputType.number,
                  validator: (value) =>
                      value!.isEmpty ? "Ingrese un promedio" : null,
                ),
                TextFormField(
                  controller: evaluacionesTotalesCtrl,
                  decoration: const InputDecoration(
                      labelText: "Evaluaciones totales"),
                  keyboardType: TextInputType.number,
                  validator: (value) => value!.isEmpty
                      ? "Ingrese las evaluaciones totales"
                      : null,
                ),
                TextFormField(
                  controller: categoriaCtrl,
                  decoration: const InputDecoration(labelText: "Categoría"),
                  validator: (value) =>
                      value!.isEmpty ? "Ingrese una categoría" : null,
                ),
                TextFormField(
                  controller: inscritosCtrl,
                  decoration: const InputDecoration(labelText: "Inscritos"),
                  keyboardType: TextInputType.number,
                  validator: (value) =>
                      value!.isEmpty ? "Ingrese los inscritos" : null,
                ),
                const SizedBox(height: 20),
                ElevatedButton(
                  onPressed: guardarCurso,
                  child: const Text("Guardar"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    nombreCtrl.dispose();
    inscritosCtrl.dispose();
    categoriaCtrl.dispose();
    evaluacionesTotalesCtrl.dispose();
    puntuacionPromedioCtrl.dispose();
    super.dispose();
  }
}
