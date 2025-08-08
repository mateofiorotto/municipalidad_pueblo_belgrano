import { CategoryResponseDTO } from './category.model';
import { EventResponseDTO } from './event.model';

export interface NewsResponseDTO {
  id: number;
  titular: string;
  subtitulo: string;
  fecha: string;
  imagen: string;
  descripcion: string;
  descripcion_adicional: string;
  categoria: CategoryResponseDTO;
  evento: EventResponseDTO;
}

export interface Page {
  size: number;
  number: number;
  totalElements: number;
  totalPages: number;
}

export interface NewsListResponse {
  status: number;
  message: string;
  result: {
    content: NewsResponseDTO[];
    page: Page;
  };
}

export interface NewsByIdResponse {
  status: number;
  message: string;
  result: NewsResponseDTO;
}