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