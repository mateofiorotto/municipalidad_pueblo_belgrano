export const environment: Environment = {
    production: false,
    baseUrl: 'http://localhost:8080',
};

interface Environment {
    production: boolean;
    baseUrl: string;
}