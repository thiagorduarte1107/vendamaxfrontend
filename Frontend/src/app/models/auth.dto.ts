/**
 * DTOs para autenticação
 * Estrutura de dados que vem da API de autenticação
 */

export interface LoginRequestDTO {
  email: string;
  password: string;
}

export interface LoginResponseDTO {
  token: string;
  type: string;
  expiresIn: number;
  user: UserInfoDTO;
}

export interface UserInfoDTO {
  id: number;
  name: string;
  email: string;
  role: string;
}

export interface RegisterRequestDTO {
  nome: string;
  email: string;
  senha: string;
  perfil: string;
}

export interface AuthApiResponse<T> {
  success: boolean;
  message?: string;
  data: T;
  timestamp?: string;
}
