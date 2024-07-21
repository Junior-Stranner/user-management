declare namespace Projeto {
    type User = {
    id?: number;
    name: string;
    login: string;
    password: string;
    email: string;
    };

    type Resource ={
        id?: number;
        name: string;
        key: String;
    }

    type Profile = {
        id?: number;
        description: string;
    }

    type ProdileUser = {
        id?: number;
        Profile: perfil;
        User: user;
    }

    type PermissaoPerfilRecurso = {
        id?: number;
        perfil: Perfil;
        recurso: Recurso;
    }
}