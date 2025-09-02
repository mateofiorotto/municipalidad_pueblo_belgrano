import { AreaIdDTO, AreaResponseDTO } from "./area.model";
import { Page } from "./page.model";

export interface ComplaintRequestDTO {
    motivo: string;
    descripcion: string;
    celular: string;
    direccion: string;
    nombre_apellido: string;
    email: string;
}

export interface ComplaintResponseDTO {
    id: number;
    motivo: string;
    descripcion: string;
    celular: string;
    direccion: string;
    nombre_apellido: string;
    email: string;
    fecha_reclamo: string;
    cerrado: boolean;
    fecha_cerrado: string;
    comentario: string;
    area: AreaResponseDTO;
}

export interface ComplaintUpdateDTO {
    cerrado: boolean;
    comentario: string;
    area: AreaIdDTO;
}

export interface ComplaintListResponse {
    status: number;
    message: string;
    result: {
        content: ComplaintResponseDTO[];
        page: Page;
      };
}

export interface ComplaintByIdResponse {
  status: number;
  message: string;
  result: ComplaintResponseDTO;
}