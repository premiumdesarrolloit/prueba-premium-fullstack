import 'package:flutter/material.dart';
import 'login_screen.dart';
import 'package:flutter_prueba_spring/pages/curso_main_page.dart';
import 'services/auth_service.dart';
import 'package:flutter_web_plugins/url_strategy.dart';


void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  setUrlStrategy(PathUrlStrategy());
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool _isLoggedIn = false;
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _checkAuthStatus();
  }

  Future<void> _checkAuthStatus() async {
    final loggedIn = await AuthService.isLoggedIn();
    setState(() {
      _isLoggedIn = loggedIn;
      _isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    // Mostrar loading mientras verifica autenticación
    if (_isLoading) {
      return MaterialApp(
        home: Scaffold(
          body: Center(
            child: CircularProgressIndicator(),
          ),
        ),
      );
    }

    return MaterialApp(
      title: 'Flutter Login',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: _isLoggedIn ? const Cursopage() : const LoginScreen(),
      routes: {
        '/login': (context) => const LoginScreen(),
        '/home': (context) => const Cursopage(),
      },
      onGenerateRoute: (settings) {
         // Bloquear acceso a home si no está logueado
        if (settings.name == '/home' && !_isLoggedIn) {
          return MaterialPageRoute(builder: (context) => const LoginScreen());
        }
        // Bloquear acceso a login si ya está logueado
        if (settings.name == '/login' && _isLoggedIn) {
          return MaterialPageRoute(builder: (context) => const Cursopage());
        }
        return null;
      },
    );
  }
}