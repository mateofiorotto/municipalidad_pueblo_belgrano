import { Page } from "./page.model";

export interface TransparencyResponseDTO {
  id: number;
  fecha: string;
  pdf: string;
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
  fecha: string;
  pdf: string;
}