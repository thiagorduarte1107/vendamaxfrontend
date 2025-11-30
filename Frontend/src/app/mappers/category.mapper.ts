/**
 * Mapper para conversão de dados de categorias
 */

import { Category } from '../models';
import { CategoryDTO } from '../models/product.dto';

export class CategoryMapper {
  /**
   * Converte CategoryDTO (backend) para Category (frontend)
   */
  static toModel(dto: CategoryDTO): Category {
    return {
      id: dto.id.toString(),
      name: dto.name,
      description: dto.description,
      createdAt: new Date(dto.createdAt)
    };
  }

  /**
   * Converte Category (frontend) para CategoryDTO (backend)
   */
  static toDTO(category: Partial<Category>): Partial<CategoryDTO> {
    return {
      name: category.name,
      description: category.description,
      active: true
    };
  }

  /**
   * Converte array de DTOs para array de Models
   */
  static toModelArray(dtos: CategoryDTO[]): Category[] {
    return dtos.map(dto => this.toModel(dto));
  }
}
