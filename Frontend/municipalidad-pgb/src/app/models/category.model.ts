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