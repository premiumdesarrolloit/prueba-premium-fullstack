import 'package:flutter/material.dart';
import '../services/curso_service.dart';

class EditarCursoPage extends StatefulWidget {
  final Map<String, dynamic> curso;

  EditarCursoPage({required this.curso});

  @override
  _EditarCursoPageState createState() => _EditarCursoPageState();
}

class _EditarCursoPageState extends State<EditarCursoPage> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController nombreCtrl = TextEditingController();
  late TextEditingController inscritosCtrl = TextEditingController();
  late TextEditingController categoriaCtrl = TextEditingController();
  late TextEditingController evaluacionesTotalesCtrl = TextEditingController();
  late TextEditingController puntuacionPromedioCtrl = TextEditingController();
  final cursoService = CursoService();

  @override
  void initState() {
    super.initState();
    nombreCtrl = TextEditingController(text: widget.curso['nombre']);
    evaluacionesTotalesCtrl = TextEditingController(text: widget.curso['evaluacionesTotales'].toString());
    categoriaCtrl = TextEditingController(text: widget.curso['categoria']);
    inscritosCtrl = TextEditingController(text: widget.curso['inscritos'].toString());
    puntuacionPromedioCtrl =  TextEditingController(text: widget.curso['puntuacionPromedio'].toString());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Editar Curso")),
      
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextFormField(
                controller: nombreCtrl,
                decoration: InputDecoration(labelText: "Nombre"),
                validator: (value) => value!.isEmpty ? "Ingrese un nombre" : null,
              ),
              TextFormField(
                controller: puntuacionPromedioCtrl,
                decoration: InputDecoration(labelText: "Puntuacion promedio"),
                keyboardType: TextInputType.number,
                validator: (value) => value!.isEmpty ? "Ingrese un promedio" : null,
              ),
                TextFormField(
                controller: evaluacionesTotalesCtrl,
                decoration: InputDecoration(labelText: "evaluaciones totales"),
                keyboardType: TextInputType.number,
                validator: (value) => value!.isEmpty ? "Ingrese la evaluacion total" : null,
              ),
                TextFormField(
                controller: categoriaCtrl,
                decoration: InputDecoration(labelText: "categoria"),
                validator: (value) => value!.isEmpty ? "Ingrese una categoria" : null,
              ),
              TextFormField(
                controller: inscritosCtrl,
                decoration: InputDecoration(labelText: "inscritos"),
                keyboardType: TextInputType.number,
                validator: (value) => value!.isEmpty ? "Ingrese los inscritos" : null,
              ),
              SizedBox(height: 20),
              ElevatedButton(
                child: Text("Actualizar"),
                onPressed: () async {
                  if (_formKey.currentState!.validate()) {
                    final actualizado = await cursoService.actualizarCurso(
                      widget.curso['id'],
                      {
                        "nombre": nombreCtrl.text,
                        "puntuacionPromedio": double.parse(puntuacionPromedioCtrl.text),
                        "categoria": categoriaCtrl.text,
                        "inscritos": double.parse(inscritosCtrl.text),
                        "evaluacionesTotales": double.parse(evaluacionesTotalesCtrl.text),
                      },
                    );
                    Navigator.pop(context, actualizado); 
                  }
                },
              )
            ],
          ),
        ),
      ),
    );
  }
}
