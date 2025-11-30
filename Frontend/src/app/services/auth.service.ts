import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap, map } from 'rxjs/operators';
import { User, LoginCredentials } from '../models';
import { AuthApiResponse, LoginResponseDTO, LoginRequestDTO } from '../models/auth.dto';
import { AuthMapper } from '../mappers/auth.mapper';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();
  private apiUrl = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {
    // Check if user is already logged in
    const storedUser = localStorage.getItem('currentUser');
    const token = localStorage.getItem('token');
    if (storedUser && token) {
      const user = JSON.parse(storedUser);
      // Garantir que o usuário tenha todas as propriedades necessárias
      if (user && !user.permissions) {
        // Usuário antigo, fazer logout
        localStorage.removeItem('currentUser');
        localStorage.removeItem('token');
      } else {
        this.currentUserSubject.next(user);
        this.isAuthenticatedSubject.next(true);
      }
    }
  }

  login(credentials: LoginCredentials): Observable<User> {
    const loginRequest = AuthMapper.toLoginRequest(credentials);
    
    return this.http.post<AuthApiResponse<LoginResponseDTO>>(`${this.apiUrl}/login`, loginRequest).pipe(
      map(response => {
        const loginData = response.data;
        const user = AuthMapper.loginResponseToUser(loginData);
        return { user, token: loginData.token };
      }),
      tap(({ user, token }) => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        localStorage.setItem('token', token);
        this.currentUserSubject.next(user);
        this.isAuthenticatedSubject.next(true);
      }),
      map(({ user }) => user)
    );
  }


  logout(): void {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
    this.currentUserSubject.next(null);
    this.isAuthenticatedSubject.next(false);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }
}
