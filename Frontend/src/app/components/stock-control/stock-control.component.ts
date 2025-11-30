import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatChipsModule } from '@angular/material/chips';
import { NotificationService } from '../../services/notification.service';

interface StockItem {
  id: number;
  name: string;
  category: string;
  currentStock: number;
  minStock: number;
  price: number;
  status: 'ok' | 'low' | 'out';
}

@Component({
  selector: 'app-stock-control',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatCardModule,
    MatSelectModule,
    MatDialogModule,
    MatChipsModule
  ],
  templateUrl: './stock-control.component.html',
  styleUrl: './stock-control.component.scss'
})
export class StockControlComponent implements OnInit {
  displayedColumns: string[] = ['name', 'category', 'currentStock', 'minStock', 'price', 'status', 'actions'];
  
  stockItems: StockItem[] = [];
  filteredItems: StockItem[] = [];
  
  searchTerm = '';
  categoryFilter = 'all';
  statusFilter = 'all';

  constructor(private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.loadMockStock();
    this.applyFilters();
    this.checkStockAlerts();
  }

  loadMockStock(): void {
    this.stockItems = [
      { id: 1, name: 'Notebook Dell', category: 'Eletrônicos', currentStock: 10, minStock: 5, price: 3500.00, status: 'ok' },
      { id: 2, name: 'Mouse Logitech', category: 'Periféricos', currentStock: 3, minStock: 10, price: 89.90, status: 'low' },
      { id: 3, name: 'Teclado Mecânico', category: 'Periféricos', currentStock: 25, minStock: 10, price: 450.00, status: 'ok' },
      { id: 4, name: 'Monitor LG 24"', category: 'Eletrônicos', currentStock: 0, minStock: 5, price: 899.00, status: 'out' },
      { id: 5, name: 'Webcam HD', category: 'Periféricos', currentStock: 30, minStock: 15, price: 250.00, status: 'ok' }
    ];
  }

  applyFilters(): void {
    this.filteredItems = this.stockItems.filter(item => {
      const matchesSearch = item.name.toLowerCase().includes(this.searchTerm.toLowerCase());
      const matchesCategory = this.categoryFilter === 'all' || item.category === this.categoryFilter;
      const matchesStatus = this.statusFilter === 'all' || item.status === this.statusFilter;
      return matchesSearch && matchesCategory && matchesStatus;
    });
  }

  clearFilters(): void {
    this.searchTerm = '';
    this.categoryFilter = 'all';
    this.statusFilter = 'all';
    this.applyFilters();
  }

  get totalItems(): number {
    return this.filteredItems.reduce((sum, item) => sum + item.currentStock, 0);
  }

  get lowStockCount(): number {
    return this.filteredItems.filter(item => item.status === 'low').length;
  }

  get outOfStockCount(): number {
    return this.filteredItems.filter(item => item.status === 'out').length;
  }

  get totalValue(): number {
    return this.filteredItems.reduce((sum, item) => sum + (item.currentStock * item.price), 0);
  }

  getStatusLabel(status: string): string {
    const labels: { [key: string]: string } = {
      'ok': 'Normal',
      'low': 'Baixo',
      'out': 'Esgotado'
    };
    return labels[status] || status;
  }

  addStock(item: StockItem): void {
    const quantity = prompt(`Adicionar estoque para ${item.name}\nQuantidade:`);
    if (quantity && !isNaN(Number(quantity))) {
      item.currentStock += Number(quantity);
      this.updateStatus(item);
      alert(`Estoque atualizado!\n${item.name}: ${item.currentStock} unidades`);
    }
  }

  removeStock(item: StockItem): void {
    const quantity = prompt(`Remover estoque de ${item.name}\nQuantidade:`);
    if (quantity && !isNaN(Number(quantity))) {
      item.currentStock = Math.max(0, item.currentStock - Number(quantity));
      this.updateStatus(item);
      alert(`Estoque atualizado!\n${item.name}: ${item.currentStock} unidades`);
    }
  }

  updateStatus(item: StockItem): void {
    if (item.currentStock === 0) {
      item.status = 'out';
    } else if (item.currentStock < item.minStock) {
      item.status = 'low';
    } else {
      item.status = 'ok';
    }
  }

  viewHistory(item: StockItem): void {
    console.log('Histórico:', item);
    alert(`Histórico de movimentação\n${item.name}\nEm breve!`);
  }

  checkStockAlerts(): void {
    this.stockItems.forEach(item => {
      if (item.status === 'out') {
        this.notificationService.addNotification({
          type: 'out-of-stock',
          title: 'Produto Esgotado',
          message: `${item.name} está sem estoque!`,
          productId: item.id
        });
      } else if (item.status === 'low') {
        this.notificationService.addNotification({
          type: 'low-stock',
          title: 'Estoque Baixo',
          message: `${item.name} está com estoque baixo (${item.currentStock} unidades)`,
          productId: item.id
        });
      }
    });
  }
}
