import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class CursoService {
  final String baseUrl = "http://127.0.0.1:90/api/cursos";

  // Método privado para obtener el token desde SharedPreferences
  Future<String?> _getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString('token');
  }

  Future<List<dynamic>> listarCursos() async {
    final token = await _getToken();
    final res = await http.get(
      Uri.parse(baseUrl),
      headers: {
        "Content-Type": "application/json",
        if (token != null) "Authorization": "Bearer $token", 
      },
    );

    if (res.statusCode == 200) {
      return jsonDecode(res.body);
    } else {
      throw Exception("Error al listar cursos");
    }
  }

  Future<Map<String, dynamic>> crearCurso(Map<String, dynamic> curso) async {
    final token = await _getToken();
    final res = await http.post(
      Uri.parse(baseUrl),
      headers: {
        "Content-Type": "application/json",
        if (token != null) "Authorization": "Bearer $token",
      },
      body: jsonEncode(curso),
    );
    return jsonDecode(res.body);
  }

  Future<Map<String, dynamic>> actualizarCurso(int id, Map<String, dynamic> curso) async {
    final token = await _getToken();
    final res = await http.put(
      Uri.parse("$baseUrl/$id"),
      headers: {
        "Content-Type": "application/json",
        if (token != null) "Authorization": "Bearer $token",
      },
      body: jsonEncode(curso),
    );
    return jsonDecode(res.body);
  }

  Future<Map<String, dynamic>> eliminarCurso(int id) async {
    final token = await _getToken();
    final res = await http.delete(
      Uri.parse("$baseUrl/$id"),
      headers: {
        "Content-Type": "application/json",
        if (token != null) "Authorization": "Bearer $token",
      },
    );
    return jsonDecode(res.body);
  }
}
