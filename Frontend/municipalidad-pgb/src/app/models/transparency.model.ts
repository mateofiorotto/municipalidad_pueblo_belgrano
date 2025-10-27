import { Page } from "./page.model";

export interface TransparencyResponseDTO {
  id: number;
  titulo: string;
  fecha: string;
  pdf: string;
  tipo: string;
}

export interface TransparencyListResponse {
  status: number;
  message: string;
  result: {
    content: TransparencyResponseDTO[];
    page: Page;
  };
}

export interface TransparencyByIdResponse {
  status: number;
  message: string;
  result: TransparencyResponseDTO;
}

export interface TransparencyRequestDTO {
  titulo: string;
  fecha: string;
  pdf: string;
  tipo: string;
}