import { Page } from './page.model';

export interface AreaResponseDTO {
  id: number;
  nombre: string;
}

export interface AreaRequestDTO {
  nombre: string;
}

export interface AreaIdDTO {
  id: number
}

export interface AreaListResponse {
    status: number;
    message: string;
    result: AreaResponseDTO[];
}

export interface AreaListPaginatedResponse {
    status: number;
    message: string;
    result: {
        content: AreaResponseDTO[];
        page: Page;
    };
}

export interface AreaByIdResponse {
  status: number;
  message: string;
  result: AreaResponseDTO;
}