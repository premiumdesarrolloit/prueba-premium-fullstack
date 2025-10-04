import 'package:flutter/material.dart';
import 'package:flutter_prueba_spring/pages/crear_curso_paget.dart';
import 'package:flutter_prueba_spring/pages/editar_curso_page.dart';
import 'package:flutter_prueba_spring/services/auth_service.dart';

import '../services/curso_service.dart';

class Cursopage extends StatefulWidget {
  const Cursopage({super.key});

  @override
  State<Cursopage> createState() => _CursoPageState();
}

class _CursoPageState extends State<Cursopage> {
  final CursoService service = CursoService();
  List<dynamic> cursos = [];
  final TextEditingController nombreCtrl = TextEditingController();
  final TextEditingController inscritosCtrl = TextEditingController();
  final TextEditingController categoriaCtrl = TextEditingController();
  final TextEditingController evaluacionesTotalesCtrl = TextEditingController();
  final TextEditingController puntuacionPromedioCtrl = TextEditingController();


  @override
  void initState() {
    super.initState();
    cargarCursos();
  }

  Future<void> cargarCursos() async {
    final data = await service.listarCursos();
    setState(() {
      cursos = data;
    });
  }



  

  Future<void> eliminarCurso(int id) async {
    await service.eliminarCurso(id);
    cargarCursos();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Listado de cursos disponibles"),
      automaticallyImplyLeading: false,
      
     
      actions: [
        
                        IconButton(
                          icon: const Icon(
                            Icons.add_circle,
                            color: Colors.green,
                          ),
                          tooltip: "Crear nuevo curso",
                          onPressed: () async {
                            final nuevo = await Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (_) => const CrearCursoPage(),
                              ),
                            );
                            if (nuevo != null) {
                              cargarCursos(); // recargar lista tras crear
                            }
                          },
                        ),
        
          IconButton(
            padding: const EdgeInsets.only(right: 40),
            
            icon: const Icon(Icons.logout),
            onPressed: () async {
              await AuthService.logout();
              Navigator.pushReplacementNamed(context, '/login');
            },
          ),
        ],),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
           
            const SizedBox(height: 20),


            Expanded(
  child: ListView.builder(
    itemCount: cursos.length,
    itemBuilder: (context, index) {
      final p = cursos[index];
      return ListTile(
        title: Text("${p['nombre']}"),
        subtitle: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          "Índice de calidad: ${p['indiceCalidad']?.toStringAsFixed(2)}",
                        ),
                        Text("Inscritos: ${p['inscritos']}"),
                        Text("Categoría: ${p['categoria']}"),
                        Text("Evaluaciones: ${p['evaluacionesTotales']}"),
                         Text(
                          "Puntuacion promedio: ${p['puntuacionPromedio']?.toStringAsFixed(2)}",
                        ),
                      ],
                    ),
         trailing: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        IconButton(
                          icon: const Icon(Icons.edit, color: Colors.blue),
                          onPressed: () async {
                            final actualizado = await Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (_) => EditarCursoPage(curso: p),
                              ),
                            );
                            if (actualizado != null) {
                              setState(() {
                                cursos[index] = actualizado;
                              });
                            }
                          },
                        ),
                        IconButton(
                          icon: const Icon(Icons.delete, color: Colors.red),
                          onPressed: () => eliminarCurso(p['id']),
                        )
                      ],
                    ),
      );
    },
  ),
)

          ],
        ),
      ),
    );
  }
}