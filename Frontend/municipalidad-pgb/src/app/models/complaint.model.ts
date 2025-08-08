export interface ComplaintRequest {
    motivo: string;
    descripcion: string;
    celular: string;
    direccion: string;
    imagen?: string;
    nombre_apellido: string;
    email: string;
}

export interface ComplaintResponse {
    id: number;
    motivo: string;
    descripcion: string;
    celular: string;
    direccion: string;
    imagen: string;
    nombre_apellido: string;
    email: string;
}

export interface ComplaintUpdate {
    cerrado: boolean;
    comentario: string;
    //area: AreaIdDTO;
}