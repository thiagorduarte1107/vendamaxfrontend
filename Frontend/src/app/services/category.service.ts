import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Category } from '../models';
import { ApiResponse, CategoryDTO } from '../models/product.dto';
import { CategoryMapper } from '../mappers/category.mapper';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = `${environment.apiUrl}/categorias`;
  private categoriesSubject = new BehaviorSubject<Category[]>([]);
  public categories$ = this.categoriesSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadCategories();
  }

  private loadCategories(): void {
    this.getAll().subscribe();
  }

  getAll(): Observable<Category[]> {
    return this.http.get<ApiResponse<CategoryDTO[]>>(`${this.apiUrl}/ativas`).pipe(
      map(response => CategoryMapper.toModelArray(response.data)),
      tap(categories => this.categoriesSubject.next(categories))
    );
  }

  searchByName(name: string): Observable<Category[]> {
    const params = new HttpParams().set('nome', name);
    return this.http.get<ApiResponse<CategoryDTO[]>>(`${this.apiUrl}/buscar`, { params }).pipe(
      map(response => CategoryMapper.toModelArray(response.data))
    );
  }

  getById(id: string): Observable<Category | undefined> {
    return this.http.get<ApiResponse<CategoryDTO>>(`${this.apiUrl}/${id}`).pipe(
      map(response => CategoryMapper.toModel(response.data))
    );
  }

  create(category: Omit<Category, 'id' | 'createdAt'>): Observable<Category> {
    const dto = CategoryMapper.toDTO(category);
    return this.http.post<ApiResponse<CategoryDTO>>(this.apiUrl, dto).pipe(
      map(response => CategoryMapper.toModel(response.data)),
      tap(() => this.loadCategories())
    );
  }

  update(id: string, category: Partial<Category>): Observable<Category> {
    const dto = CategoryMapper.toDTO(category);
    return this.http.put<ApiResponse<CategoryDTO>>(`${this.apiUrl}/${id}`, dto).pipe(
      map(response => CategoryMapper.toModel(response.data)),
      tap(() => this.loadCategories())
    );
  }

  delete(id: string): Observable<boolean> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`).pipe(
      map(response => response.success),
      tap(() => this.loadCategories())
    );
  }
}
