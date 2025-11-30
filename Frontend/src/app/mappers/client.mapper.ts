/**
 * Mapper para conversão de dados de clientes
 */

import { Client } from '../models';
import { ClientDTO } from '../models/product.dto';

export class ClientMapper {
  /**
   * Converte ClientDTO (backend) para Client (frontend)
   */
  static toModel(dto: ClientDTO): Client {
    return {
      id: dto.id.toString(),
      name: dto.name,
      email: dto.email,
      phone: dto.phone,
      address: dto.address || '',
      creditLimit: 0, // Backend não tem esse campo
      currentDebt: 0, // Backend não tem esse campo
      createdAt: new Date(dto.createdAt)
    };
  }

  /**
   * Converte Client (frontend) para ClientDTO (backend)
   */
  static toDTO(client: any): any {
    // Detectar se é CPF ou CNPJ baseado no tamanho (apenas números)
    const cpfCnpj = client.cpfCnpj?.replace(/\D/g, '') || '';
    const timestamp = Date.now();
    
    let cpf: string;
    let cnpj: string;
    
    if (cpfCnpj.length === 11) {
      // É CPF
      cpf = cpfCnpj;
      cnpj = `CNPJ${timestamp}`;
    } else if (cpfCnpj.length === 14) {
      // É CNPJ
      cpf = `CPF${timestamp}`;
      cnpj = cpfCnpj;
    } else if (cpfCnpj.length > 0) {
      // Valor inválido, usar como está
      cpf = cpfCnpj;
      cnpj = `CNPJ${timestamp}`;
    } else {
      // Vazio, gerar temporários
      cpf = `CPF${timestamp}`;
      cnpj = `CNPJ${timestamp}`;
    }
    
    return {
      name: client.name || '',
      email: client.email || '',
      phone: client.phone || '',
      cpf: cpf,
      cnpj: cnpj,
      address: client.address || '',
      city: '',
      state: '',
      zipcode: '',
      notes: '',
      active: true
    };
  }

  /**
   * Converte array de DTOs para array de Models
   */
  static toModelArray(dtos: ClientDTO[]): Client[] {
    return dtos.map(dto => this.toModel(dto));
  }
}
