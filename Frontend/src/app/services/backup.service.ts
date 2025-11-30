import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BackupService {

  constructor() { }

  exportToJSON(data: any, filename: string): void {
    const jsonStr = JSON.stringify(data, null, 2);
    const blob = new Blob([jsonStr], { type: 'application/json' });
    this.downloadFile(blob, `${filename}.json`);
  }

  exportAllData(): void {
    const allData = {
      products: this.getFromLocalStorage('products') || [],
      clients: this.getFromLocalStorage('clients') || [],
      categories: this.getFromLocalStorage('categories') || [],
      sales: this.getFromLocalStorage('sales') || [],
      accountsReceivable: this.getFromLocalStorage('accountsReceivable') || [],
      accountsPayable: this.getFromLocalStorage('accountsPayable') || [],
      notifications: this.getFromLocalStorage('notifications') || [],
      exportDate: new Date().toISOString()
    };

    this.exportToJSON(allData, `vendamax-backup-${Date.now()}`);
  }

  importFromJSON(file: File): Promise<any> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      
      reader.onload = (e: any) => {
        try {
          const data = JSON.parse(e.target.result);
          resolve(data);
        } catch (error) {
          reject('Erro ao ler arquivo JSON');
        }
      };
      
      reader.onerror = () => reject('Erro ao ler arquivo');
      reader.readAsText(file);
    });
  }

  restoreAllData(data: any): void {
    if (data.products) localStorage.setItem('products', JSON.stringify(data.products));
    if (data.clients) localStorage.setItem('clients', JSON.stringify(data.clients));
    if (data.categories) localStorage.setItem('categories', JSON.stringify(data.categories));
    if (data.sales) localStorage.setItem('sales', JSON.stringify(data.sales));
    if (data.accountsReceivable) localStorage.setItem('accountsReceivable', JSON.stringify(data.accountsReceivable));
    if (data.accountsPayable) localStorage.setItem('accountsPayable', JSON.stringify(data.accountsPayable));
    if (data.notifications) localStorage.setItem('notifications', JSON.stringify(data.notifications));
  }

  private getFromLocalStorage(key: string): any {
    const item = localStorage.getItem(key);
    return item ? JSON.parse(item) : null;
  }

  private downloadFile(blob: Blob, filename: string): void {
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = filename;
    link.click();
    window.URL.revokeObjectURL(url);
  }

  clearAllData(): void {
    if (confirm('Tem certeza que deseja limpar TODOS os dados? Esta ação não pode ser desfeita!')) {
      localStorage.clear();
      alert('Todos os dados foram limpos!');
      window.location.reload();
    }
  }
}
