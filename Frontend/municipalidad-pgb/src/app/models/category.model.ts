import { Page } from "./page.model";

export interface CategoryResponseDTO {
  id: number;
  nombre: string;
}

export interface CategoryIdDTO {
  id: number
}

export interface CategoryListResponse {
    status: number;
    message: string;
    result: CategoryResponseDTO[];
}

export interface CategoryListPaginatedResponse {
    status: number;
    message: string;
    result: {
        content: CategoryResponseDTO[];
        page: Page;
    };
}

export interface CategoryByIdResponse {
  status: number;
  message: string;
  result: CategoryResponseDTO;
}