import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Sale } from '../models';
import { ApiResponse, SaleDTO } from '../models/product.dto';
import { SaleMapper } from '../mappers/sale.mapper';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  private apiUrl = `${environment.apiUrl}/vendas`;
  private salesSubject = new BehaviorSubject<Sale[]>([]);
  public sales$ = this.salesSubject.asObservable();

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.loadSales();
  }

  private loadSales(): void {
    this.getAll().subscribe();
  }

  getAll(): Observable<Sale[]> {
    return this.http.get<ApiResponse<SaleDTO[]>>(this.apiUrl).pipe(
      map(response => SaleMapper.toModelArray(response.data)),
      tap(sales => this.salesSubject.next(sales))
    );
  }

  getById(id: string): Observable<Sale | undefined> {
    return this.http.get<ApiResponse<SaleDTO>>(`${this.apiUrl}/${id}`).pipe(
      map(response => SaleMapper.toModel(response.data))
    );
  }

  create(sale: Omit<Sale, 'id' | 'createdAt'>): Observable<Sale> {
    const dto = SaleMapper.toDTO(sale);
    const currentUser = this.authService.getCurrentUser();
    const userId = currentUser?.id || '1';
    
    const headers = new HttpHeaders({
      'User-Id': userId
    });
    
    return this.http.post<ApiResponse<SaleDTO>>(this.apiUrl, dto, { headers }).pipe(
      map(response => SaleMapper.toModel(response.data)),
      tap(() => this.loadSales())
    );
  }

  createFromPdv(request: {
    clienteId: number | null;
    caixaId: number;
    itens: Array<{ produtoId: number; quantity: number; unitPrice: number; discount: number }>;
    pagamentos: Array<{ paymentMethod: string; amount: number }>;
    discount: number;
    notes: string;
  }): Observable<Sale> {
    const currentUser = this.authService.getCurrentUser();
    const userId = currentUser?.id || '1';
    
    const headers = new HttpHeaders({
      'User-Id': userId
    });
    
    return this.http.post<ApiResponse<SaleDTO>>(this.apiUrl, request, { headers }).pipe(
      map(response => SaleMapper.toModel(response.data)),
      tap(() => this.loadSales())
    );
  }

  cancelar(id: string): Observable<Sale> {
    return this.http.put<ApiResponse<SaleDTO>>(`${this.apiUrl}/${id}/cancelar`, {}).pipe(
      map(response => SaleMapper.toModel(response.data)),
      tap(() => this.loadSales())
    );
  }

  getTodaySales(): Observable<Sale[]> {
    return this.http.get<ApiResponse<SaleDTO[]>>(`${this.apiUrl}/dia`).pipe(
      map(response => SaleMapper.toModelArray(response.data))
    );
  }

  getTotalSales(): Observable<number> {
    return this.http.get<ApiResponse<number>>(`${this.apiUrl}/count-dia`).pipe(
      map(response => response.data)
    );
  }

  getTotalRevenue(): Observable<number> {
    return this.http.get<ApiResponse<number>>(`${this.apiUrl}/total-dia`).pipe(
      map(response => response.data)
    );
  }
}
