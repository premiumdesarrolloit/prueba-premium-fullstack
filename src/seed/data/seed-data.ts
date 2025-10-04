import * as bcrypt from 'bcrypt';

export const seedData = {
    users: [
        {
            email: 'admin@gmail.com',
            fullName: 'Test One',
            password: bcrypt.hashSync( 'Pruebas2025', 10 ),
            roles: ['admin']
            },
        {
            email: 'user@gmail.com',
            fullName: 'Test Two',
            password: bcrypt.hashSync('Pruebas2025', 10),
            roles: ['user']
        }
    ],
    courses: [
        {
            nombre: 'CISCO Networking',
            categoria: 'Informatica'
        },
        {
            nombre: 'Mecanica automotriz',
            categoria: 'Ingenieria'
        },
        {
            nombre: 'Electricidad y electronica',
            categoria: 'Ingenieria'
        },
        {
            nombre: 'Docker & Kubernetes',
            categoria: 'Informatica'
        }
    ]
};