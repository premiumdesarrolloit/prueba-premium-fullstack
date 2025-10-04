import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';


@Entity('cursos')
export class Course {
    @PrimaryGeneratedColumn('uuid')
    id: string;

    @Column('text')
    nombre: string;

    @Column('text')
    categoria: string;

    @Column('bigint')
    evaluacionesTotales: number;
    
    @Column('decimal')
    puntuacionPromedio: number;
    
    @Column('int')
    inscritos: number;
    
    @Column('decimal')
    indiceCalidad: number;
}
