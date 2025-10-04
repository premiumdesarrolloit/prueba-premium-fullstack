import { IsString, MaxLength, MinLength } from 'class-validator';


export class CreateCourseDto {
    @IsString()
    @MinLength(6)
    @MaxLength(50)
    nombre: string;

    @IsString()
    @MinLength(6)
    @MaxLength(50)
    categoria: string;
}