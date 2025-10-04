import { IsString, MaxLength, MinLength } from 'class-validator';


export class UpdateCourseDto {
    @IsString()
    @MinLength(6)
    @MaxLength(50)
    nombre: string;

    @IsString()
    @MinLength(6)
    @MaxLength(50)
    categoria: string;
}