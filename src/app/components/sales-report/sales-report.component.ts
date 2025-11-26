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
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatChipsModule } from '@angular/material/chips';

interface Sale {
  id: number;
  date: Date;
  client: string;
  items: number;
  subtotal: number;
  discount: number;
  total: number;
  paymentMethod: string;
  status: string;
}

@Component({
  selector: 'app-sales-report',
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
    MatDatepickerModule,
    MatNativeDateModule,
    MatChipsModule
  ],
  templateUrl: './sales-report.component.html',
  styleUrl: './sales-report.component.scss'
})
export class SalesReportComponent implements OnInit {
  displayedColumns: string[] = ['id', 'date', 'client', 'items', 'total', 'paymentMethod', 'status', 'actions'];
  
  startDate: Date | null = null;
  endDate: Date | null = null;
  paymentFilter = 'all';
  statusFilter = 'all';
  
  sales: Sale[] = [];
  filteredSales: Sale[] = [];

  ngOnInit(): void {
    this.loadMockSales();
    this.applyFilters();
  }

  loadMockSales(): void {
    // Mock de vendas
    this.sales = [
      {
        id: 1001,
        date: new Date(2024, 10, 20),
        client: 'João Silva',
        items: 3,
        subtotal: 4500.00,
        discount: 225.00,
        total: 4275.00,
        paymentMethod: 'credit',
        status: 'completed'
      },
      {
        id: 1002,
        date: new Date(2024, 10, 21),
        client: 'Maria Santos',
        items: 2,
        subtotal: 1200.00,
        discount: 0,
        total: 1200.00,
        paymentMethod: 'pix',
        status: 'completed'
      },
      {
        id: 1003,
        date: new Date(2024, 10, 22),
        client: 'Pedro Costa',
        items: 5,
        subtotal: 2800.00,
        discount: 140.00,
        total: 2660.00,
        paymentMethod: 'money',
        status: 'completed'
      },
      {
        id: 1004,
        date: new Date(2024, 10, 23),
        client: 'Ana Paula',
        items: 1,
        subtotal: 3500.00,
        discount: 0,
        total: 3500.00,
        paymentMethod: 'debit',
        status: 'pending'
      },
      {
        id: 1005,
        date: new Date(2024, 10, 24),
        client: 'Carlos Mendes',
        items: 4,
        subtotal: 1800.00,
        discount: 90.00,
        total: 1710.00,
        paymentMethod: 'credit',
        status: 'completed'
      }
    ];
  }

  applyFilters(): void {
    this.filteredSales = this.sales.filter(sale => {
      let matchesDate = true;
      let matchesPayment = true;
      let matchesStatus = true;

      // Filtro de data
      if (this.startDate && this.endDate) {
        matchesDate = sale.date >= this.startDate && sale.date <= this.endDate;
      }

      // Filtro de pagamento
      if (this.paymentFilter !== 'all') {
        matchesPayment = sale.paymentMethod === this.paymentFilter;
      }

      // Filtro de status
      if (this.statusFilter !== 'all') {
        matchesStatus = sale.status === this.statusFilter;
      }

      return matchesDate && matchesPayment && matchesStatus;
    });
  }

  clearFilters(): void {
    this.startDate = null;
    this.endDate = null;
    this.paymentFilter = 'all';
    this.statusFilter = 'all';
    this.applyFilters();
  }

  get totalSales(): number {
    return this.filteredSales.reduce((sum, sale) => sum + sale.total, 0);
  }

  get totalItems(): number {
    return this.filteredSales.reduce((sum, sale) => sum + sale.items, 0);
  }

  get averageTicket(): number {
    return this.filteredSales.length > 0 ? this.totalSales / this.filteredSales.length : 0;
  }

  getPaymentMethodLabel(method: string): string {
    const labels: { [key: string]: string } = {
      'money': 'Dinheiro',
      'debit': 'Débito',
      'credit': 'Crédito',
      'pix': 'PIX'
    };
    return labels[method] || method;
  }

  getStatusLabel(status: string): string {
    const labels: { [key: string]: string } = {
      'completed': 'Concluída',
      'pending': 'Pendente',
      'cancelled': 'Cancelada'
    };
    return labels[status] || status;
  }

  viewSaleDetails(sale: Sale): void {
    console.log('Detalhes da venda:', sale);
    alert(`Venda #${sale.id}\nCliente: ${sale.client}\nTotal: R$ ${sale.total.toFixed(2)}`);
  }

  exportReport(): void {
    console.log('Exportando relatório...', this.filteredSales);
    alert('Funcionalidade de exportação será implementada em breve!');
  }
}
