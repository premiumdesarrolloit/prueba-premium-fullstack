import { IsString, IsUUID, Max, Min } from 'class-validator';


export class RateCourseDto {
    @IsUUID()
    @IsString()
    userId: string;

    @IsString()
    @Min(0)
    @Max(10)
    rate: number;
}