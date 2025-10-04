import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from '@angular/router';
import {LoginRequest} from '../models/login.request';
import {catchError, Observable, tap, throwError} from 'rxjs';
import { environment } from '../../../environments/environment.development';
import {LoginResponse} from '../models/login.response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http: HttpClient = inject(HttpClient);
  private jwtHelper: JwtHelperService = inject(JwtHelperService);
  private routing: Router = inject(Router);

  private urlLocal: string = "http:localhost:3200/api/v1"


  public login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>( `${environment.apiUrl}/auth/login`, credentials).pipe(
      tap( (response: LoginResponse) => {
        localStorage.setItem("token", response.token);
        const token = this.jwtHelper.decodeToken(response.token);
        //localStorage.setItem("role", token.role[0].authority);
        localStorage.setItem("email", token.sub);
        console.log("decript token", token);
      }),
      catchError( (err) => {
        console.error('Error en el login:', err.error);
        return throwError(() => err);
      })
    );
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem("token");
    return !!token && !this.jwtHelper.isTokenExpired(token);
  }
}
