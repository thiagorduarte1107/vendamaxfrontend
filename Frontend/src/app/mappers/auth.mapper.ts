/**
 * Mapper para conversão de dados de autenticação
 */

import { User, UserRole, LoginCredentials } from '../models';
import { LoginRequestDTO, LoginResponseDTO, UserInfoDTO } from '../models/auth.dto';

export class AuthMapper {
  /**
   * Mapeia perfil do backend para role do frontend
   */
  private static mapRole(backendRole: string): UserRole {
    const roleMap: Record<string, UserRole> = {
      'ADMIN': 'admin',
      'GERENTE': 'manager',
      'MANAGER': 'manager',
      'VENDEDOR': 'seller',
      'SELLER': 'seller',
      'CAIXA': 'cashier',
      'CASHIER': 'cashier'
    };
    return roleMap[backendRole.toUpperCase()] || 'seller';
  }

  /**
   * Mapeia permissões baseadas na role
   */
  private static mapPermissions(role: UserRole) {
    const permissionsMap = {
      admin: {
        dashboard: true,
        products: true,
        clients: true,
        sales: true,
        pdv: true,
        reports: true,
        stock: true,
        financial: true,
        users: true,
        settings: true
      },
      manager: {
        dashboard: true,
        products: true,
        clients: true,
        sales: true,
        pdv: true,
        reports: true,
        stock: true,
        financial: true,
        users: false,
        settings: false
      },
      seller: {
        dashboard: true,
        products: true,
        clients: true,
        sales: true,
        pdv: true,
        reports: false,
        stock: false,
        financial: false,
        users: false,
        settings: false
      },
      cashier: {
        dashboard: false,
        products: false,
        clients: false,
        sales: true,
        pdv: true,
        reports: false,
        stock: false,
        financial: false,
        users: false,
        settings: false
      }
    };
    return permissionsMap[role];
  }

  /**
   * Converte LoginCredentials (frontend) para LoginRequestDTO (backend)
   */
  static toLoginRequest(credentials: LoginCredentials): LoginRequestDTO {
    return {
      email: credentials.email,
      password: credentials.password
    };
  }

  /**
   * Converte UserInfoDTO (backend) para User (frontend)
   */
  static toUser(userInfo: UserInfoDTO): User {
    const role = this.mapRole(userInfo.role);
    return {
      id: userInfo.id.toString(),
      name: userInfo.name,
      email: userInfo.email,
      role: role,
      permissions: this.mapPermissions(role),
      active: true,
      createdAt: new Date(),
      updatedAt: new Date()
    };
  }

  /**
   * Converte LoginResponseDTO completo para User
   */
  static loginResponseToUser(response: LoginResponseDTO): User {
    return this.toUser(response.user);
  }
}
