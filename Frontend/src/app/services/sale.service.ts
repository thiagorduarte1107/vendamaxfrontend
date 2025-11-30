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

  // ===== MÉTODOS DE CAIXA =====
  
  getOpenCashRegister(): Observable<any> {
    const currentUser = this.authService.getCurrentUser();
    const userId = currentUser?.id || '1';
    
    const headers = new HttpHeaders({
      'User-Id': userId
    });
    
    const caixaUrl = `${environment.apiUrl}/caixas/aberto`;
    return this.http.get<ApiResponse<any>>(caixaUrl, { headers }).pipe(
      map(response => response.data)
    );
  }

  openCashRegister(openingBalance: number): Observable<any> {
    const currentUser = this.authService.getCurrentUser();
    const userId = currentUser?.id || '1';
    
    const headers = new HttpHeaders({
      'User-Id': userId
    });
    
    const caixaUrl = `${environment.apiUrl}/caixas/abrir`;
    return this.http.post<ApiResponse<any>>(caixaUrl, null, {
      headers,
      params: { saldoInicial: openingBalance.toString() }
    }).pipe(
      map(response => response.data)
    );
  }

  closeCashRegister(caixaId: number, notes?: string): Observable<any> {
    const caixaUrl = `${environment.apiUrl}/caixas/${caixaId}/fechar`;
    const params: any = notes ? { observacoes: notes } : undefined;
    return this.http.post<ApiResponse<any>>(caixaUrl, null, params ? { params } : {}).pipe(
      map(response => response.data)
    );
  }

  registerWithdrawal(caixaId: number, amount: number, description?: string): Observable<any> {
    const movementUrl = `${environment.apiUrl}/movimentacoes-caixa/sangria`;
    const params: any = { caixaId: caixaId.toString(), valor: amount.toString() };
    if (description) {
      params.descricao = description;
    }
    return this.http.post<ApiResponse<any>>(movementUrl, null, { params }).pipe(
      map(response => response.data)
    );
  }

  registerDeposit(caixaId: number, amount: number, description?: string): Observable<any> {
    const movementUrl = `${environment.apiUrl}/movimentacoes-caixa/suprimento`;
    const params: any = { caixaId: caixaId.toString(), valor: amount.toString() };
    if (description) {
      params.descricao = description;
    }
    return this.http.post<ApiResponse<any>>(movementUrl, null, { params }).pipe(
      map(response => response.data)
    );
  }
}
