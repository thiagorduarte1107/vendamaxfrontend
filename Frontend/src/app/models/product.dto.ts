/**
 * DTOs para comunicação com o backend
 * Estrutura de dados que vem da API
 */

export interface ApiResponse<T> {
  success: boolean;
  message?: string;
  data: T;
  timestamp?: string;
}

export interface ProductDTO {
  id: number;
  categoriaId: number;
  categoriaNome: string;
  name: string;
  description: string;
  sku: string;
  barcode: string;
  price: number;
  costPrice: number;
  salePrice: number;
  stock: number;
  minStock: number;
  maxStock: number;
  unit: string;
  imageUrl: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface CategoryDTO {
  id: number;
  name: string;
  description: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface CategoryCreateDTO {
  name: string;
  description?: string;
  active?: boolean;
}

export interface ClientDTO {
  id: number;
  name: string;
  email: string;
  phone: string;
  cpf: string;
  cnpj: string;
  address: string;
  city: string;
  state: string;
  zipcode: string;
  notes: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface SaleDTO {
  id: number;
  clienteId: number;
  clienteNome: string;
  usuarioId: number;
  usuarioNome: string;
  caixaId: number;
  totalAmount: number;
  discount: number;
  finalAmount: number;
  status: string;
  notes: string;
  itens: SaleItemDTO[];
  pagamentos: PaymentDTO[];
  createdAt: string;
  updatedAt: string;
}

export interface SaleItemDTO {
  id: number;
  vendaId: number;
  produtoId: number;
  produtoNome: string;
  quantity: number;
  unitPrice: number;
  discount: number;
  totalPrice: number;
  createdAt: string;
}

export interface PaymentDTO {
  id: number;
  vendaId: number;
  paymentMethod: string;
  amount: number;
  createdAt: string;
}
