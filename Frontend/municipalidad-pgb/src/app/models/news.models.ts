import { CategoryIdDTO, CategoryResponseDTO } from './category.model';
import { EventResponseDTO } from './event.model';
import { Page } from './page.model';
import { EventIdDTO } from './event.model';

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

export interface NewsListResponse {
  status: number;
  message: string;
  result: {
    content: NewsResponseDTO[];
    page: Page;
  };
}

export interface NewsThreeLastResponse {
  status: number;
  message: string;
  result: NewsResponseDTO[];
}

export interface NewsByIdResponse {
  status: number;
  message: string;
  result: NewsResponseDTO;
}

export interface NewsRequestDTO {
  titular: string;
  subtitulo: string;
  fecha: string;
  imagen: string;
  descripcion: string;
  descripcion_adicional?: string;
  categoria: CategoryIdDTO;
  evento?: EventIdDTO;
}