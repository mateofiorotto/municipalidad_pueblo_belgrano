export interface EventResponseDTO {
  id: number;
  titular: string;
  fecha: string; // Tipo `string` porque se representa como ISO date (ej: "2025-08-05")
  imagen: string;
  descripcion: string;
  descripcion_adicional: string;
}