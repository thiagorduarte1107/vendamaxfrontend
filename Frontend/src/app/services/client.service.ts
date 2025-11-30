import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Client } from '../models';
import { ApiResponse, ClientDTO } from '../models/product.dto';
import { ClientMapper } from '../mappers/client.mapper';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = `${environment.apiUrl}/clientes`;
  private clientsSubject = new BehaviorSubject<Client[]>([]);
  public clients$ = this.clientsSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadClients();
  }

  private loadClients(): void {
    this.getAll().subscribe();
  }

  getAll(): Observable<Client[]> {
    return this.http.get<ApiResponse<ClientDTO[]>>(`${this.apiUrl}/ativos`).pipe(
      map(response => ClientMapper.toModelArray(response.data)),
      tap(clients => this.clientsSubject.next(clients))
    );
  }

  searchByName(name: string): Observable<Client[]> {
    const params = new HttpParams().set('nome', name);
    return this.http.get<ApiResponse<ClientDTO[]>>(`${this.apiUrl}/buscar`, { params }).pipe(
      map(response => ClientMapper.toModelArray(response.data))
    );
  }

  getById(id: string): Observable<Client | undefined> {
    return this.http.get<ApiResponse<ClientDTO>>(`${this.apiUrl}/${id}`).pipe(
      map(response => ClientMapper.toModel(response.data))
    );
  }

  create(client: Omit<Client, 'id' | 'createdAt'>): Observable<Client> {
    const dto = ClientMapper.toDTO(client);
    return this.http.post<ApiResponse<ClientDTO>>(this.apiUrl, dto).pipe(
      map(response => ClientMapper.toModel(response.data)),
      tap(() => this.loadClients())
    );
  }

  update(id: string, client: Partial<Client>): Observable<Client> {
    const dto = ClientMapper.toDTO(client);
    return this.http.put<ApiResponse<ClientDTO>>(`${this.apiUrl}/${id}`, dto).pipe(
      map(response => ClientMapper.toModel(response.data)),
      tap(() => this.loadClients())
    );
  }

  delete(id: string): Observable<boolean> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`).pipe(
      map(response => response.success),
      tap(() => this.loadClients())
    );
  }

  countActive(): Observable<number> {
    return this.http.get<ApiResponse<number>>(`${this.apiUrl}/count`).pipe(
      map(response => response.data)
    );
  }
}
