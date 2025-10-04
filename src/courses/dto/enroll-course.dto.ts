import { IsString, IsUUID } from 'class-validator';


export class EnrollCourseDto {
    @IsString()
    @IsUUID()
    userId: string;

    @IsString()
    @IsUUID()
    courseId: string;
}