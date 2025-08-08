export interface UserLoginResponse {
    jwt: string;
}

export interface User {
    username: string;
    roles: string[];
    enabled: boolean;
}