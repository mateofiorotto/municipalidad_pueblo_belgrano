import { Page } from "./page.model";

export interface EventResponseDTO {
  id: number;
  titular: string;
  fecha: string;
  imagen: string;
  descripcion: string;
  descripcion_adicional: string;
}

export interface EventIdDTO {
  id: number;
}

export interface EventListResponse {
  status: number;
  message: string;
  result: EventResponseDTO[];
}

export interface EventListDataResponse {
  status: number;
  message: string;
  result: {
      content: EventResponseDTO[];
      page: Page;
    };
}

export interface EventByIdResponse {
  status: number;
  message: string;
  result: EventResponseDTO;
}

export interface EventRequestDTO {
  titular: string;
  fecha: string;
  imagen: string;
  descripcion: string;
  descripcion_adicional?: string;
}