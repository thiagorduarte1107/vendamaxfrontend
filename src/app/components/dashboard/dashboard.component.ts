import { Component, OnInit, ViewChild } from '@angular/core';
import { DashboardService } from '../../services/dashboard.service';
import { ProductService } from '../../services/product.service';
import { DashboardMetrics, Product } from '../../models';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;
  
  metrics: DashboardMetrics | null = null;
  lowStockProducts: Product[] = [];
  loading = true;

  // Gráfico de Vendas por Período (Linha)
  public salesChartData: ChartData<'line'> = {
    labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun'],
    datasets: [
      {
        data: [12500, 15800, 18200, 22100, 19500, 25300],
        label: 'Vendas (R$)',
        fill: true,
        tension: 0.4,
        borderColor: '#000',
        backgroundColor: 'rgba(0, 0, 0, 0.1)'
      }
    ]
  };

  public salesChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: true, position: 'top' }
    }
  };

  public salesChartType: ChartType = 'line';

  // Gráfico de Produtos Mais Vendidos (Barra)
  public productsChartData: ChartData<'bar'> = {
    labels: ['Notebook', 'Mouse', 'Teclado', 'Monitor', 'Webcam'],
    datasets: [
      {
        data: [45, 89, 67, 34, 52],
        label: 'Unidades Vendidas',
        backgroundColor: '#000',
        borderColor: '#000',
        borderWidth: 1
      }
    ]
  };

  public productsChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: false }
    },
    scales: {
      y: { beginAtZero: true }
    }
  };

  public productsChartType: ChartType = 'bar';

  // Gráfico de Formas de Pagamento (Pizza)
  public paymentChartData: ChartData<'pie'> = {
    labels: ['Dinheiro', 'Débito', 'Crédito', 'PIX'],
    datasets: [
      {
        data: [25, 20, 35, 20],
        backgroundColor: ['#000', '#333', '#666', '#999'],
        borderWidth: 2,
        borderColor: '#fff'
      }
    ]
  };

  public paymentChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: true, position: 'right' }
    }
  };

  public paymentChartType: ChartType = 'pie';


  constructor(
    private dashboardService: DashboardService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.loading = true;

    this.dashboardService.getMetrics().subscribe({
      next: (metrics) => {
        this.metrics = metrics;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar métricas:', err);
        this.loading = false;
      }
    });

    this.productService.getLowStockProducts().subscribe({
      next: (products) => {
        this.lowStockProducts = products;
      },
      error: (err) => {
        console.error('Erro ao carregar produtos:', err);
      }
    });
  }


  formatCurrency(value: number): string {
    return value.toLocaleString('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    });
  }
}
