/**
 * Mappers para conversão entre DTOs e Models
 * Centraliza a lógica de transformação de dados
 */

import { Product } from '../models';
import { ProductDTO } from '../models/product.dto';

export class ProductMapper {
  /**
   * Converte ProductDTO (backend) para Product (frontend)
   */
  static toModel(dto: ProductDTO): Product {
    return {
      id: dto.id.toString(),
      name: dto.name,
      description: dto.description,
      categoryId: dto.categoriaId.toString(),
      category: {
        id: dto.categoriaId.toString(),
        name: dto.categoriaNome,
        createdAt: new Date()
      },
      cost: dto.costPrice,
      price: dto.price,
      salePrice: dto.salePrice, // Mapear preço de venda do backend
      stock: dto.stock,
      minStock: dto.minStock,
      imageUrl: dto.imageUrl,
      createdAt: new Date(dto.createdAt),
      updatedAt: new Date(dto.updatedAt)
    };
  }

  /**
   * Converte Product (frontend) para ProductDTO (backend)
   */
  static toDTO(product: Partial<Product>): Partial<ProductDTO> {
    return {
      categoriaId: product.categoryId ? parseInt(product.categoryId) : undefined,
      name: product.name,
      description: product.description,
      sku: product.id || 'AUTO', // Backend gera automaticamente
      barcode: product.id || 'AUTO', // Backend gera automaticamente
      price: product.price,
      costPrice: product.cost,
      salePrice: product.price, // Preço de venda = preço normal por padrão
      stock: product.stock,
      minStock: product.minStock,
      maxStock: product.minStock ? product.minStock * 3 : 100,
      unit: 'UN',
      imageUrl: product.imageUrl || '',
      active: true
    };
  }

  /**
   * Converte array de DTOs para array de Models
   */
  static toModelArray(dtos: ProductDTO[]): Product[] {
    return dtos.map(dto => this.toModel(dto));
  }
}
