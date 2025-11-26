import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatBadgeModule } from '@angular/material/badge';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  cost: number;
  stock: number;
  category: string;
  image: string;
  barcode: string;
  active: boolean;
}

interface Category {
  id: number;
  name: string;
  count: number;
}

@Component({
  selector: 'app-product-catalog',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatFormFieldModule,
    MatInputModule,
    MatBadgeModule,
    MatSnackBarModule
  ],
  templateUrl: './product-catalog.component.html',
  styleUrls: ['./product-catalog.component.scss']
})
export class ProductCatalogComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  categories: Category[] = [];
  selectedCategory: string = 'Todos';
  searchTerm: string = '';
  cartCount: number = 0;
  currentComandaId: string | null = null;
  comandaItemsCount: number = 0;

  constructor(
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProducts();
    this.loadCategories();
    this.loadCartCount();
    this.checkComandaMode();
  }

  checkComandaMode(): void {
    const comandaId = localStorage.getItem('current_comanda_id');
    if (comandaId) {
      this.currentComandaId = comandaId;
      this.loadComandaItemsCount();
    }
  }

  loadComandaItemsCount(): void {
    if (!this.currentComandaId) return;

    const comandas = localStorage.getItem('comandas');
    if (comandas) {
      const allComandas = JSON.parse(comandas);
      const comanda = allComandas.find((c: any) => c.id.toString() === this.currentComandaId);
      if (comanda) {
        this.comandaItemsCount = comanda.items.length;
      }
    }
  }

  loadProducts(): void {
    // Simulação de produtos - substituir por chamada ao backend
    this.products = [
      {
        id: 1,
        name: 'Mouse Gamer RGB',
        description: 'Mouse gamer com iluminação RGB e 7 botões programáveis',
        price: 129.90,
        cost: 80.00,
        stock: 15,
        category: 'Informática',
        image: 'https://images.unsplash.com/photo-1527814050087-3793815479db?w=400',
        barcode: '7891234567890',
        active: true
      },
      {
        id: 2,
        name: 'Teclado Mecânico',
        description: 'Teclado mecânico com switches blue e iluminação RGB',
        price: 299.90,
        cost: 180.00,
        stock: 8,
        category: 'Informática',
        image: 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=400',
        barcode: '7891234567891',
        active: true
      },
      {
        id: 3,
        name: 'Headset Gamer',
        description: 'Headset com som surround 7.1 e microfone removível',
        price: 199.90,
        cost: 120.00,
        stock: 12,
        category: 'Eletrônicos',
        image: 'https://images.unsplash.com/photo-1599669454699-248893623440?w=400',
        barcode: '7891234567892',
        active: true
      },
      {
        id: 4,
        name: 'Webcam Full HD',
        description: 'Webcam 1080p com microfone integrado',
        price: 249.90,
        cost: 150.00,
        stock: 20,
        category: 'Eletrônicos',
        image: 'https://images.unsplash.com/photo-1587825140708-dfaf72ae4b04?w=400',
        barcode: '7891234567893',
        active: true
      },
      {
        id: 5,
        name: 'Monitor 24" Full HD',
        description: 'Monitor LED 24 polegadas Full HD com HDMI',
        price: 699.90,
        cost: 450.00,
        stock: 5,
        category: 'Informática',
        image: 'https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=400',
        barcode: '7891234567894',
        active: true
      },
      {
        id: 6,
        name: 'SSD 480GB',
        description: 'SSD SATA III 480GB com velocidade de leitura 550MB/s',
        price: 279.90,
        cost: 180.00,
        stock: 25,
        category: 'Informática',
        image: 'https://images.unsplash.com/photo-1531492746076-161ca9bcad58?w=400',
        barcode: '7891234567895',
        active: true
      },
      {
        id: 7,
        name: 'Mousepad Gamer XXL',
        description: 'Mousepad gamer tamanho estendido 90x40cm',
        price: 79.90,
        cost: 40.00,
        stock: 30,
        category: 'Acessórios',
        image: 'https://images.unsplash.com/photo-1625948515291-69613efd103f?w=400',
        barcode: '7891234567896',
        active: true
      },
      {
        id: 8,
        name: 'Suporte para Notebook',
        description: 'Suporte ergonômico ajustável para notebook',
        price: 89.90,
        cost: 50.00,
        stock: 18,
        category: 'Acessórios',
        image: 'https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=400',
        barcode: '7891234567897',
        active: true
      },
      {
        id: 9,
        name: 'Hub USB 3.0',
        description: 'Hub USB 3.0 com 4 portas',
        price: 49.90,
        cost: 25.00,
        stock: 40,
        category: 'Acessórios',
        image: 'https://images.unsplash.com/photo-1625948515291-69613efd103f?w=400',
        barcode: '7891234567898',
        active: true
      },
      {
        id: 10,
        name: 'Caderno Universitário',
        description: 'Caderno 10 matérias 200 folhas',
        price: 24.90,
        cost: 12.00,
        stock: 50,
        category: 'Escritório',
        image: 'https://images.unsplash.com/photo-1517842645767-c639042777db?w=400',
        barcode: '7891234567899',
        active: true
      },
      {
        id: 11,
        name: 'Caneta Esferográfica',
        description: 'Caixa com 12 canetas azuis',
        price: 15.90,
        cost: 8.00,
        stock: 100,
        category: 'Escritório',
        image: 'https://images.unsplash.com/photo-1586075010923-2dd4570fb338?w=400',
        barcode: '7891234567900',
        active: true
      },
      {
        id: 12,
        name: 'Pasta Organizadora',
        description: 'Pasta organizadora A4 com 12 divisórias',
        price: 34.90,
        cost: 18.00,
        stock: 35,
        category: 'Escritório',
        image: 'https://images.unsplash.com/photo-1544816155-12df9643f363?w=400',
        barcode: '7891234567901',
        active: true
      }
    ];

    this.filteredProducts = [...this.products];
  }

  loadCategories(): void {
    const categoryMap = new Map<string, number>();
    
    this.products.forEach(product => {
      const count = categoryMap.get(product.category) || 0;
      categoryMap.set(product.category, count + 1);
    });

    this.categories = [
      { id: 0, name: 'Todos', count: this.products.length }
    ];

    categoryMap.forEach((count, name) => {
      this.categories.push({
        id: this.categories.length,
        name,
        count
      });
    });
  }

  loadCartCount(): void {
    const cart = localStorage.getItem('pdv_cart');
    if (cart) {
      const items = JSON.parse(cart);
      this.cartCount = items.reduce((sum: number, item: any) => sum + item.quantity, 0);
    }
  }

  filterByCategory(category: string): void {
    this.selectedCategory = category;
    this.applyFilters();
  }

  onSearch(): void {
    this.applyFilters();
  }

  applyFilters(): void {
    this.filteredProducts = this.products.filter(product => {
      const matchesCategory = this.selectedCategory === 'Todos' || product.category === this.selectedCategory;
      const matchesSearch = !this.searchTerm || 
        product.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        product.description.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        product.barcode.includes(this.searchTerm);
      
      return matchesCategory && matchesSearch && product.active;
    });
  }

  addToCart(product: Product): void {
    if (product.stock <= 0) {
      this.showSnackBar('Produto sem estoque!', 'error');
      return;
    }

    // Se estiver em modo comanda, adicionar à comanda
    if (this.currentComandaId) {
      this.addToComanda(product);
      return;
    }

    // Carregar carrinho atual
    let cart = [];
    const cartData = localStorage.getItem('pdv_cart');
    if (cartData) {
      cart = JSON.parse(cartData);
    }

    // Verificar se produto já está no carrinho
    const existingItem = cart.find((item: any) => item.id === product.id);
    
    if (existingItem) {
      if (existingItem.quantity >= product.stock) {
        this.showSnackBar('Quantidade máxima atingida!', 'error');
        return;
      }
      existingItem.quantity++;
      existingItem.subtotal = existingItem.quantity * existingItem.price;
    } else {
      cart.push({
        id: product.id,
        name: product.name,
        price: product.price,
        quantity: 1,
        subtotal: product.price,
        stock: product.stock
      });
    }

    // Salvar carrinho
    localStorage.setItem('pdv_cart', JSON.stringify(cart));
    this.loadCartCount();
    
    this.showSnackBar(`${product.name} adicionado ao carrinho!`, 'success');
  }

  addToComanda(product: Product): void {
    const comandas = localStorage.getItem('comandas');
    if (!comandas) return;

    const allComandas = JSON.parse(comandas);
    const comanda = allComandas.find((c: any) => c.id.toString() === this.currentComandaId);
    
    if (!comanda) {
      this.showSnackBar('Comanda não encontrada!', 'error');
      return;
    }

    // Verificar se produto já está na comanda
    const existingItem = comanda.items.find((item: any) => item.productId === product.id);
    
    if (existingItem) {
      existingItem.quantity++;
      existingItem.subtotal = existingItem.quantity * existingItem.unitPrice;
    } else {
      comanda.items.push({
        id: Date.now(),
        productId: product.id,
        productName: product.name,
        quantity: 1,
        unitPrice: product.price,
        subtotal: product.price
      });
    }

    // Recalcular subtotal da comanda
    comanda.subtotal = comanda.items.reduce((sum: number, item: any) => sum + item.subtotal, 0);

    // Salvar comandas atualizadas
    localStorage.setItem('comandas', JSON.stringify(allComandas));
    this.loadComandaItemsCount();
    
    this.showSnackBar(`${product.name} adicionado à comanda!`, 'success');
  }

  goToPDV(): void {
    this.router.navigate(['/pdv']);
  }

  goToComanda(): void {
    if (this.currentComandaId) {
      localStorage.removeItem('current_comanda_id');
      this.router.navigate(['/comandas']);
    }
  }

  showSnackBar(message: string, type: 'success' | 'error' = 'success'): void {
    this.snackBar.open(message, 'Fechar', {
      duration: 3000,
      horizontalPosition: 'end',
      verticalPosition: 'top',
      panelClass: type === 'error' ? ['error-snackbar'] : ['success-snackbar']
    });
  }

  getStockStatus(stock: number): string {
    if (stock === 0) return 'Sem estoque';
    if (stock <= 5) return 'Estoque baixo';
    return `${stock} unidades`;
  }

  getStockClass(stock: number): string {
    if (stock === 0) return 'out-of-stock';
    if (stock <= 5) return 'low-stock';
    return 'in-stock';
  }
}
