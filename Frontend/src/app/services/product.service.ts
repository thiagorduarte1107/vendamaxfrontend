import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Product, ProductFilters } from '../models';
import { ApiResponse, ProductDTO } from '../models/product.dto';
import { ProductMapper } from '../mappers/product.mapper';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = `${environment.apiUrl}/produtos`;
  private productsSubject = new BehaviorSubject<Product[]>([]);
  public products$ = this.productsSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadProducts();
  }

  private loadProducts(): void {
    this.getAll().subscribe();
  }

  getAll(filters?: ProductFilters): Observable<Product[]> {
    let params = new HttpParams();
    
    if (filters?.search) {
      return this.searchByName(filters.search);
    }
    
    if (filters?.categoryId) {
      return this.getByCategory(filters.categoryId);
    }
    
    if (filters?.lowStock) {
      return this.getLowStockProducts();
    }

    return this.http.get<ApiResponse<ProductDTO[]>>(`${this.apiUrl}/ativos`).pipe(
      map(response => ProductMapper.toModelArray(response.data)),
      tap(products => this.productsSubject.next(products))
    );
  }

  searchByName(name: string): Observable<Product[]> {
    const params = new HttpParams().set('nome', name);
    return this.http.get<ApiResponse<ProductDTO[]>>(`${this.apiUrl}/buscar`, { params }).pipe(
      map(response => ProductMapper.toModelArray(response.data))
    );
  }

  getByCategory(categoryId: string): Observable<Product[]> {
    return this.http.get<ApiResponse<ProductDTO[]>>(`${this.apiUrl}/categoria/${categoryId}`).pipe(
      map(response => ProductMapper.toModelArray(response.data))
    );
  }

  getById(id: string): Observable<Product | undefined> {
    return this.http.get<ApiResponse<ProductDTO>>(`${this.apiUrl}/${id}`).pipe(
      map(response => ProductMapper.toModel(response.data))
    );
  }

  create(product: Omit<Product, 'id' | 'createdAt' | 'updatedAt'>): Observable<Product> {
    const dto = ProductMapper.toDTO(product);
    return this.http.post<ApiResponse<ProductDTO>>(this.apiUrl, dto).pipe(
      map(response => ProductMapper.toModel(response.data)),
      tap(() => this.loadProducts())
    );
  }

  update(id: string, product: Partial<Product>): Observable<Product> {
    const dto = ProductMapper.toDTO(product);
    return this.http.put<ApiResponse<ProductDTO>>(`${this.apiUrl}/${id}`, dto).pipe(
      map(response => ProductMapper.toModel(response.data)),
      tap(() => this.loadProducts())
    );
  }

  delete(id: string): Observable<boolean> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`).pipe(
      map(response => response.success),
      tap(() => this.loadProducts())
    );
  }

  getLowStockProducts(): Observable<Product[]> {
    return this.http.get<ApiResponse<ProductDTO[]>>(`${this.apiUrl}/estoque-baixo`).pipe(
      map(response => ProductMapper.toModelArray(response.data))
    );
  }

  getOutOfStockProducts(): Observable<Product[]> {
    return this.http.get<ApiResponse<ProductDTO[]>>(`${this.apiUrl}/sem-estoque`).pipe(
      map(response => ProductMapper.toModelArray(response.data))
    );
  }

  updateStock(id: string, quantity: number): Observable<Product> {
    const params = new HttpParams().set('quantidade', quantity.toString());
    return this.http.patch<ApiResponse<ProductDTO>>(`${this.apiUrl}/${id}/estoque`, null, { params }).pipe(
      map(response => ProductMapper.toModel(response.data)),
      tap(() => this.loadProducts())
    );
  }

  countActive(): Observable<number> {
    return this.http.get<ApiResponse<number>>(`${this.apiUrl}/count`).pipe(
      map(response => response.data)
    );
  }
}
