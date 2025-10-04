import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class AuthService {
  static const String baseUrl = 'http://127.0.0.1:90/api/auth'; // Cambia por tu URL
  
  // Método para login
static Future<bool> login(String user, String password) async {
  try {
    final response = await http.post(
      Uri.parse('$baseUrl/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'username': user,
        'password': password,
      }),
    );

    // Verificar primero el status HTTP
    if (response.statusCode != 200) {
      print("Error HTTP: ${response.statusCode}");
      return false;
    }

    final data = jsonDecode(response.body);
    
    // Tu respuesta: {"token": "...", "username": "dixon", "statusCode": 200}
    if (data['statusCode'] == 200 && data['token'] != null) {
      await _saveToken(data['token']);
      await _saveUserData(data['username']);
      return true;
    } else {
      return false;
    }
  } catch (e) {
    return false;
  }
}

  // Guardar token
  static Future<void> _saveToken(String token) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('token', token);
  }

  // Guardar datos del usuario
  static Future<void> _saveUserData(String user) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('user', jsonEncode(user));
  }

  // Verificar si el usuario está logueado
  static Future<bool> isLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('token');
    return token != null;
  }

  // Cerrar sesión
  static Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('token');
    await prefs.remove('user');
  }
}